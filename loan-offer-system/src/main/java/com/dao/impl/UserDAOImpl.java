package com.dao.impl;

import com.dao.OfferDAOConstant;
import com.dao.UserDAO;
import com.dto.product.Request.NewBrandReq;
import com.dto.product.Request.NewCategoryReq;
import com.dto.product.Request.NewProductReq;
import com.dto.product.Request.ProductDetailsReq;
import com.dto.product.Response.BrandRes;
import com.dto.product.Response.CategoryRes;
import com.dto.product.Response.ProductDetailsRes;
import com.dto.response.CommonResponse;
import com.dto.response.GeneralResponse;
import com.dto.user.request.CreateNewUserReq;
import com.dto.user.request.GetCustomerDetailReq;
import com.dto.user.request.UserLoginReq;
import com.dto.user.response.CustomerRes;
import com.dto.user.response.InstallmentPlanRes;
import com.dto.user.response.UserLoginRes;
import com.util.HashUtil;
import com.util.SecureKeyUtil;
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
public class UserDAOImpl implements UserDAO {

    Logger logger = Logger.getLogger(this.getClass().getName());

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Override
    public GeneralResponse createNewUser(CreateNewUserReq createNewUserReq) {
        long startTime = System.currentTimeMillis();
        GeneralResponse response = new GeneralResponse();
        Connection connection = null;
        CallableStatement callableStatement;
        try {
            logger.info("createNewUser-request------------------>"+createNewUserReq.toString());

            connection = DataSourceUtils.getConnection(Objects.requireNonNull(jdbcTemplate.getDataSource()));
            callableStatement = connection.prepareCall(OfferDAOConstant.UserConstant.INSERT_UPDATE_ADMIN_CUSTOMER);
            callableStatement.setObject(1,createNewUserReq.getUserId(), Types.INTEGER);
            callableStatement.setObject(2,createNewUserReq.getFirstName(),Types.VARCHAR);
            callableStatement.setObject(3,createNewUserReq.getLastName(),Types.VARCHAR);
            callableStatement.setObject(4,createNewUserReq.getDob(),Types.VARCHAR);
            callableStatement.setObject(5,createNewUserReq.getUserType(),Types.INTEGER);
            callableStatement.setObject(6,createNewUserReq.getUserName(),Types.VARCHAR);
            callableStatement.setObject(7, HashUtil.getHashCode(createNewUserReq.getSecretKey()),Types.VARCHAR);
            callableStatement.setObject(8,createNewUserReq.getUserEmail(),Types.VARCHAR);
            callableStatement.setObject(9,createNewUserReq.getUserMobileNumber(),Types.VARCHAR);
            callableStatement.setObject(10,createNewUserReq.getNic(),Types.VARCHAR);
            callableStatement.setObject(11, SecureKeyUtil.getSecureRandomKey(),Types.VARCHAR);
            callableStatement.setObject(12,createNewUserReq.getCustomerId(),Types.INTEGER);
            callableStatement.setObject(13,createNewUserReq.getLoanBalance(),Types.DOUBLE);
            callableStatement.setObject(14,createNewUserReq.getUsedAmount(),Types.DOUBLE);
            callableStatement.setObject(15,createNewUserReq.getInstallPlan(),Types.INTEGER);

            callableStatement.registerOutParameter(16,Types.BOOLEAN);
            callableStatement.registerOutParameter(17,Types.INTEGER);
            callableStatement.registerOutParameter(18,Types.VARCHAR);

            callableStatement.executeUpdate();
            callableStatement.getResultSet();
            response.setRes((Boolean) callableStatement.getObject(16));
            response.setStatusCode((Integer) callableStatement.getObject(17));
            response.setMsg((String) callableStatement.getObject(18));
        }catch (SQLException exception){
            logger.info("An error occurred in createNewUser "+ exception);
        }finally {
            DataSourceUtils.releaseConnection(connection,jdbcTemplate.getDataSource());
            logger.info("Time taken for createNewUser in seconds: "+ (double)(System.currentTimeMillis() - startTime)/1000 );
        }
        return response;
    }

    @Override
    public CustomerRes getCustomerDetail(GetCustomerDetailReq getCustomerDetailReq) {
        long startTime = System.currentTimeMillis();
        CustomerRes response = null;
        Connection connection = null;
        CallableStatement callableStatement;
        ResultSet resultSet;
        try {
            logger.info("getCustomerDetail-request------------------>"+getCustomerDetailReq.toString());

            connection = DataSourceUtils.getConnection(Objects.requireNonNull(jdbcTemplate.getDataSource()));
            callableStatement = connection.prepareCall(OfferDAOConstant.UserConstant.GET_CUSTOMER_DETAIL);
            callableStatement.setObject(1,getCustomerDetailReq.getCustomerId(),Types.INTEGER);
            callableStatement.execute();
            resultSet = callableStatement.getResultSet();
            if(resultSet != null){
                response = new CustomerRes();
                while (resultSet.next()){
                    response.setFullName(resultSet.getString(1));
                    response.setDob(resultSet.getString(2));
                    response.setUserTypeStr(resultSet.getString(3));
                    response.setUserEmail(resultSet.getString(4));
                    response.setUserMobileNumber(resultSet.getString(5));
                    response.setLoanBalance(resultSet.getDouble(6));
                    response.setUsedAmount(resultSet.getDouble(7));
                    response.setPlanName(resultSet.getString(8));
                }
            }
        }catch (SQLException exception){
            logger.info("An error occurred in getCustomerDetail "+ exception);
        }finally {
            DataSourceUtils.releaseConnection(connection,jdbcTemplate.getDataSource());
            logger.info("Time taken for getCustomerDetail in seconds: "+ (double)(System.currentTimeMillis() - startTime)/1000 );
        }
        return response;
    }

