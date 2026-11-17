package com.wilfredo.portfolio_api.controller;

import com.wilfredo.portfolio_api.dto.ContactRequest;
import com.wilfredo.portfolio_api.dto.ContactResponse;
import com.wilfredo.portfolio_api.service.ContactService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/contact")
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @PostMapping
    public ContactResponse receiveContactMessage(
            @Valid @RequestBody ContactRequest request) {

        contactService.processContact(request);

        return new ContactResponse(
                "success",
                "Mensaje recibido de forma correcta"
        );
    }
}
