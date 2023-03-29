package com.example.customerOrderApp.pojo;

import java.util.List;

public class Order {

    private String OrderId;
    private String OrderDate;
    private String OrderCashier = "Admin";
    private String OrderStatus = "1002"; //for kitchen order
    private int cashierId =1;
    private int OrderPaymentMethod = 1001;
    private Double OrderPaymentReceived = 0.0;
    private Double OrderReturnCash = 0.0;
    private Double OrderUserPayment = 0.0;
    private Double OrderSubTotal = 0.0;
    private Double OrderItemDiscountTotal = 0.0;
    private Double OrderCreditDiscount = 0.0;
    private Double OrderVatTotal = 0.0;
    private Double OrderTotal = 0.0;
    private Double OrderDiscount = 0.0;
    private int OrderCustomerId = 0;
    private String OrderType = "Table";
    private int OrderSyncStatus = 1;
    private String OrderResPaymentType = "Table";
    private Double OrderResCharge = 0.0;
    private int OrderResTableId = 1;
    private int OrderCardId = 1;
    private Double OrderCardPoints = 0.0;
    private Double OrderCardTakePoints = 0.0;
    private Double OrderAdvancePayment = 0.0;
    private List<OrderDetails> orderDetails;
    private String companyId ;
    private String clintId;
    private OrderDetails[] orderDetailsList;
    private Double discountForTotal=0.0;
    private String returnId = "0";
    private String returnTempId = "0";
    private String cashierName = "admin";
    private String orderStatusString = "getOrderStatus";
    private String dueDate = "n/l";
    private Double serviceCharge = 0.0;

    private String customerName = "";

    private Double balanceDue = 0.0;

    public Order(String orderId, String orderDate, String orderCashier, String orderStatus, int cashierId, int orderPaymentMethod, Double orderPaymentReceived, Double orderReturnCash, Double orderUserPayment, Double orderSubTotal, Double orderItemDiscountTotal, Double orderCreditDiscount, Double orderVatTotal, Double orderTotal, Double orderDiscount, int orderCustomerId, String orderType, int orderSyncStatus, String orderResPaymentType, Double orderResCharge, int orderResTableId, int orderCardId, Double orderCardPoints, Double orderCardTakePoints, Double orderAdvancePayment, List<OrderDetails> orderDetails, String companyId, OrderDetails[] orderDetailsList, Double discountForTotal, String customerName) {
        OrderId = orderId;
        OrderDate = orderDate;
        OrderCashier = orderCashier;
        OrderStatus = orderStatus;
        this.cashierId = cashierId;
        OrderPaymentMethod = orderPaymentMethod;
        OrderPaymentReceived = orderPaymentReceived;
        OrderReturnCash = orderReturnCash;
        OrderUserPayment = orderUserPayment;
        OrderSubTotal = orderSubTotal;
        OrderItemDiscountTotal = orderItemDiscountTotal;
        OrderCreditDiscount = orderCreditDiscount;
        OrderVatTotal = orderVatTotal;
        OrderTotal = orderTotal;
        OrderDiscount = orderDiscount;
        OrderCustomerId = orderCustomerId;
        OrderType = orderType;
        OrderSyncStatus = orderSyncStatus;
        OrderResPaymentType = orderResPaymentType;
        OrderResCharge = orderResCharge;
        OrderResTableId = orderResTableId;
        OrderCardId = orderCardId;
        OrderCardPoints = orderCardPoints;
        OrderCardTakePoints = orderCardTakePoints;
        OrderAdvancePayment = orderAdvancePayment;
        this.orderDetails = orderDetails;
        this.companyId = companyId;
        this.orderDetailsList = orderDetailsList;
        this.discountForTotal = discountForTotal;
        this.customerName = customerName;
    }

    public Order(String orderId, String orderDate, String orderCashier, String orderStatus, int orderPaymentMethod, Double orderPaymentReceived, Double orderReturnCash, Double orderUserPayment, Double orderSubTotal, Double orderItemDiscountTotal, Double orderCreditDiscount, Double orderVatTotal, Double orderTotal, Double orderDiscount, int orderCustomerId, String orderType, int orderSyncStatus, String orderResPaymentType, Double orderResCharge, int orderResTableId, int orderCardId, Double orderCardPoints, Double orderCardTakePoints, Double orderAdvancePayment, String companyId , String clintId) {
        OrderId = orderId;
        OrderDate = orderDate;
        OrderCashier = orderCashier;
        OrderStatus = orderStatus;
        OrderPaymentMethod = orderPaymentMethod;
        OrderPaymentReceived = orderPaymentReceived;
        OrderReturnCash = orderReturnCash;
        OrderUserPayment = orderUserPayment;
        OrderSubTotal = orderSubTotal;
        OrderItemDiscountTotal = orderItemDiscountTotal;
        OrderCreditDiscount = orderCreditDiscount;
        OrderVatTotal = orderVatTotal;
        OrderTotal = orderTotal;
        OrderDiscount = orderDiscount;
        OrderCustomerId = orderCustomerId;
        OrderType = orderType;
        OrderSyncStatus = orderSyncStatus;
        OrderResPaymentType = orderResPaymentType;
        OrderResCharge = orderResCharge;
        OrderResTableId = orderResTableId;
        OrderCardId = orderCardId;
        OrderCardPoints = orderCardPoints;
        OrderCardTakePoints = orderCardTakePoints;
        OrderAdvancePayment = orderAdvancePayment;
        this.companyId = companyId;
        this.clintId =clintId;
    }

