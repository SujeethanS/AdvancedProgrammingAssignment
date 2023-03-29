package com.dao.impl;

import com.dao.OfferDAOConstant;
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
import com.dto.product.Response.BrandRes;
import com.dto.response.GeneralResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

@Repository
public class OrderDAOImpl implements OrderDAO {
    Logger logger = Logger.getLogger(this.getClass().getName());
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Override
    public DashboardSummaryRes getOrderSummary(DashboardSummaryReq dashboardSummaryReq) {
        long startTime = System.currentTimeMillis();
        DashboardSummaryRes response = new DashboardSummaryRes();
        Connection connection = null;
        CallableStatement callableStatement;
        try {
            logger.info("getOrderSummary-request------------------>"+ dashboardSummaryReq.toString());

            connection = DataSourceUtils.getConnection(Objects.requireNonNull(jdbcTemplate.getDataSource()));
            callableStatement = connection.prepareCall(OfferDAOConstant.UserConstant.GET_DASHBOARD_SUMMARY);
            callableStatement.setObject(1, dashboardSummaryReq.getUserId(), Types.INTEGER);
            callableStatement.registerOutParameter(2,Types.INTEGER);
            callableStatement.registerOutParameter(3,Types.DOUBLE);
            callableStatement.registerOutParameter(4,Types.INTEGER);
            callableStatement.registerOutParameter(5,Types.INTEGER);
            callableStatement.registerOutParameter(6,Types.BOOLEAN);
            callableStatement.registerOutParameter(7,Types.INTEGER);
            callableStatement.registerOutParameter(8,Types.VARCHAR);

            callableStatement.executeUpdate();
            callableStatement.getResultSet();

            response.setOrderCount((Integer) callableStatement.getObject(2));
            response.setOrderTotal((Double) callableStatement.getObject(3));
            response.setProductCount((Integer) callableStatement.getObject(4));
            response.setCustomerCount((Integer) callableStatement.getObject(5));

        }catch (SQLException exception){
            logger.info("An error occurred in createNewUser "+ exception);
        }finally {
            DataSourceUtils.releaseConnection(connection,jdbcTemplate.getDataSource());
            logger.info("Time taken for getOrderSummary in seconds: "+ (double)(System.currentTimeMillis() - startTime)/1000 );
        }
        return response;
    }

    @Override
    public GeneralResponse createOrder(OrderReq orderReq) {
        long startTime = System.currentTimeMillis();
        GeneralResponse response = new GeneralResponse();
        Connection connection = null;
        CallableStatement callableStatement;
        try {
            logger.info("createOrder-request------------------>"+orderReq.toString());

            connection = DataSourceUtils.getConnection(Objects.requireNonNull(jdbcTemplate.getDataSource()));
            callableStatement = connection.prepareCall(OfferDAOConstant.UserConstant.INSERT_ORDER_DETAILS);
            callableStatement.setObject(1,orderReq.getPaymentOption(), Types.INTEGER);
            callableStatement.setObject(2,orderReq.getUserId(),Types.INTEGER);
            callableStatement.setObject(3,orderReq.getInstallmentStatus(),Types.INTEGER);
            callableStatement.setObject(4,orderReq.getOrderDetails(),Types.VARCHAR);

            callableStatement.registerOutParameter(5,Types.BOOLEAN);
            callableStatement.registerOutParameter(6,Types.INTEGER);
            callableStatement.registerOutParameter(7,Types.VARCHAR);

            callableStatement.executeUpdate();
            callableStatement.getResultSet();
            response.setRes((Boolean) callableStatement.getObject(5));
            response.setStatusCode((Integer) callableStatement.getObject(6));
            response.setMsg((String) callableStatement.getObject(7));
        }catch (SQLException exception){
            logger.info("An error occurred in createOrder "+ exception);
        }finally {
            DataSourceUtils.releaseConnection(connection,jdbcTemplate.getDataSource());
            logger.info("Time taken for createOrder in seconds: "+ (double)(System.currentTimeMillis() - startTime)/1000 );
        }
        return response;
    }

