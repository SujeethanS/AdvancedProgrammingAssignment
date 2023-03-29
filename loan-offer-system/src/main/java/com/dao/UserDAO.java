package com.dao;

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

import java.util.List;

public interface UserDAO {

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
