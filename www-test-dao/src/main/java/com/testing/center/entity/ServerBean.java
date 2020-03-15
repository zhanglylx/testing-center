package com.testing.center.entity;

import com.testing.center.cmmon.utils.http.NetworkHeaders;
import sun.plugin2.message.Serializer;

import java.io.Serializable;
import java.util.Map;

public abstract class ServerBean implements Serializable {
    private Integer _testingCenterRequestServerResponseStatusCode;
    private Object _testingCenterRequestUri;
    private String _testingCenterRequestMethod;
    private Map<String, Object> _testingCenterRequestHeaders;
    private Map<String, Object> _testingCenterRequestBody;
    private String _testingCenterRequestMsg;

    public String get_testingCenterRequestMsg() {
        return _testingCenterRequestMsg;
    }

    public void set_testingCenterRequestMsg(String _testingCenterRequestMsg) {
        this._testingCenterRequestMsg = _testingCenterRequestMsg;
    }

    public void set_testingCenterRequestMsg(String _testingCenterRequestMsg, Exception e) {
        this._testingCenterRequestMsg = _testingCenterRequestMsg + "【" + e.getLocalizedMessage() + "】";
    }

    public void set_testingCenterRequestMsg(Exception e) {
        set_testingCenterRequestMsg("发生了不可预知的错误", e);
    }

    public void setPostUriHeadersBody(Object uri, Map<String, Object> headers, Map<String, Object> body) {
        set_testingCenterRequestHeaders(headers);
        set_testingCenterRequestBody(body);
        setRequestMethodGet();
        set_testingCenterRequestUri(uri);
    }

    public void setGetUriHeadersBody(Object uri, Map<String, Object> headers, Map<String, Object> body) {
        set_testingCenterRequestHeaders(headers);
        set_testingCenterRequestBody(body);
        setRequestMethodGet();
        set_testingCenterRequestUri(uri);
    }


    public void setRequestMethodGet() {
        set_testingCenterRequestMethod("GET");
    }

    public void setRequestMethodPost() {
        set_testingCenterRequestMethod("POST");
    }

    public String get_testingCenterRequestMethod() {
        return _testingCenterRequestMethod;
    }

    public void set_testingCenterRequestMethod(String _testingCenterRequestMethod) {
        this._testingCenterRequestMethod = _testingCenterRequestMethod;
    }

    public Map<String, Object> get_testingCenterRequestHeaders() {
        return _testingCenterRequestHeaders;
    }

    public void set_testingCenterRequestHeaders(Map<String, Object> _testingCenterRequestHeaders) {
        this._testingCenterRequestHeaders = _testingCenterRequestHeaders;
    }

    public Map<String, Object> get_testingCenterRequestBody() {
        return _testingCenterRequestBody;
    }

    public void set_testingCenterRequestBody(Map<String, Object> _testingCenterRequestBody) {
        this._testingCenterRequestBody = _testingCenterRequestBody;
    }

//    public ServerBean() {
//        this._testingCenterRequestServerResponseStatusCode = 200;
//    }

    public Object get_testingCenterRequestUri() {
        return _testingCenterRequestUri;
    }

    public void set_testingCenterRequestUri(Object _testingCenterRequestUri) {
        this._testingCenterRequestUri = _testingCenterRequestUri;
    }

    public Integer get_testingCenterRequestServerResponseStatusCode() {
        return _testingCenterRequestServerResponseStatusCode;
    }

    public void set_testingCenterRequestServerResponseStatusCode(Integer _testingCenterRequestServerResponseStatusCode) {
        this._testingCenterRequestServerResponseStatusCode = _testingCenterRequestServerResponseStatusCode;
    }
}
