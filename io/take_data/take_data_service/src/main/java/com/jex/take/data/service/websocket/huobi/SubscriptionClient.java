package com.jex.take.data.service.websocket.huobi;

import com.jex.take.data.service.dto.TickerDTO;
import com.jex.take.data.service.enums.CandlestickInterval;
import com.jex.take.data.service.model.event.CandlestickEvent;
import com.jex.take.data.service.model.event.CandlestickReqEvent;
import com.jex.take.data.service.util.SubscriptionErrorHandler;
import com.jex.take.data.service.util.SubscriptionListener;

public interface SubscriptionClient {



    /**
     * Subscribe candlestick/kline event. If the candlestick/kline is updated, server will send the data to client and onReceive in callback will be
     * called.
     *
     * @param symbols The symbols, like "btcusdt". Use comma to separate multi symbols, like "btcusdt,ethusdt".
     * @param interval The candlestick/kline interval, MIN1, MIN5, DAY1 etc.
     * @param callback The implementation is required. onReceive will be called if receive server's update.
     */
    void subscribeCandlestickEvent(String symbols, CandlestickInterval interval,
                                   SubscriptionListener<CandlestickEvent> callback);

    /**
     * Subscribe candlestick/kline event. If the candlestick/kline is updated, server will send the data to client and onReceive in callback will be
     * called.
     *
     * @param symbols The symbols, like "btcusdt". Use comma to separate multi symbols, like "btcusdt,ethusdt".
     * @param interval The candlestick/kline interval, MIN1, MIN5, DAY1 etc.
     * @param callback The implementation is required. onReceive will be called if receive server's update.
     * @param errorHandler The error handler will be called if subscription failed or error happen between client and Huobi server.
     */
    void subscribeCandlestickHuobiEvent(String symbols, CandlestickInterval interval,
                                   SubscriptionListener<CandlestickEvent> callback,
                                   SubscriptionErrorHandler errorHandler);



    void subscribeCandlestickOKEvent(String symbols,
                                        SubscriptionListener<CandlestickEvent> callback,
                                        SubscriptionErrorHandler errorHandler);

    void subscribeCandlestickBinanceEvent(
                                        SubscriptionListener<CandlestickEvent> callback,
                                        SubscriptionErrorHandler errorHandler);
    /**
     * 火币的ticker(火币的ticker 主要取的是 k 线,没有找到一直推送的数据)
     * @param symbols
     * @param interval
     * @param callback
     * @param errorHandler
     */
    void subscribeTickerHuobiEvent(String symbols, CandlestickInterval interval,
                                   SubscriptionListener<TickerDTO> callback,
                                   SubscriptionErrorHandler errorHandler);

    /**
     * ok 的订阅ticker
     * @param symbols
     * @param callback
     * @param errorHandler
     */
    void subscribeTickerOkEvent(String symbols,
                                   SubscriptionListener<TickerDTO> callback,
                                   SubscriptionErrorHandler errorHandler);

    /**
     * 币安的ticker

     * @param callback
     * @param errorHandler
     */
    void subscribeTickerBinanceEvent(
                                SubscriptionListener<TickerDTO> callback,
                                SubscriptionErrorHandler errorHandler);
    /**
     * Request candlestick/kline event. If the candlestick/kline is received, server will send the data to client and onReceive in callback will be
     * called.
     *
     * @param symbols The symbols, like "btcusdt". Use comma to separate multi symbols, like "btcusdt,ethusdt".
     * @param from The query start timestamp
     * @param to The query end timestamp
     * @param interval The candlestick/kline interval, MIN1, MIN5, DAY1 etc.
     * @param subscriptionListener The implementation is required. onReceive will be called if receive server's response.
     */

    void requestCandlestickEvent(
            String symbols, Long from, Long to,
            CandlestickInterval interval,
            SubscriptionListener<CandlestickReqEvent> subscriptionListener);

    /**
     * Request candlestick/kline event. If the candlestick/kline is received, server will send the data to client and onReceive in callback will be
     * called.
     *
     * @param symbols The symbols, like "btcusdt". Use comma to separate multi symbols, like "btcusdt,ethusdt".
     * @param from The query start timestamp
     * @param to The query end timestamp
     * @param interval The candlestick/kline interval, MIN1, MIN5, DAY1 etc.
     * @param subscriptionListener The implementation is required. onReceive will be called if receive server's response.
     * @param errorHandler The error handler will be called if subscription failed or error happen * between client and Huobi server.
     */

    void requestCandlestickEvent(
            String symbols, Long from, Long to,
            CandlestickInterval interval,
            SubscriptionListener<CandlestickReqEvent> subscriptionListener,
            SubscriptionErrorHandler errorHandler);

    /**
     * Request candlestick/kline event. If the candlestick/kline is received, server will send the data to client and onReceive in callback will be
     * called.
     *
     * @param symbols The symbols, like "btcusdt". Use comma to separate multi symbols, like "btcusdt,ethusdt".
     * @param from The query start timestamp
     * @param to The query end timestamp
     * @param interval The candlestick/kline interval, MIN1, MIN5, DAY1 etc.
     * @param autoClose True or false.
     * @param subscriptionListener The implementation is required. onReceive will be called if receive server's response.
     * @param errorHandler The error handler will be called if subscription failed or error happen * between client and Huobi server.
     */

    void requestCandlestickEvent(
            String symbols, Long from, Long to,
            CandlestickInterval interval,
            boolean autoClose,
            SubscriptionListener<CandlestickReqEvent> subscriptionListener,
            SubscriptionErrorHandler errorHandler);


    static SubscriptionClient create() {
        return create( new SubscriptionOptions());
    }

    static SubscriptionClient create(
           SubscriptionOptions subscriptionOptions) {
        return HuobiApiInternalFactory.getInstance().createSubscriptionClient(
                subscriptionOptions);
    }

}
