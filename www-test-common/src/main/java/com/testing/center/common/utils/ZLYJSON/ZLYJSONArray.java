package com.testing.center.common.utils.ZLYJSON;

import net.sf.json.JSONArray;

import java.util.Objects;

public class ZLYJSONArray {
    private JSONArray jsonArray;

    public ZLYJSONArray() {

    }

    public Object get(int index) {

        return this.jsonArray.get(index);
    }

    public Boolean getBoolean(int index) {

        return this.jsonArray.getBoolean(index);
    }

    public Double getDouble(int index) {

        return this.jsonArray.getDouble(index);
    }

    public String getString(int index) {
        return this.jsonArray.getString(index);
    }


    public String toString() {
        return this.jsonArray.toString();
    }

    public String toString(int indentFactor) {
        return this.jsonArray.toString(indentFactor);
    }

    public String toString(int indentFactor, int indent) {

        return this.jsonArray.toString(indentFactor, indent);
    }

    public Long getLong(int index) {
        return this.jsonArray.getLong(index);
    }


    public ZLYJSONArray(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
    }

    public ZLYJSONObject getJSONObject(int index) {
        return new ZLYJSONObject(this.jsonArray.getJSONObject(index));
    }

    public Integer size() {
        return this.jsonArray.size();
    }

    public ZLYJSONArray getJSONArray(int index) {
        return new ZLYJSONArray(this.jsonArray.getJSONArray(index));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ZLYJSONArray that = (ZLYJSONArray) o;
        return Objects.equals(jsonArray, that.jsonArray);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jsonArray);
    }
}
