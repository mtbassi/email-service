package com.bassi.emailapi.router;

import com.bassi.emailapi.adapters.EmailSenderGateway;
import com.bassi.emailapi.model.EmailTO;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EmailSenderRouter {

    private final EmailSenderGateway amazonSimpleEmailService;

    @Autowired
    EmailSenderRouter(@Qualifier("sesEmailSender") EmailSenderGateway amazonSimpleEmailService) {
        this.amazonSimpleEmailService = amazonSimpleEmailService;
    }

    @CircuitBreaker(name = "alterProvider", fallbackMethod = "sendEmailFallback")
    public void sendEmail(EmailTO data) {
        amazonSimpleEmailService.sendEmail(data);
    }

    public void sendEmailFallback(EmailTO data, Exception e) {
        log.error("Primary provider request failed, failing over to provider secondary. Error was: " + e.getMessage());
    }
}