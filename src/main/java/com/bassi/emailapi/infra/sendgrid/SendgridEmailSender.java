package com.bassi.emailapi.infra.sendgrid;

import com.bassi.emailapi.adapters.EmailSenderGateway;
import com.bassi.emailapi.core.exceptions.EmailServiceException;
import com.bassi.emailapi.model.EmailTO;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class SendgridEmailSender implements EmailSenderGateway {

    @Value("${email.sender}")
    private String source;

    @Value("${sendgrid.apikey}")
    private String apiKey;

    private static final String ENDPOINT = "mail/send";

    @Override
    public void sendEmail(EmailTO data) {
        Email from = new Email(source);
        Email to = new Email(data.to());
        Content content = new Content("text/plain", data.body());
        Mail mail = new Mail(from, data.subject(), to, content);
        SendGrid sendGrid = new SendGrid(apiKey);
        try {
            Request request = new Request();
            request.setMethod(Method.POST);
            request.setEndpoint(ENDPOINT);
            request.setBody(mail.build());
            sendGrid.api(request);
            log.info("Sending email via Sendgrid API.");
        } catch (IOException e) {
            throw new EmailServiceException("Failed while sending email.", e);
        }
    }
}