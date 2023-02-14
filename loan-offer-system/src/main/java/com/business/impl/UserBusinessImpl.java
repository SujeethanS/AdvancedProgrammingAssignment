package com.business.impl;

import com.business.UserBusiness;
import com.dao.UserDAO;
import com.dto.response.CommonResponse;
import com.dto.response.GeneralResponse;
import com.dto.user.request.CreateNewUserReq;
import com.dto.user.request.GetCustomerDetailReq;
import com.dto.user.request.UserLoginReq;
import com.dto.user.response.CustomerRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class UserBusinessImpl implements UserBusiness {

    Logger logger = Logger.getLogger(this.getClass().getName());
    @Autowired
    UserDAO userDAO;
    @Override
    public GeneralResponse createNewUser(CreateNewUserReq createNewUserReq) {
        logger.info("UserBusinessImpl-createNewUser-initiated");
        return userDAO.createNewUser(createNewUserReq);
    }

    @Override
    public CustomerRes getCustomerDetail(GetCustomerDetailReq getCustomerDetailReq) {
        logger.info("UserBusinessImpl-getCustomerDetail-initiated");
        return userDAO.getCustomerDetail(getCustomerDetailReq);
    }

    @Override
    public List<CustomerRes> getCustomerList() {
        logger.info("UserBusinessImpl-getCustomerList-initiated");
        return userDAO.getCustomerList();
    }

    @Override
    public CommonResponse login(UserLoginReq userLoginReq) {
        logger.info("UserBusinessImpl-login-initiated");
        return userDAO.login(userLoginReq);
    }

}
