package com.dto.response;

public class CommonResponse extends GeneralResponse{
    private Object value;

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
