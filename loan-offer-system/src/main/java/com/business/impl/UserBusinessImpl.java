package com.business.impl;

import com.business.UserBusiness;
import com.dao.UserDAO;
import com.dto.response.GeneralResponse;
import com.dto.user.request.CreateNewUserReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class UserBusinessImpl implements UserBusiness {

    Logger logger = Logger.getLogger(this.getClass().getName());
    @Autowired
    UserDAO userDAO;
    @Override
    public GeneralResponse createNewUser(CreateNewUserReq createNewUserReq) {
        return userDAO.createNewUser(createNewUserReq);
    }

}
