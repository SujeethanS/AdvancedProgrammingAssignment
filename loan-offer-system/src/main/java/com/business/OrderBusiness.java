package com.business;

import com.dto.dashboard.Request.DashboardSummaryReq;
import com.dto.dashboard.Response.DashboardSummaryRes;
import com.dto.order.Request.GetAllOrderReq;
import com.dto.order.Request.OrderDetailsReq;
import com.dto.order.Request.OrderReq;
import com.dto.order.Request.PaymentReq;
import com.dto.order.response.GetAllOrderRes;
import com.dto.order.response.OrderDetailRes;
import com.dto.order.response.PaymentRes;
import com.dto.response.GeneralResponse;

import java.util.List;

public interface OrderBusiness {
    /**
     * getOrderSummary
     * @param dashboardSummaryReq
     * @return
     */
    DashboardSummaryRes getOrderSummary(DashboardSummaryReq dashboardSummaryReq);

    /**
     * createOrder
     * @param orderReq
     * @return
     */
    GeneralResponse createOrder(OrderReq orderReq);

    /***
     * getAllOrders
     * @param getAllOrderReq
     * @return
     */
    List<GetAllOrderRes> getAllOrders(GetAllOrderReq getAllOrderReq);

    /**
     * getAllOrderDetails
     * @param orderDetailsReq
     * @return
     */
    List<OrderDetailRes> getAllOrderDetails(OrderDetailsReq orderDetailsReq);

    /**
     * getAllPaymentDetails
     * @param paymentReq
     * @return
     */
    List<PaymentRes> getAllPaymentDetails(PaymentReq paymentReq);
}
