package com.controller.impl;

import com.business.UserBusiness;
import com.controller.UserController;
import com.dto.response.CommonResponse;
import com.dto.response.GeneralResponse;
import com.dto.response.LoanOfferResponse;
import com.dto.user.request.CreateNewUserReq;
import com.dto.user.request.GetCustomerDetailReq;
import com.dto.user.request.UserLoginReq;
import com.dto.user.response.CustomerRes;
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
public class UserControllerImpl implements UserController {

    Logger logger = Logger.getLogger(this.getClass().getName());
    @Autowired
    UserBusiness userBusiness;

    @Override
    @PostMapping("/create/new/user")
    public LoanOfferResponse createNewUser(@RequestBody CreateNewUserReq createNewUserReq) {
        logger.info("UserControllerImpl-createNewUser-initiated");
        GeneralResponse response = userBusiness.createNewUser(createNewUserReq);
        return LoanOfferResponse.generateResponse(null,response.getStatusCode(),response.getMsg());
    }

    @Override
    @PostMapping("/get/customer/detail")
    public LoanOfferResponse getCustomerDetails(@RequestBody GetCustomerDetailReq getCustomerDetailReq) {
        logger.info("UserControllerImpl-getCustomerDetails-initiated");
        CustomerRes customerRes = userBusiness.getCustomerDetail(getCustomerDetailReq);
        return LoanOfferResponse.generateResponse(
                customerRes,
                ApplicationConstant.SuccessStatusCode,
                ApplicationConstant.SuccessMsg);
    }

    @Override
    @PostMapping("/get/customer/list")
    public LoanOfferResponse getCustomerList() {
        logger.info("UserControllerImpl-getCustomerList-initiated");
        List<CustomerRes> customerResList = userBusiness.getCustomerList();
        return LoanOfferResponse.generateResponse(
                customerResList,
                ApplicationConstant.SuccessStatusCode,
                ApplicationConstant.SuccessMsg);
    }

    @Override
    @PostMapping("/user/login")
    public LoanOfferResponse login(@RequestBody UserLoginReq userLoginReq) {
        logger.info("UserControllerImpl-login-initiated");
        CommonResponse commonResponse = userBusiness.login(userLoginReq);
        return LoanOfferResponse.generateResponse(
                commonResponse.getValue(),
                commonResponse.getStatusCode(),
                commonResponse.getMsg());
    }
}
