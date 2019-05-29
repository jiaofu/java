package com.jex.market.job;

public interface JexCallback {



    void onResponse(String response);

    /**
     * Called whenever an error occurs.
     *

     */
    default void onFailure() {
        initialize();
    }

    void initialize();
}
