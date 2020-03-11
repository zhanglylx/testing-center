package com.testing.center.entity;

public class ServerBean {
    private Integer _testingCenterRequestServerResponseStatusCode;
    private Object _testingCenterRequestUri;
    private String _testingCenterRequestMethod;
    private String _testingCenterRequestHeaders;
    private String _testingCenterRequestBody;

    public void setPostUriHeadersBody(Object uri, Object headers, Object body) {
        setHeaderObjcet(headers);
        setBodyObjcet(body);
        setRequestMethodGet();
        set_testingCenterRequestUri(uri);
    }

    public void setGetUriHeadersBody(Object uri, Object headers, Object body) {
        setHeaderObjcet(headers);
        setBodyObjcet(body);
        setRequestMethodGet();
        set_testingCenterRequestUri(uri);
    }

    public void setBodyObjcet(Object object) {
        set_testingCenterRequestBody(object.toString());
    }

    public void setHeaderObjcet(Object object) {
        set_testingCenterRequestHeaders(object.toString());
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

    public String get_testingCenterRequestHeaders() {
        return _testingCenterRequestHeaders;
    }

    public void set_testingCenterRequestHeaders(String _testingCenterRequestHeaders) {
        this._testingCenterRequestHeaders = _testingCenterRequestHeaders;
    }

    public String get_testingCenterRequestBody() {
        return _testingCenterRequestBody;
    }

    public void set_testingCenterRequestBody(String _testingCenterRequestBody) {
        this._testingCenterRequestBody = _testingCenterRequestBody;
    }

    public ServerBean() {
        this._testingCenterRequestServerResponseStatusCode = 200;
    }

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
