package com.finalproject.chorok.Login.repository;


import com.finalproject.chorok.Login.model.EmailMessage;
import org.springframework.scheduling.annotation.Async;

public interface EmailService {

    @Async
    void sendEmail(EmailMessage emailMessage);

}