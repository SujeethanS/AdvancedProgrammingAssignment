package com.dao;

import com.dto.response.GeneralResponse;
import com.dto.user.request.CreateNewUserReq;

public interface UserDAO {
    GeneralResponse createNewUser(CreateNewUserReq createNewUserReq);

}
