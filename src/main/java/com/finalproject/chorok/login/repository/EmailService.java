package com.finalproject.chorok.login.repository;


import com.finalproject.chorok.login.model.EmailMessage;
import org.springframework.scheduling.annotation.Async;

public interface EmailService {

    @Async
    void sendEmail(EmailMessage emailMessage);

}