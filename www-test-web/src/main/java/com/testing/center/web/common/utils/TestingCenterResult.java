package com.testing.center.web.common.utils;

import org.apache.commons.lang3.StringUtils;

public class TestingCenterResult<T> {
    private int status;
    private String msg;
    private T data;

    @Override
    public String toString() {
        return "TestingCenterResult{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    /**
     * 通用错误
     *
     * @param msg
     * @return
     */
    public TestingCenterResult<T> errorCommon(String msg) {
        this.setMsg(msg);
        this.setStatus(1);
        return this;
    }

    public TestingCenterResult<T> errorCommon(String msg,Exception exception) {
        this.setMsg(msg+":"+exception.getLocalizedMessage());
        this.setStatus(1);
        return this;
    }
    public TestingCenterResult<T> setSuccess(T t) {
        return setSuccess(null, t);
    }

    /**
     * 通用错误
     *
     * @param msg
     * @return
     */
    public TestingCenterResult<T> setSuccess(String msg, T t) {
        this.setMsg(msg);
        this.setStatus(0);
        this.setData(t);
        return this;
    }

    /**
     * 自动拼接错误参数提示文案：参数[ msg ]不能为空
     *
     * @param msg
     * @return
     */
    public TestingCenterResult<T> errorParameterDefaultNull(String msg) {
        if (StringUtils.isBlank(msg)) {
            msg = "";
        } else {
            msg = "[ " + msg + " ]";
        }
        return errorParameter("参数" + msg + "不能为空");
    }

    /**
     * 参数错误响应格式
     *
     * @param msg
     * @return
     */
    public TestingCenterResult<T> errorParameter(String msg) {
        this.setMsg(msg);
        this.setStatus(2);
        return this;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
