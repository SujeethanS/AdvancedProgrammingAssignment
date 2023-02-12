package com.dto.response;

public class GeneralResponse {

    private boolean res;
    private int statusCode;
    private String msg;

    public GeneralResponse() {
    }

    public boolean isRes() {
        return res;
    }

    public void setRes(boolean res) {
        this.res = res;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "GeneralResponse{" +
                "res=" + res +
                ", statusCode=" + statusCode +
                ", msg='" + msg + '\'' +
                '}';
    }

}
