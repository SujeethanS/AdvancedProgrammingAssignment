package com.business;

import com.dao.impl.UserDAOImpl;
import com.dto.response.GeneralResponse;
import com.dto.user.request.CreateNewUserReq;
import org.springframework.beans.factory.annotation.Autowired;

public interface UserBusiness {

    GeneralResponse createNewUser(CreateNewUserReq createNewUserReq);

}
