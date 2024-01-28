package com.bassi.emailapi.application;

import com.bassi.emailapi.core.EmailSender;
import com.bassi.emailapi.model.EmailTO;
import com.bassi.emailapi.router.EmailSenderRouter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailSenderService implements EmailSender {

    private final EmailSenderRouter emailSenderRouter;

    @Override
    public void sendEmail(EmailTO data) {
        this.emailSenderRouter.sendEmail(data);
    }
}
