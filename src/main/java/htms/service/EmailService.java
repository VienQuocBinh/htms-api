package htms.service;

import htms.api.domain.EmailDetails;

public interface EmailService {
    String sendSimpleEmail(EmailDetails details);

    String sendMailWithAttachment(EmailDetails details);
}
