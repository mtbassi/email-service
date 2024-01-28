package com.bassi.emailapi.application;

import com.bassi.emailapi.adapters.EmailSenderGateway;
import com.bassi.emailapi.core.EmailSender;
import com.bassi.emailapi.model.EmailTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailSenderService implements EmailSender {

    private EmailSenderGateway emailSenderGateway;

    @Override
    public void sendEmail(EmailTO data) {
        this.emailSenderGateway.sendEmail(data);
    }
}
