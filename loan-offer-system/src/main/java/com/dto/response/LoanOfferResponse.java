package com.dto.response;

public class LoanOfferResponse {

    private Object value;
    private int status;
    private String msg;

    public LoanOfferResponse() {
    }

    public LoanOfferResponse(Object value, int status, String msg) {
        this.value = value;
        this.status = status;
        this.msg = msg;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
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

    public static LoanOfferResponse generateResponse(Object value,int status,String msg){
        return new LoanOfferResponse(value,status,msg);
    }

}
