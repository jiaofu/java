package com.jex.take.data.service.websocket.huobi;

import com.jex.take.data.service.util.InternalUtils;
import com.jex.take.data.service.util.JsonWrapper;
import com.jex.take.data.service.util.TimeService;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.Unpooled;
import lombok.extern.slf4j.Slf4j;
import okhttp3.WebSocket;
import okio.ByteString;

import java.io.IOException;
import java.util.zip.Inflater;

@Slf4j
public class OkWebSocketConnection extends WebSocketConnection  {
    OkWebSocketConnection(

            SubscriptionOptions options,
            WebsocketRequest request,
            WebSocketWatchDog watchDog,
            boolean autoClose){
        super(options,request,watchDog,autoClose);
    }


    //解压
    private static String uncompress(ByteBuf buf) {
        try {
            byte[] temp = new byte[buf.readableBytes()];
            ByteBufInputStream bis = new ByteBufInputStream(buf);
            bis.read(temp);
            bis.close();
            Inflater decompresser = new Inflater(true);
            decompresser.setInput(temp, 0, temp.length);
            StringBuilder sb = new StringBuilder();
            byte[] result = new byte[1024];
            while (!decompresser.finished()) {
                int resultLength = decompresser.inflate(result);
                sb.append(new String(result, 0, resultLength, "UTF-8"));
            }
            decompresser.end();
            return sb.toString();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private static String decode(byte[] array){
        ByteBuf byteBuf = Unpooled.wrappedBuffer(array);
        String str = uncompress(byteBuf);
        return str;
    }

    // webSock 相关



    @Override
    public void onMessage(WebSocket webSocket, String text) {
        super.onMessage(webSocket, text);
        lastReceivedTime = TimeService.getCurrentTimeStamp();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onMessage(WebSocket webSocket, ByteString bytes) {
        super.onMessage(webSocket, bytes);
        try {
            if (request == null) {
                log.error("[Sub][" + this.connectionId
                        + "] request is null");
                closeOnError();
                return;
            }

            lastReceivedTime = TimeService.getCurrentTimeStamp();

            String data;
            try {
                 data = decode(bytes.toByteArray());
              //  log.info(" 收到的消息:"+data);
            } catch (Exception e) {
                log.error("[Sub][" + this.connectionId
                        + "] Receive message error ok: " + e.getMessage());
                closeOnError();
                return;
            }
            if(data == null){
                return;
            }
            if(data.equals("pong")){
                log.info(" 收到ok的pong");
                return;
            }
            JsonWrapper jsonWrapper = JsonWrapper.parseFromString(data);

            if(jsonWrapper.containKey("table")&& jsonWrapper.containKey("data")){
                onReceiveAndClose(jsonWrapper);
                //log.info("收到的消息:"+webSocketData);
            }
            if (jsonWrapper.containKey("event")) {

                if("error".equals(jsonWrapper.getString("event"))){
                    String errorCode = jsonWrapper.getStringOrDefault("message", "");

                    onError(errorCode + ": " , null);
                    log.error("[Sub][" + this.connectionId
                            + "] Got error from server: " + errorCode + "; " );
                    close();
                }
               else if("subscribe".equals(jsonWrapper.getString("event"))){
                   String channel =  jsonWrapper.getStringOrDefault("channel", "");;
                   log.info("ok订阅频道成功:"+channel);
                }

            }
        } catch (Exception e) {
            log.error("[Sub][" + this.connectionId + "] Unexpected error: " + e.getMessage());
            closeOnError();
        }
    }

    private void processPingOnTradingLine(WebSocket webSocket) {

        webSocket.send("ping");
    }



}
