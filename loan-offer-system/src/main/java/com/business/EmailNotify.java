package com.business;

import com.dto.user.request.EmailReq;

public interface EmailNotify {

    /**
     * sendEmail
     * @param emailReq
     * @return
     */
    public String sendEmail(EmailReq emailReq);

}