    @Override
    public List<GetAllOrderRes> getAllOrders(GetAllOrderReq getAllOrderReq) {
        long startTime = System.currentTimeMillis();
        List<GetAllOrderRes> list = null;
        Connection connection = null;
        CallableStatement callableStatement;
        ResultSet resultSet;
        try {
            connection = DataSourceUtils.getConnection(Objects.requireNonNull(jdbcTemplate.getDataSource()));
            callableStatement = connection.prepareCall(OfferDAOConstant.UserConstant.GET_ALL_ORDERS);
            callableStatement.setObject(1,getAllOrderReq.getUserId(), Types.INTEGER);
            callableStatement.execute();
            resultSet = callableStatement.getResultSet();
            if(resultSet != null){
                list = new ArrayList<>();
                while (resultSet.next()){
                    GetAllOrderRes response = new GetAllOrderRes();
                    response.setOrderId(resultSet.getInt(1));
                    response.setGrandTotal(resultSet.getDouble(2));
                    response.setUserId(resultSet.getInt(3));
                    response.setCreatedDate(resultSet.getString(4));
                    response.setInstallmentStatus(resultSet.getInt(5));
                    list.add(response);
                }
            }
        }catch (SQLException exception){
            logger.info("An error occurred in getAllOrders "+ exception);
        }finally {
            DataSourceUtils.releaseConnection(connection,jdbcTemplate.getDataSource());
            logger.info("Time taken for getAllOrders in seconds: "+ (double)(System.currentTimeMillis() - startTime)/1000 );
        }
        return list;
    }

    @Override
    public List<OrderDetailRes> getAllOrderDetails(OrderDetailsReq orderDetailsReq) {
        long startTime = System.currentTimeMillis();
        List<OrderDetailRes> list = null;
        Connection connection = null;
        CallableStatement callableStatement;
        ResultSet resultSet;
        try {
            connection = DataSourceUtils.getConnection(Objects.requireNonNull(jdbcTemplate.getDataSource()));
            callableStatement = connection.prepareCall(OfferDAOConstant.UserConstant.GET_ALL_ORDER_DETAILS);
            callableStatement.setObject(1,orderDetailsReq.getOrderId(), Types.INTEGER);
            callableStatement.execute();
            resultSet = callableStatement.getResultSet();
            if(resultSet != null){
                list = new ArrayList<>();
                while (resultSet.next()){
                    OrderDetailRes response = new OrderDetailRes();
                    response.setOrderDetailId(resultSet.getInt(1));
                    response.setOrderId(resultSet.getInt(2));
                    response.setProductName(resultSet.getString(3));
                    response.setProductId(resultSet.getInt(4));
                    response.setQty(resultSet.getInt(5));
                    response.setSubTotal(resultSet.getDouble(6));
                    list.add(response);
                }
            }
        }catch (SQLException exception){
            logger.info("An error occurred in getAllOrderDetails "+ exception);
        }finally {
            DataSourceUtils.releaseConnection(connection,jdbcTemplate.getDataSource());
            logger.info("Time taken for getAllOrderDetails in seconds: "+ (double)(System.currentTimeMillis() - startTime)/1000 );
        }
        return list;
    }

    @Override
    public List<PaymentRes> getAllPaymentDetails(PaymentReq paymentReq) {
        long startTime = System.currentTimeMillis();
        List<PaymentRes> list = null;
        Connection connection = null;
        CallableStatement callableStatement;
        ResultSet resultSet;
        try {
            connection = DataSourceUtils.getConnection(Objects.requireNonNull(jdbcTemplate.getDataSource()));
            callableStatement = connection.prepareCall(OfferDAOConstant.UserConstant.GET_ALL_PAYMENT_DETAILS);
            callableStatement.setObject(1,paymentReq.getOrderId(), Types.INTEGER);
            callableStatement.execute();
            resultSet = callableStatement.getResultSet();
            if(resultSet != null){
                list = new ArrayList<>();
                while (resultSet.next()){
                    PaymentRes response = new PaymentRes();
                    response.setHistoryId(resultSet.getInt(1));
                    response.setCustomerId(resultSet.getInt(2));
                    response.setInstallmentPlanId(resultSet.getInt(3));
                    response.setInstallmentPlan(resultSet.getString(4));
                    response.setAmountPaid(resultSet.getDouble(5));
                    response.setOrderId(resultSet.getInt(6));
                    response.setCreatedDate(resultSet.getString(7));
                    list.add(response);
                }
            }
        }catch (SQLException exception){
            logger.info("An error occurred in getAllPaymentDetails "+ exception);
        }finally {
            DataSourceUtils.releaseConnection(connection,jdbcTemplate.getDataSource());
            logger.info("Time taken for getAllPaymentDetails in seconds: "+ (double)(System.currentTimeMillis() - startTime)/1000 );
        }
        return list;
    }
}
