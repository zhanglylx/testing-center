package com.testing.center.common.utils.ZLYJSON;

import com.testing.center.common.utils.http.HttpUtils;
import net.sf.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

public class ZLYJSONObject {
    private JSONObject jsonObject;

    public ZLYJSONObject(JSONObject zlyjsonObject) {
        this.jsonObject = zlyjsonObject;
    }

    public ZLYJSONObject() {
    }

    public static ZLYJSONObject fromObject(Object o) {
        return new ZLYJSONObject(JSONObject.fromObject(o));
    }

    public String getString(String key) {
        if (this.jsonObject.getString(key).equals("null")) {
            return null;
        } else if (this.jsonObject.getString(key).equals("\"null\"")) {
            return "null";
        } else {
            return this.jsonObject.getString(key);
        }
    }

    public String optString(String key, String defaultValue) {
        try {
            return this.getString(key);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public String optString(String key) {
        return this.optString(key, null);
    }

    public Integer getInt(String key) {
        return this.jsonObject.getInt(key);
    }

    public Object get(String key) {
        return this.getString(key);
    }

    public Object get(Object key) {
        return this.get(key.toString());
    }

    public Object opt(String key, String defaultValue) {
        return this.optString(key, defaultValue);
    }

    public Object opt(String key) {
        return opt(key, null);
    }

    public Object opt(Object key) {
        return opt(key.toString());
    }


    public boolean getBoolean(String key) {
        return this.jsonObject.getBoolean(key);
    }

    public Boolean optBoolean(String key, Boolean defaultValue) {
        if (defaultValue == null) {
            try {
                return this.jsonObject.getBoolean(key);
            } catch (Exception e) {
                return null;
            }
        }
        return this.jsonObject.optBoolean(key, defaultValue);
    }

    public Long getLong(String key) {
        return this.jsonObject.getLong(key);
    }

    public Double getDouble(String key) {
        return this.jsonObject.getDouble(key);
    }

    public ZLYJSONObject getJSONObject(String key) {
        return ZLYJSONObject.fromObject(this.jsonObject.getJSONObject(key));
    }

    public boolean isNullObject() {
        return this.jsonObject.isNullObject();
    }

    public ZLYJSONObject optJSONObject(String key) {
        return this.jsonObject.optJSONObject(key) != null ? ZLYJSONObject.fromObject(this.jsonObject.optJSONObject(key)) : null;
    }

    public ZLYJSONArray optJSONArray(String key) {
        return this.jsonObject.optJSONArray(key) != null ? new ZLYJSONArray(this.jsonObject.optJSONArray(key)) : null;
    }


    public ZLYJSONArray getJSONArray(String key) {
        return new ZLYJSONArray(jsonObject.getJSONArray(key));
    }


    public Long optLong(String key, Long defaultValue) {
        try {
            return this.jsonObject.getLong(key);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public Integer optInt(String key) {
        return optInt(key, null);
    }

    public Integer optInt(String key, Integer defaultValue) {
        try {
            return this.jsonObject.getInt(key);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public String toHttpQuerys() {
        Map<String, String> map = new HashMap<>();
        //map迭代器
        Object o;
        for (Iterator iterator =
             this.jsonObject.keys()
             ; iterator.hasNext(); ) {
            o = iterator.next();
            map.put(o.toString(), this.getString(o.toString()));
        }
        try {
            return HttpUtils.toHttpGetParams(map);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isEmpty() {
        return this.jsonObject.isEmpty();
    }

    public boolean isArray() {
        return this.jsonObject.isArray();
    }

    @Override
    public String toString() {
        return this.jsonObject.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ZLYJSONObject that = (ZLYJSONObject) o;
        return Objects.equals(jsonObject, that.jsonObject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jsonObject);
    }
}
