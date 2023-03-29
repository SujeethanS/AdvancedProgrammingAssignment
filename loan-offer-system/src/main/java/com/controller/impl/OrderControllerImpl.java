package com.controller.impl;

import com.business.OrderBusiness;
import com.controller.OrderController;
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
import com.dto.response.LoanOfferResponse;
import com.util.ApplicationConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.logging.Logger;

@RestController
@CrossOrigin(origins = "*")
public class OrderControllerImpl implements OrderController {
    Logger logger = Logger.getLogger(this.getClass().getName());
    @Autowired
    OrderBusiness orderBusiness;
    @Override
    @PostMapping("/get/order/summary")
    public LoanOfferResponse getOrderSummary(@RequestBody DashboardSummaryReq dashboardSummaryReq) {
        logger.info("OrderControllerImpl-getOrderSummary-initiated");
        DashboardSummaryRes dashboardSummaryRes = orderBusiness.getOrderSummary(dashboardSummaryReq);
        return LoanOfferResponse.generateResponse(
                dashboardSummaryRes,
                ApplicationConstant.SuccessStatusCode,
                ApplicationConstant.SuccessMsg);
    }
    @Override
    @PostMapping("/create/new/order")
    public LoanOfferResponse createOrder(@RequestBody OrderReq orderReq) {
        logger.info("OrderControllerImpl-createOrder-initiated");
        GeneralResponse response = orderBusiness.createOrder(orderReq);
        return LoanOfferResponse.generateResponse(null,response.getStatusCode(),response.getMsg());
    }
    @Override
    @PostMapping("/get/all/order")
    public LoanOfferResponse getAllOrders(@RequestBody GetAllOrderReq getAllOrderReq) {
        logger.info("OrderControllerImpl-getAllOrders-initiated");
        List<GetAllOrderRes> getAllOrderRes = orderBusiness.getAllOrders(getAllOrderReq);
        return LoanOfferResponse.generateResponse(
                getAllOrderRes,
                ApplicationConstant.SuccessStatusCode,
                ApplicationConstant.SuccessMsg);
    }
    @Override
    @PostMapping("/get/all/order/details")
    public LoanOfferResponse getAllOrderDetails(@RequestBody OrderDetailsReq orderDetailsReq) {
        logger.info("OrderControllerImpl-getAllOrderDetails-initiated");
        List<OrderDetailRes> getAllOrderRes = orderBusiness.getAllOrderDetails(orderDetailsReq);
        return LoanOfferResponse.generateResponse(
                getAllOrderRes,
                ApplicationConstant.SuccessStatusCode,
                ApplicationConstant.SuccessMsg);
    }
    @Override
    @PostMapping("/get/all/payment/details")
    public LoanOfferResponse getAllPaymentDetails(@RequestBody PaymentReq paymentReq) {
        logger.info("OrderControllerImpl-getAllPaymentDetails-initiated");
        List<PaymentRes> getAllOrderRes = orderBusiness.getAllPaymentDetails(paymentReq);
        return LoanOfferResponse.generateResponse(
                getAllOrderRes,
                ApplicationConstant.SuccessStatusCode,
                ApplicationConstant.SuccessMsg);
    }
}
