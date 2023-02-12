package com.controller;

import com.dto.response.LoanOfferResponse;
import com.dto.user.request.CreateNewUserReq;

public interface UserController {

    /**
     * createNewUser
     * @param createNewUserReq
     * @return
     */
    public LoanOfferResponse createNewUser(CreateNewUserReq createNewUserReq);


}
