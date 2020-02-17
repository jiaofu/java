package com.jex.take.data.service.util;

import com.jex.take.data.service.exception.ApiException;
import com.jex.take.data.service.util.result.AsyncResult;
import com.jex.take.data.service.util.result.FailedAsyncResult;
import com.jex.take.data.service.util.result.ResponseCallback;
import com.jex.take.data.service.util.result.SucceededAsyncResult;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.io.IOException;

@Slf4j
public  class RestApiInvoker {



    private static final OkHttpClient client = new OkHttpClient();
    static void checkResponse(JsonWrapper json) {
        try {
            if (json.containKey("status")) {
                String status = json.getString("status");
                if ("error".equals(status)) {
                    String err_code = json.getString("err-code");
                    String err_msg = json.getString("err-msg");
                    throw new ApiException(ApiException.EXEC_ERROR,
                            "[Executing] " + err_code + ": " + err_msg);
                } else if (!"ok".equals(status)) {
                    throw new ApiException(
                            ApiException.RUNTIME_ERROR, "[Invoking] Response is not expected: " + status);
                }
            } else if (json.containKey("success")) {
                boolean success = json.getBoolean("success");
                if (!success) {
                    String err_code = EtfResult.checkResult(json.getInteger("code"));
                    String err_msg = json.getString("message");
                    if ("".equals(err_code)) {
                        throw new ApiException(ApiException.EXEC_ERROR, "[Executing] " + err_msg);
                    } else {
                        throw new ApiException(ApiException.EXEC_ERROR,
                                "[Executing] " + err_code + ": " + err_msg);
                    }
                }
            } else if (json.containKey("code")) {

                int code = json.getInteger("code");
                if (code != 200) {
                    String message = json.getString("message");
                    throw new ApiException(ApiException.EXEC_ERROR, "[Executing] " + code + ": " + message);
                }
            } else {
                throw new ApiException(
                        ApiException.RUNTIME_ERROR, "[Invoking] Status cannot be found in response.");
            }
        } catch (ApiException e) {
            throw e;
        } catch (Exception e) {
            throw new ApiException(
                    ApiException.RUNTIME_ERROR, "[Invoking] Unexpected error: " + e.getMessage());
        }
    }

   public static <T> T callSync(RestApiRequest<T> request) {
        try {
            String str;
            log.debug("Request URL " + request.request.url());
            Response response = client.newCall(request.request).execute();
            if (response.code() != 200) {
                throw new ApiException(
                        ApiException.EXEC_ERROR, "[Invoking] Response Status Error : "+response.code()+" message:"+response.message());
            }
            if (response != null && response.body() != null) {
                str = response.body().string();
                response.close();
            } else {
                throw new ApiException(
                        ApiException.ENV_ERROR, "[Invoking] Cannot get the response from server");
            }
            log.debug("Response =====> " + str);
            JsonWrapper jsonWrapper = JsonWrapper.parseFromString(str);
            checkResponse(jsonWrapper);
            return request.jsonParser.parseJson(jsonWrapper);
        } catch (ApiException e) {
            throw e;
        } catch (Exception e) {
            throw new ApiException(
                    ApiException.ENV_ERROR, "[Invoking] Unexpected error: " + e.getMessage());
        }
    }

   public static <T> void callASync(RestApiRequest<T> request, ResponseCallback<AsyncResult<T>> callback) {
        try {
            Call call = client.newCall(request.request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    FailedAsyncResult<T> result = new FailedAsyncResult<>(
                            new ApiException(ApiException.RUNTIME_ERROR,
                                    "[Invoking] Rest api call failed"));
                    try {
                        callback.onResponse(result);
                    } catch (Exception exception) {
                        log.error("[Invoking] Unexpected error: " + exception.getMessage(), e);
                    }
                }

                @Override
                public void onResponse(Call call, Response response) {
                    String str = "";
                    JsonWrapper jsonWrapper;
                    try {
                        if (response != null && response.body() != null) {
                            str = response.body().string();
                            response.close();
                        }
                        jsonWrapper = JsonWrapper.parseFromString(str);
                        checkResponse(jsonWrapper);

                    } catch (ApiException e) {
                        FailedAsyncResult<T> result = new FailedAsyncResult<>(e);
                        callback.onResponse(result);
                        return;
                    } catch (Exception e) {
                        FailedAsyncResult<T> result = new FailedAsyncResult<>(
                                new ApiException(
                                        ApiException.RUNTIME_ERROR, "[Invoking] Rest api call failed"));
                        callback.onResponse(result);
                        return;
                    }
                    try {
                        SucceededAsyncResult<T> result = new SucceededAsyncResult<>(
                                request.jsonParser.parseJson(jsonWrapper));
                        callback.onResponse(result);
                    } catch (Exception e) {
                        log.error("[Invoking] Unexpected error: " + e.getMessage(), e);
                    }

                }
            });
        } catch (Throwable e) {
            throw new ApiException(
                    ApiException.ENV_ERROR, "[Invoking] Unexpected error: " + e.getMessage());
        }
    }
   public static WebSocket createWebSocket(Request request, WebSocketListener listener) {
        return client.newWebSocket(request, listener);
    }
}
