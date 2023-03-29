package com.junit;

import com.business.UserBusiness;
import com.dto.response.GeneralResponse;
import com.dto.user.request.CreateNewUserReq;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UserBusinessTest {
    @Autowired
    UserBusiness userBusiness;


    @Test
    public void testCreateNewUser(){
        CreateNewUserReq createNewUserReq = new CreateNewUserReq();
        createNewUserReq.setFirstName("");
        createNewUserReq.setLastName("");
        createNewUserReq.setUserEmail("");

        GeneralResponse response = userBusiness.createNewUser(createNewUserReq);
        assertEquals(false, response.isRes());
    }

    @Test
    public void testCreateNewUser1(){
        CreateNewUserReq createNewUserReq = new CreateNewUserReq();
        createNewUserReq.setFirstName("");
        createNewUserReq.setLastName("");
        createNewUserReq.setUserEmail("");

        GeneralResponse response = userBusiness.createNewUser(createNewUserReq);
        assertEquals(false, response.isRes());
    }
}

