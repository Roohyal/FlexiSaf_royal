package com.mathias.flexisaf.service;

import com.mathias.flexisaf.payload.request.EmailDetails;
import jakarta.mail.MessagingException;

public interface EmailService {

    void sendEmailAlert(EmailDetails emailDetails, String templateName) throws MessagingException;
}
