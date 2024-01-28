package com.bassi.emailapi.infra.ses;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.*;
import com.bassi.emailapi.adapters.EmailSenderGateway;
import com.bassi.emailapi.core.exceptions.EmailServiceException;
import com.bassi.emailapi.model.EmailTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Service
@RequiredArgsConstructor
@Slf4j
public class SesEmailSender implements EmailSenderGateway {

    @Value("${email.sender}")
    private String source;

    private final AmazonSimpleEmailService amazonSimpleEmailService;

    @Override
    public void sendEmail(EmailTO data) {
        SendEmailRequest request = new SendEmailRequest()
                .withSource(source)
                .withDestination(new Destination()
                        .withToAddresses(data.to()))
                .withMessage(new Message()
                        .withSubject(new Content(data.subject()))
                        .withBody(new Body().withText(new Content(data.body()))));
        try {
            this.amazonSimpleEmailService.sendEmail(request);
        } catch (HttpClientErrorException e) {
            log.error("HttpClientErrorException exception thrown AmazonSimpleEmailService.");
            throw e;
        } catch (AmazonServiceException e) {
            throw new EmailServiceException("Failed while sending email.", e);
        }
    }
}
