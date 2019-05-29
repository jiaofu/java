package com.jex.market.job;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

@Slf4j
public class JexWebSocketListener extends WebSocketListener {

    private JexCallback callback;


    private boolean closing = false;

    private static   long end = 0;
    private static int frequency = 0;
    private static int allFrequency = 0;

    public JexWebSocketListener(JexCallback callback) {
        this.callback = callback;

    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        try {
            callback.onResponse(text);
            end = System.currentTimeMillis();
            allFrequency = 0;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void onClosing(final WebSocket webSocket, final int code, final String reason) {
        closing = true;
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {

        if (!closing) {
            String str = getTryConnect(webSocket);
            try {
                Thread.sleep(frequency * 1000);
                log.error(str, t);
                callback.onFailure();
            } catch (Exception ex) {
                log.error(str + "出现异常", ex);
            }
        }
    }

    /**
     * 尝试次数
     *
     * @return
     */
    private String getTryConnect(WebSocket webSocket) {
        allFrequency++;
        long now = System.currentTimeMillis();
        if ((now - end) / 1000 > 300) {
            frequency = 30;
        }
        frequency++;
        if (frequency > 30) {
            frequency = 30;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(webSocket.request().url());
        stringBuilder.append("连接失败");
        stringBuilder.append("在");
        stringBuilder.append(now / 1000);
        stringBuilder.append("秒内一共尝试了");
        stringBuilder.append(allFrequency);
        stringBuilder.append("次,当前沉睡时间为" + frequency);
        stringBuilder.append("秒");
        return stringBuilder.toString();
    }

}
