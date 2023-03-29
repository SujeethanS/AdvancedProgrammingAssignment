package com.business;

import com.dto.response.CommonResponse;
import com.dto.response.GeneralResponse;
import com.dto.user.request.CreateNewUserReq;
import com.dto.user.request.GetCustomerDetailReq;
import com.dto.user.request.UserLoginReq;
import com.dto.user.response.CustomerRes;
import com.dto.user.response.InstallmentPlanRes;

import java.util.List;

public interface UserBusiness {

    /**
     * createNewUser
     * @param createNewUserReq
     * @return
     */
    GeneralResponse createNewUser(CreateNewUserReq createNewUserReq);

    /**
     * getCustomerDetail
     * @param getCustomerDetailReq
     * @return
     */
    CustomerRes getCustomerDetail(GetCustomerDetailReq getCustomerDetailReq);

    /**
     * getCustomerList
     * @return
     */
    List<CustomerRes> getCustomerList();

    /**
     * login
     * @param userLoginReq
     * @return
     */
    CommonResponse login(UserLoginReq userLoginReq);

    /**
     * getInstallmentPlans
     * @return
     */
    List<InstallmentPlanRes> getInstallmentPlans();

}
