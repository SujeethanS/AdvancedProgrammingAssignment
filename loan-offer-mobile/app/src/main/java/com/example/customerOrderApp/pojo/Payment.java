package com.example.customerOrderApp.pojo;

public class Payment {
    private int payment_type = 1002;//     paymenttype_invoice
    private int payment_id = 0;//        0
    private int invoice_number = 0;//    1029
    private String received_by = "";//       "100220"
    private int payment_method = 1001;//    paymentmethod_cash
    private double payment_total = 0.0;//     150.50
    private double payment_subtotal = 0.0;//     150.50
    private double payment_discount = 0.0;//  0.50
    private String payment_status = "paid";//    "paid"
    private String payment_number = "************0000";//    "xxxxxxxxxx4444"
    private String customer_id = "0";//       "147"
    private String customer_name = "n/l";//       "sujeethan"
    private String description = "this bill needs to be collected ontime";//       "this bill needs to be collected ontime"
    private String paymentDate = "n/l";//       "2019-05-10"
    private Double userPayment=0.0;
    private Double balance=0.0;
    private Double receivedAmount =0.0;

    private double payment_opening_balance = 0.0;//  0.50

    public int getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(int payment_type) {
        this.payment_type = payment_type;
    }

    public int getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(int payment_id) {
        this.payment_id = payment_id;
    }

    public int getInvoice_number() {
        return invoice_number;
    }

    public void setInvoice_number(int invoice_number) {
        this.invoice_number = invoice_number;
    }

    public String getReceived_by() {
        return received_by;
    }

    public void setReceived_by(String received_by) {
        this.received_by = received_by;
    }

    public int getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(int payment_method) {
        this.payment_method = payment_method;
    }

    public double getPayment_total() {
        return payment_total;
    }

    public void setPayment_total(double payment_total) {
        this.payment_total = payment_total;
    }

    public double getPayment_discount() {
        return payment_discount;
    }

    public void setPayment_discount(double payment_discount) {
        this.payment_discount = payment_discount;
    }

    public String getPayment_status() {
        return payment_status;
    }

    public void setPayment_status(String payment_status) {
        this.payment_status = payment_status;
    }

    public String getPayment_number() {
        return payment_number;
    }

    public void setPayment_number(String payment_number) {
        this.payment_number = payment_number;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public double getPayment_subtotal() {
        return payment_subtotal;
    }

    public void setPayment_subtotal(double payment_subtotal) {
        this.payment_subtotal = payment_subtotal;
    }

    public double getPayment_opening_balance() {
        return payment_opening_balance;
    }

    public void setPayment_opening_balance(double payment_opening_balance) {
        this.payment_opening_balance = payment_opening_balance;
    }

    public Double getUserPayment() {
        return userPayment;
    }

    public void setUserPayment(Double userPayment) {
        this.userPayment = userPayment;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Double getReceivedAmount() {
        return receivedAmount;
    }

    public void setReceivedAmount(Double receivedAmount) {
        this.receivedAmount = receivedAmount;
    }
}
