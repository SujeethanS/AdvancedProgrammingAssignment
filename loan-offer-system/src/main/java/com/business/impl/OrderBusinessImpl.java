package com.business.impl;

import com.business.OrderBusiness;
import com.dao.OrderDAO;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class OrderBusinessImpl implements OrderBusiness {

    Logger logger = Logger.getLogger(this.getClass().getName());

    @Autowired
    OrderDAO orderDAO;

    @Override
    public DashboardSummaryRes getOrderSummary(DashboardSummaryReq dashboardSummaryReq) {
        logger.info("OrderBusinessImpl-getOrderSummary-initiated");
        return orderDAO.getOrderSummary(dashboardSummaryReq);
    }

    @Override
    public GeneralResponse createOrder(OrderReq orderReq) {
        logger.info("OrderBusinessImpl-getOrderSummary-initiated");
        return orderDAO.createOrder(orderReq);
    }

    @Override
    public List<GetAllOrderRes> getAllOrders(GetAllOrderReq getAllOrderReq) {
        logger.info("OrderBusinessImpl-getAllOrders-initiated");
        return orderDAO.getAllOrders(getAllOrderReq);
    }

    @Override
    public List<OrderDetailRes> getAllOrderDetails(OrderDetailsReq orderDetailsReq) {
        logger.info("OrderBusinessImpl-getAllOrderDetails-initiated");
        return orderDAO.getAllOrderDetails(orderDetailsReq);
    }

    @Override
    public List<PaymentRes> getAllPaymentDetails(PaymentReq paymentReq) {
        logger.info("OrderBusinessImpl-getAllPaymentDetails-initiated");
        return orderDAO.getAllPaymentDetails(paymentReq);
    }
}
