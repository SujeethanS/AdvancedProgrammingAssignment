package com.automation;

import com.automation.LoginTest;
import com.dto.user.request.CreateNewUserReq;
import com.dto.user.request.UserLoginReq;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ApplicationTest {

    @Autowired
    private LoginTest loginTest;
    @Autowired
    private RegisterTest registerTest;

    @Test
    void loginTest(){
        UserLoginReq userLoginReq = new UserLoginReq();
        userLoginReq.setUsername("mathu");
        userLoginReq.setPassword("tevtiiys");
        //loginTest.login(userLoginReq);
    }

    @Test
    void registerTest(){
        CreateNewUserReq createNewUserReq = new CreateNewUserReq();
        createNewUserReq.setFirstName("sujee");
        createNewUserReq.setLastName("sujee");
        createNewUserReq.setUserEmail("sujee@sujee.com");
        createNewUserReq.setNic("940931524V");
        createNewUserReq.setUserMobileNumber("07772527030");
        createNewUserReq.setInstallPlan(2);
        //registerTest.register(createNewUserReq);
    }
}

