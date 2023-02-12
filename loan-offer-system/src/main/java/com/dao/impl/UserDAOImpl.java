package com.dao.impl;

import com.dao.OfferDAOConstant;
import com.dao.UserDAO;
import com.dto.response.GeneralResponse;
import com.dto.user.request.CreateNewUserReq;
import com.util.HashUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
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
            callableStatement.setObject(11,createNewUserReq.getCustomerId(),Types.INTEGER);
            callableStatement.setObject(12,createNewUserReq.getLoanBalance(),Types.DOUBLE);
            callableStatement.setObject(13,createNewUserReq.getUsedAmount(),Types.DOUBLE);
            callableStatement.setObject(14,createNewUserReq.getInstallPlan(),Types.INTEGER);

            callableStatement.registerOutParameter(15,Types.BOOLEAN);
            callableStatement.registerOutParameter(16,Types.INTEGER);
            callableStatement.registerOutParameter(17,Types.VARCHAR);

            callableStatement.executeUpdate();
            callableStatement.getResultSet();
            response.setRes((Boolean) callableStatement.getObject(15));
            response.setStatusCode((Integer) callableStatement.getObject(16));
            response.setMsg((String) callableStatement.getObject(17));
        }catch (SQLException exception){
            logger.info("An error occurred in createNewUser "+ exception);
        }finally {
            DataSourceUtils.releaseConnection(connection,jdbcTemplate.getDataSource());
            logger.info("Time taken for createNewUser in seconds: "+ (double)(System.currentTimeMillis() - startTime)/1000 );
        }
        return response;
    }

}
