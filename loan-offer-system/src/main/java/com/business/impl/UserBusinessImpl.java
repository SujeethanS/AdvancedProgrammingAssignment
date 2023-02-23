package com.business.impl;

import com.business.EmailNotify;
import com.business.UserBusiness;
import com.dao.UserDAO;
import com.dto.response.CommonResponse;
import com.dto.response.GeneralResponse;
import com.dto.user.request.CreateNewUserReq;
import com.dto.user.request.EmailReq;
import com.dto.user.request.GetCustomerDetailReq;
import com.dto.user.request.UserLoginReq;
import com.dto.user.response.CustomerRes;
import com.dto.user.response.InstallmentPlanRes;
import com.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class UserBusinessImpl implements UserBusiness {

    Logger logger = Logger.getLogger(this.getClass().getName());
    @Autowired
    UserDAO userDAO;

    @Autowired
    EmailNotify emailNotify;

    @Override
    public GeneralResponse createNewUser(CreateNewUserReq createNewUserReq) {
        String randomPassword = CommonUtil.generateRandomPasscode();
        logger.info("randomPassword-------------->" + randomPassword);
        createNewUserReq.setSecretKey(randomPassword);
        GeneralResponse response = userDAO.createNewUser(createNewUserReq);

        if (response.isRes()) {
            /*Send Password to User via email*/
            EmailReq emailReq = new EmailReq();
            emailReq.setReceiver(createNewUserReq.getUserEmail());
            String bodyText = "Hi " + createNewUserReq.getLastName() + "\n" +
                    "Please find your loan offer system login username is : "+ createNewUserReq.getUserName() +"  password is : " + randomPassword
                    + "\n" + "Thanks, LoanOfferAdmin";
            logger.info("body--------------->" + bodyText);
            emailReq.setMessageBody(bodyText);
            emailReq.setSubject("LoanOfferSystem[ Login Password ]");
            emailNotify.sendEmail(emailReq);
        }

        return response;
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

    @Override
    public List<InstallmentPlanRes> getInstallmentPlans() {
        logger.info("UserBusinessImpl-getInstallmentPlans-initiated");
        return userDAO.getInstallmentPlans();
    }

}
