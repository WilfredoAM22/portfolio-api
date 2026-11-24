package com.wilfredo.portfolio_api.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.mail.MailSendException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.List;
import java.util.Map;

@Service
public class SmtpEmailService implements EmailService {

    private final RestClient restClient;
    private final String apiKey;
    private final String senderEmail;

    public SmtpEmailService(
            @Value("${BREVO_API_KEY}") String apiKey,
            @Value("${MAIL_USERNAME}") String senderEmail) {
        this.restClient = RestClient.create("https://brevo.com");
        this.apiKey = apiKey;
        this.senderEmail = senderEmail;
    }

    @Override
    public void sendEmail(String to, String subject, String body) {
        try {
            Map<String, Object> payload = Map.of(
                    "sender", Map.of("name", "Portafolio Web", "email", senderEmail),
                    "to", List.of(Map.of("email", to)),
                    "subject", subject,
                    "htmlContent", body
            );

            restClient.post()
                    .uri("/smtp/email")
                    .header("api-key", apiKey)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(payload)
                    .retrieve()
                    .toBodilessEntity();

        } catch (RestClientException ex) {
            throw new MailSendException(
                    "No se pudo enviar el correo electrónico a través de Brevo.", ex
            );
        }
    }
}
