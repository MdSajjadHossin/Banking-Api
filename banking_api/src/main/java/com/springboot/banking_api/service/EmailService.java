package com.springboot.banking_api.service;

import com.springboot.banking_api.dto.EmailDetails;

public interface EmailService {

    void sendEmailAlert(EmailDetails emailDetails);
}
