package com.controller.impl;

import com.business.UserBusiness;
import com.controller.UserController;
import com.dto.response.GeneralResponse;
import com.dto.response.LoanOfferResponse;
import com.dto.user.request.CreateNewUserReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
public class UserControllerImpl implements UserController {

    Logger logger = Logger.getLogger(this.getClass().getName());
    @Autowired
    UserBusiness userBusiness;

    @Override
    public LoanOfferResponse createNewUser(CreateNewUserReq createNewUserReq) {
        GeneralResponse response = userBusiness.createNewUser(createNewUserReq);
        return LoanOfferResponse.generateResponse(null,response.getStatusCode(),response.getMsg());
    }
}
