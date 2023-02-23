package com.controller;

import com.dto.response.LoanOfferResponse;
import com.dto.user.request.CreateNewUserReq;
import com.dto.user.request.GetCustomerDetailReq;
import com.dto.user.request.UserLoginReq;

public interface UserController {

    /**
     * createNewUser
     * @param createNewUserReq
     * @return
     */
    public LoanOfferResponse createNewUser(CreateNewUserReq createNewUserReq);

    /**
     * getCustomerDetails
     * @param getCustomerDetailReq
     * @return
     */
    public LoanOfferResponse getCustomerDetails(GetCustomerDetailReq getCustomerDetailReq);

    /**
     * getCustomerList
     * @return
     */
    public LoanOfferResponse getCustomerList();

    /**
     * login
     * @param userLoginReq
     * @return
     */
    public LoanOfferResponse login(UserLoginReq userLoginReq);

    /**
     * getInstallmentPlans
     * @return
     */
    public LoanOfferResponse getInstallmentPlans();
}