    public Order() {

    }

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }

    public String getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(String orderDate) {
        OrderDate = orderDate;
    }

    public String getOrderCashier() {
        return OrderCashier;
    }

    public void setOrderCashier(String orderCashier) {
        OrderCashier = orderCashier;
    }

    public String getOrderStatus() {
        return OrderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        OrderStatus = orderStatus;
    }

    public int getOrderPaymentMethod() {
        return OrderPaymentMethod;
    }

    public void setOrderPaymentMethod(int orderPaymentMethod) {
        OrderPaymentMethod = orderPaymentMethod;
    }

    public Double getOrderPaymentReceived() {
        return OrderPaymentReceived;
    }

    public void setOrderPaymentReceived(Double orderPaymentReceived) {
        OrderPaymentReceived = orderPaymentReceived;
    }

    public Double getOrderReturnCash() {
        return OrderReturnCash;
    }

    public void setOrderReturnCash(Double orderReturnCash) {
        OrderReturnCash = orderReturnCash;
    }

    public Double getOrderUserPayment() {
        return OrderUserPayment;
    }

    public void setOrderUserPayment(Double orderUserPayment) {
        OrderUserPayment = orderUserPayment;
    }

    public Double getOrderSubTotal() {
        return OrderSubTotal;
    }

    public void setOrderSubTotal(Double orderSubTotal) {
        OrderSubTotal = orderSubTotal;
    }

    public Double getOrderItemDiscountTotal() {
        return OrderItemDiscountTotal;
    }

    public void setOrderItemDiscountTotal(Double orderItemDiscountTotal) {
        OrderItemDiscountTotal = orderItemDiscountTotal;
    }

    public Double getOrderCreditDiscount() {
        return OrderCreditDiscount;
    }

    public void setOrderCreditDiscount(Double orderCreditDiscount) {
        OrderCreditDiscount = orderCreditDiscount;
    }

    public Double getOrderVatTotal() {
        return OrderVatTotal;
    }

    public void setOrderVatTotal(Double orderVatTotal) {
        OrderVatTotal = orderVatTotal;
    }

    public Double getOrderTotal() {
        return OrderTotal;
    }

    public void setOrderTotal(Double orderTotal) {
        OrderTotal = orderTotal;
    }

    public Double getOrderDiscount() {
        return OrderDiscount;
    }

    public void setOrderDiscount(Double orderDiscount) {
        OrderDiscount = orderDiscount;
    }

    public int getOrderCustomerId() {
        return OrderCustomerId;
    }

    public void setOrderCustomerId(int orderCustomerId) {
        OrderCustomerId = orderCustomerId;
    }

    public String getOrderType() {
        return OrderType;
    }

    public void setOrderType(String orderType) {
        OrderType = orderType;
    }

    public int getOrderSyncStatus() {
        return OrderSyncStatus;
    }

    public void setOrderSyncStatus(int orderSyncStatus) {
        OrderSyncStatus = orderSyncStatus;
    }

    public String getOrderResPaymentType() {
        return OrderResPaymentType;
    }

    public void setOrderResPaymentType(String orderResPaymentType) {
        OrderResPaymentType = orderResPaymentType;
    }

    public Double getOrderResCharge() {
        return OrderResCharge;
    }

    public void setOrderResCharge(Double orderResCharge) {
        OrderResCharge = orderResCharge;
    }

    public int getOrderResTableId() {
        return OrderResTableId;
    }

    public void setOrderResTableId(int orderResTableId) {
        OrderResTableId = orderResTableId;
    }

    public int getOrderCardId() {
        return OrderCardId;
    }

    public void setOrderCardId(int orderCardId) {
        OrderCardId = orderCardId;
    }

    public Double getOrderCardPoints() {
        return OrderCardPoints;
    }

    public void setOrderCardPoints(Double orderCardPoints) {
        OrderCardPoints = orderCardPoints;
    }

    public Double getOrderCardTakePoints() {
        return OrderCardTakePoints;
    }

    public void setOrderCardTakePoints(Double orderCardTakePoints) {
        OrderCardTakePoints = orderCardTakePoints;
    }

    public Double getOrderAdvancePayment() {
        return OrderAdvancePayment;
    }

    public void setOrderAdvancePayment(Double orderAdvancePayment) {
        OrderAdvancePayment = orderAdvancePayment;
    }

    public void getOrderPaymentReceived(double aDouble) {

    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }


    public void setOrderDetails(List<OrderDetails> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public List<OrderDetails> getOrderDetails() {
        return orderDetails;
    }

    public OrderDetails[] getOrderDetailsList() {
        return orderDetailsList;
    }


    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getCashierId() {
        return cashierId;
    }

    public void setCashierId(int cashierId) {
        this.cashierId = cashierId;
    }

    public void setOrderDetailsList(OrderDetails[] orderDetailsList) {
        this.orderDetailsList = orderDetailsList;
    }

    public Double getDiscountForTotal() {
        return discountForTotal;
    }

    public void setDiscountForTotal(Double discountForTotal) {
        this.discountForTotal = discountForTotal;
    }

    public String getClintId() {
        return clintId;
    }

    public void setClintId(String clintId) {
        this.clintId = clintId;
    }

    public String getReturnId() {
        return returnId;
    }

    public void setReturnId(String returnId) {
        this.returnId = returnId;
    }

    public String getCashierName() {
        return cashierName;
    }

    public void setCashierName(String cashierName) {
        this.cashierName = cashierName;
    }

    public String getOrderStatusString() {
        return orderStatusString;
    }

    public void setOrderStatusString(String orderStatusString) {
        this.orderStatusString = orderStatusString;
    }

    public String getReturnTempId() {
        return returnTempId;
    }

    public void setReturnTempId(String returnTempId) {
        this.returnTempId = returnTempId;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public Double getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(Double serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    public Double getBalanceDue() {
        return balanceDue;
    }

    public void setBalanceDue(Double balanceDue) {
        this.balanceDue = balanceDue;
    }
}
