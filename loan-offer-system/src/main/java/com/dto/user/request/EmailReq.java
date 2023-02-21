package com.dto.user.request;

public class EmailReq {

    private String receiver;
    private String messageBody;
    private String subject;

    public EmailReq() {
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "EmailReq{" +
                "receiver='" + receiver + '\'' +
                ", messageBody='" + messageBody + '\'' +
                ", subject='" + subject + '\'' +
                '}';
    }
}
