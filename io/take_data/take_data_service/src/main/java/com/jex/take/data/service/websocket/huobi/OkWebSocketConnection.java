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
import org.apache.commons.compress.compressors.deflate64.Deflate64CompressorInputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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
    private static String uncompress(byte[] bytes) {
        try (final ByteArrayOutputStream out = new ByteArrayOutputStream();
             final ByteArrayInputStream in = new ByteArrayInputStream(bytes);
             final Deflate64CompressorInputStream zin = new Deflate64CompressorInputStream(in)) {
            final byte[] buffer = new byte[1024];
            int offset;
            while (-1 != (offset = zin.read(buffer))) {
                out.write(buffer, 0, offset);
            }
            return out.toString();
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
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
                String resultWebSocket = uncompress(   bytes.toByteArray());
                System.out.println(uncompress(   bytes.toByteArray()));
                data = new String(InternalUtils.decode(bytes.toByteArray()));
            } catch (IOException e) {
                log.error("[Sub][" + this.connectionId
                        + "] Receive message error: " + e.getMessage());
                closeOnError();
                return;
            }
            log.debug("[On Message][{}] {}", connectionId, data);
            JsonWrapper jsonWrapper = JsonWrapper.parseFromString(data);
            if (jsonWrapper.containKey("status") && !"ok".equals(jsonWrapper.getString("status"))) {
                String errorCode = jsonWrapper.getStringOrDefault("err-code", "");
                String errorMsg = jsonWrapper.getStringOrDefault("err-msg", "");
                onError(errorCode + ": " + errorMsg, null);
                log.error("[Sub][" + this.connectionId
                        + "] Got error from server: " + errorCode + "; " + errorMsg);
                close();
            } else if (jsonWrapper.containKey("op")) {
                String op = jsonWrapper.getString("op");
                if (op.equals("notify")) {
                    onReceive(jsonWrapper);
                } else if (op.equals("ping")) {
                    processPingOnTradingLine(jsonWrapper, webSocket);
                } else if (op.equals("auth")) {
                    if (request.authHandler != null) {
                        request.authHandler.handle(this);
                    }
                } else if (op.equals("req")) {
                    onReceiveAndClose(jsonWrapper);
                }
            } else if (jsonWrapper.containKey("ch") || jsonWrapper.containKey("rep")) {
                onReceiveAndClose(jsonWrapper);
            } else if (jsonWrapper.containKey("ping")) {

            } else if (jsonWrapper.containKey("subbed")) {
            }
        } catch (Exception e) {
            log.error("[Sub][" + this.connectionId + "] Unexpected error: " + e.getMessage());
            closeOnError();
        }
    }

    private void processPingOnTradingLine(JsonWrapper jsonWrapper, WebSocket webSocket) {

        webSocket.send("ping");
    }



}