    @Override
    public List<CustomerRes> getCustomerList() {
        long startTime = System.currentTimeMillis();
        List<CustomerRes> customerList = null;
        Connection connection = null;
        CallableStatement callableStatement;
        ResultSet resultSet;
        try {
            connection = DataSourceUtils.getConnection(Objects.requireNonNull(jdbcTemplate.getDataSource()));
            callableStatement = connection.prepareCall(OfferDAOConstant.UserConstant.GET_CUSTOMER_LIST);
            callableStatement.execute();
            resultSet = callableStatement.getResultSet();
            if(resultSet != null){
                customerList = new ArrayList<>();
                while (resultSet.next()){
                    CustomerRes response = new CustomerRes();
                    response.setFullName(resultSet.getString(1));
                    response.setDob(resultSet.getString(2));
                    response.setUserTypeStr(resultSet.getString(3));
                    response.setUserEmail(resultSet.getString(4));
                    response.setUserMobileNumber(resultSet.getString(5));
                    response.setLoanBalance(resultSet.getDouble(6));
                    response.setUsedAmount(resultSet.getDouble(7));
                    response.setPlanName(resultSet.getString(8));

                    customerList.add(response);
                }
            }
        }catch (SQLException exception){
            logger.info("An error occurred in getCustomerList "+ exception);
        }finally {
            DataSourceUtils.releaseConnection(connection,jdbcTemplate.getDataSource());
            logger.info("Time taken for getCustomerList in seconds: "+ (double)(System.currentTimeMillis() - startTime)/1000 );
        }
        return customerList;
    }

    @Override
    public CommonResponse login(UserLoginReq userLoginReq) {
        long startTime = System.currentTimeMillis();
        CommonResponse response = new CommonResponse();
        Connection connection = null;
        CallableStatement callableStatement;
        try {
            logger.info("login-request------------------>"+userLoginReq.toString());

            connection = DataSourceUtils.getConnection(Objects.requireNonNull(jdbcTemplate.getDataSource()));
            callableStatement = connection.prepareCall(OfferDAOConstant.UserConstant.LOGIN_USER);
            callableStatement.setObject(1,userLoginReq.getUsername(),Types.VARCHAR);
            callableStatement.setObject(2,HashUtil.getHashCode(userLoginReq.getPassword()),Types.VARCHAR);
            callableStatement.registerOutParameter(3,Types.INTEGER);
            callableStatement.registerOutParameter(4,Types.INTEGER);
            callableStatement.registerOutParameter(5,Types.VARCHAR);
            callableStatement.registerOutParameter(6,Types.VARCHAR);
            callableStatement.registerOutParameter(7,Types.DOUBLE);
            callableStatement.registerOutParameter(8,Types.INTEGER);
            callableStatement.registerOutParameter(9,Types.VARCHAR);
            callableStatement.registerOutParameter(10,Types.BOOLEAN);
            callableStatement.registerOutParameter(11,Types.INTEGER);
            callableStatement.registerOutParameter(12,Types.VARCHAR);
            callableStatement.execute();
            callableStatement.getResultSet();

            response.setRes((Boolean) callableStatement.getObject(10));
            response.setStatusCode((Integer) callableStatement.getObject(11));
            response.setMsg((String) callableStatement.getObject(12));
            if(response.isRes()){
                UserLoginRes loginRes = new UserLoginRes();
                loginRes.setFullName( callableStatement.getString(3));
                loginRes.setUserEmail( callableStatement.getString(4));
                loginRes.setUserType( callableStatement.getInt(5));
                loginRes.setUserId( callableStatement.getInt(6));
                loginRes.setUsedAmount( callableStatement.getDouble(7));
                loginRes.setInstallmentPlan( callableStatement.getInt(8));
                loginRes.setUserKey( callableStatement.getString(9));

                response.setValue(loginRes);
            }
        }catch (SQLException exception){
            logger.info("An error occurred in login "+ exception);
        }finally {
            DataSourceUtils.releaseConnection(connection,jdbcTemplate.getDataSource());
            logger.info("Time taken for login in seconds: "+ (double)(System.currentTimeMillis() - startTime)/1000 );
        }
        return response;
    }

    @Override
    public List<InstallmentPlanRes> getInstallmentPlans() {
        long startTime = System.currentTimeMillis();
        List<InstallmentPlanRes> installmentPlanList = null;
        Connection connection = null;
        CallableStatement callableStatement;
        ResultSet resultSet;
        try {
            connection = DataSourceUtils.getConnection(Objects.requireNonNull(jdbcTemplate.getDataSource()));
            callableStatement = connection.prepareCall(OfferDAOConstant.UserConstant.GET_INSTALLMENT_PLAN_LIST);
            callableStatement.execute();
            resultSet = callableStatement.getResultSet();
            if(resultSet != null){
                installmentPlanList = new ArrayList<>();
                while (resultSet.next()){
                    InstallmentPlanRes response = new InstallmentPlanRes();
                    response.setInstallmentPlanId(resultSet.getInt(1));
                    response.setPlanName(resultSet.getString(2));

                    installmentPlanList.add(response);
                }
            }
        }catch (SQLException exception){
            logger.info("An error occurred in getInstallmentPlans "+ exception);
        }finally {
            DataSourceUtils.releaseConnection(connection,jdbcTemplate.getDataSource());
            logger.info("Time taken for getInstallmentPlans in seconds: "+ (double)(System.currentTimeMillis() - startTime)/1000 );
        }
        return installmentPlanList;
    }

}
