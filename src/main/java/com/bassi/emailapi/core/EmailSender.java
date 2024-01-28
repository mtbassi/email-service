package com.bassi.emailapi.core;

import com.bassi.emailapi.model.EmailTO;

public interface EmailSender {

    void sendEmail(EmailTO data);
}
