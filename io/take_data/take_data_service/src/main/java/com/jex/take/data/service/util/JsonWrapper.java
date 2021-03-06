package com.jex.take.data.service.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.jex.take.data.service.exception.ApiException;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

@Slf4j
public class JsonWrapper {
    private  JSONObject json;
    private   JSONArray jsonArrayOk;
    public static JsonWrapper parseFromString(String text)  {

        if(JSON.isValidArray(text)){
            JSONArray jsona = JSON.parseArray(text);
            if (jsona != null) {
                return new JsonWrapper(jsona);
            } else {
                throw new ApiException(ApiException.RUNTIME_ERROR,
                        "[Json] Unknown error when parse: " + text);
            }
        }
        try {
            JSONObject jsonObject = (JSONObject) JSON.parse(text);
            if (jsonObject != null) {
                return new JsonWrapper(jsonObject);
            } else {
                throw new ApiException(ApiException.RUNTIME_ERROR,
                        "[Json] Unknown error when parse: " + text);
            }
        } catch (JSONException e) {
            throw new ApiException(ApiException.RUNTIME_ERROR,
                    "[Json] Fail to parse json: " + text);
        } catch (Exception e) {
            throw new ApiException(ApiException.RUNTIME_ERROR, "[Json] " + e.getMessage());
        }

    }
    public JsonWrapper(JSONObject json) {
        this.json = json;
    }
    public JsonWrapper(JSONArray json) {
        this.jsonArrayOk = json;
    }

    public void setJsonArrayOk(JSONArray jsonArrayOk) {
        this.jsonArrayOk = jsonArrayOk;
    }

    public JSONArray getJsonArrayOk() {
        return jsonArrayOk;
    }

    private void checkMandatoryField(String name) {
        if (!json.containsKey(name)) {
            throw new ApiException(ApiException.RUNTIME_ERROR,
                    "[Json] Get json item field: " + name + " does not exist");
        }
    }

    public boolean containKey(String name) {
        return json.containsKey(name);
    }

    public String getString(String name) {
        checkMandatoryField(name);
        try {
            return json.getString(name);
        } catch (Exception e) {
            throw new ApiException(ApiException.RUNTIME_ERROR,
                    "[Json] Get string error: " + name + " " + e.getMessage());
        }
    }

    public String getStringOrDefault(String name, String def) {
        if (!containKey(name)) {
            return def;
        }
        return getString(name);
    }

    public boolean getBoolean(String name) {
        checkMandatoryField(name);
        try {
            return json.getBoolean(name);
        } catch (Exception e) {
            throw new ApiException(ApiException.RUNTIME_ERROR,
                    "[Json] Get boolean error: " + name + " " + e.getMessage());
        }
    }

    public int getInteger(String name) {
        checkMandatoryField(name);
        try {
            return json.getInteger(name);
        } catch (Exception e) {
            throw new ApiException(ApiException.RUNTIME_ERROR,
                    "[Json] Get integer error: " + name + " " + e.getMessage());
        }
    }

    public Integer getIntegerOrDefault(String name, Integer defValue) {
        try {
            if (!containKey(name)) {
                return defValue;
            }
            return json.getInteger(name);
        } catch (Exception e) {
            throw new ApiException(ApiException.RUNTIME_ERROR,
                    "[Json] Get integer error: " + name + " " + e.getMessage());
        }
    }

    public long getLong(String name) {
        checkMandatoryField(name);
        try {
            return json.getLong(name);
        } catch (Exception e) {
            throw new ApiException(ApiException.RUNTIME_ERROR,
                    "[Json] Get long error: " + name + " " + e.getMessage());
        }
    }

    public long getLongOrDefault(String name, long defValue) {
        try {
            if (!containKey(name)) {
                return defValue;
            }
            return json.getLong(name);
        } catch (Exception e) {
            throw new ApiException(ApiException.RUNTIME_ERROR,
                    "[Json] Get long error: " + name + " " + e.getMessage());
        }
    }

    public BigDecimal getBigDecimal(String name) {
        checkMandatoryField(name);
        try {
            return new BigDecimal(json.getBigDecimal(name).stripTrailingZeros().toPlainString());
        } catch (Exception e) {
            throw new ApiException(ApiException.RUNTIME_ERROR,
                    "[Json] Get decimal error: " + name + " " + e.getMessage());
        }
    }

    public BigDecimal getBigDecimalOrDefault(String name, BigDecimal defValue) {
        if (!containKey(name)) {
            return defValue;
        }
        try {
            return new BigDecimal(json.getBigDecimal(name).stripTrailingZeros().toPlainString());
        } catch (Exception e) {
            throw new ApiException(ApiException.RUNTIME_ERROR,
                    "[Json] Get decimal error: " + name + " " + e.getMessage());
        }
    }

    public JsonWrapper getJsonObject(String name) {
        checkMandatoryField(name);
        return new JsonWrapper(json.getJSONObject(name));
    }

    public void getJsonObject(String name, Handler<JsonWrapper> todo) {
        checkMandatoryField(name);
        todo.handle(new JsonWrapper(json.getJSONObject(name)));
    }

    public JsonWrapperArray getJsonArray(String name) {
        checkMandatoryField(name);
        JSONArray array = null;
        try {
            array = json.getJSONArray(name);
        } catch (Exception e) {
            throw new ApiException(ApiException.RUNTIME_ERROR,
                    "[Json] Get array: " + name + " error");
        }
        if (array == null) {
            throw new ApiException(ApiException.RUNTIME_ERROR,
                    "[Json] Array: " + name + " does not exist");
        }
        return new JsonWrapperArray(array);
    }

    public JSONObject getJson(){
        return json;
    }
}
