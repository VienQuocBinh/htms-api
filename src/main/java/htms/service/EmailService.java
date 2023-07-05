package htms.service;

import htms.api.domain.EmailDetails;

public interface EmailService {
    void sendSimpleEmail(EmailDetails details);
}
