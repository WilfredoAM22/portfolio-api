package com.wilfredo.portfolio_api.service;

public interface EmailService {
    void sendEmail(String to, String subject, String text);
}
