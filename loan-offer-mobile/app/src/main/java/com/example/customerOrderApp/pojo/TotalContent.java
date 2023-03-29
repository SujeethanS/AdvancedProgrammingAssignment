package com.example.customerOrderApp.pojo;

public class TotalContent {

    private Double sum = 0.0;

    public TotalContent(){

    }

    public TotalContent(Double sum){
        this.sum = sum;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }
}
