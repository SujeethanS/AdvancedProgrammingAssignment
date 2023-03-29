package com.controller;

import com.dto.dashboard.Request.DashboardSummaryReq;
import com.dto.order.Request.GetAllOrderReq;
import com.dto.order.Request.OrderDetailsReq;
import com.dto.order.Request.OrderReq;
import com.dto.order.Request.PaymentReq;
import com.dto.response.LoanOfferResponse;

public interface OrderController {

    /**
     * getOrderSummary
     * @param dashboardSummaryReq
     * @return
     */
    public LoanOfferResponse getOrderSummary(DashboardSummaryReq dashboardSummaryReq);

    /**
     * createOrder
     * @param orderReq
     * @return
     */
    public LoanOfferResponse createOrder(OrderReq orderReq);

    /**
     * getAllOrders
     * @param getAllOrderReq
     * @return
     */
    public LoanOfferResponse getAllOrders(GetAllOrderReq getAllOrderReq);

    /**
     * getAllOrderDetails
     * @param orderDetailsReq
     * @return
     */
    public LoanOfferResponse getAllOrderDetails(OrderDetailsReq orderDetailsReq);

    /**
     * getAllPaymentDetails
     * @param paymentReq
     * @return
     */
    public LoanOfferResponse getAllPaymentDetails(PaymentReq paymentReq);
}
