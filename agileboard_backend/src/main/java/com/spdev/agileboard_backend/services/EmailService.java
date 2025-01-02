package com.spdev.agileboard_backend.services;

public interface EmailService {

    void SendEmailWIthToken(String userEmail, String link) throws Exception;
}
