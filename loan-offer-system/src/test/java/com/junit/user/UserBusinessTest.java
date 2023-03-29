package com.junit.user;

import com.business.UserBusiness;
import com.dto.response.CommonResponse;
import com.dto.response.GeneralResponse;
import com.dto.user.request.CreateNewUserReq;
import com.dto.user.request.GetCustomerDetailReq;
import com.dto.user.request.UserLoginReq;
import com.dto.user.response.CustomerRes;
import com.dto.user.response.InstallmentPlanRes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UserBusinessTest {
    @Autowired
    UserBusiness userBusiness;
    @Test
    public void testCreateNewUser(){

        CreateNewUserReq createNewUserReq = new CreateNewUserReq();

        createNewUserReq.setFirstName("prem");
        createNewUserReq.setLastName("kathir");
        createNewUserReq.setUserEmail("prem@kathir.com");
        createNewUserReq.setInstallPlan(1);
        createNewUserReq.setDob("1994-01-01");
        createNewUserReq.setUserMobileNumber("0775454526");
        createNewUserReq.setNic("945862548V");
        createNewUserReq.setUserType(2);
        createNewUserReq.setUserName(createNewUserReq.getUserEmail());

        GeneralResponse response = userBusiness.createNewUser(createNewUserReq);
        assertEquals(true, response.isRes());
    }

    @Test
    public void testGetCustomerDetails(){

        GetCustomerDetailReq getCustomerDetailReq = new GetCustomerDetailReq();
        getCustomerDetailReq.setCustomerId(1);

        CustomerRes response = userBusiness.getCustomerDetail(getCustomerDetailReq);
        assertEquals(true, response.getUserEmail() != null);
    }

    @Test
    public void testGetCustomerList(){

        List<CustomerRes> response = userBusiness.getCustomerList();
        assertEquals(true, response.size() > 0);
    }

    @Test
    public void testLogin(){

        UserLoginReq userLoginReq = new UserLoginReq();

        userLoginReq.setPassword("tevtiiys");
        userLoginReq.setUsername("mathu");

        CommonResponse response = userBusiness.login(userLoginReq);
        assertEquals(true, response != null);
    }

    @Test
    public void testGetInstallmentPlans(){

        List<InstallmentPlanRes> response = userBusiness.getInstallmentPlans();
        assertEquals(true, response.size() > 0);
    }
}

