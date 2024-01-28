package com.bassi.emailapi.adapters;

import com.bassi.emailapi.model.EmailTO;

public interface EmailSenderGateway {
    void sendEmail(EmailTO data);
}
