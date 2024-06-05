package kpi.diploma.server.notification.service.impl;

import kpi.diploma.server.notification.config.MailSendingPropertySource;
import kpi.diploma.server.notification.service.MailSendingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import static java.lang.String.format;


@Log4j2
@RequiredArgsConstructor
@Service
public class SimpleMailSendingService implements MailSendingService {

    private static final String SENDING_ERROR_MESSAGE_TEMPLATE = "Error sending message to '%s'";

    private final Session session;
    private final MailSendingPropertySource mailSendingPropertySource;

    @Override
    public void sendEmail(String toEmail, String subject, String body) {
        try {
            MimeMessage msg = new MimeMessage(session);

            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");

            msg.setFrom(new InternetAddress(mailSendingPropertySource.getSenderEmail(), "NoReply-JD"));
            msg.setReplyTo(InternetAddress.parse(mailSendingPropertySource.getSenderEmail(), false));
            msg.setSubject(subject, StandardCharsets.UTF_8.name());
            msg.setContent(body, "text/HTML; charset=UTF-8");
            msg.setSentDate(new Date());
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));

            Transport.send(msg);
        } catch (MessagingException | UnsupportedEncodingException e) {
            log.error(format(SENDING_ERROR_MESSAGE_TEMPLATE, toEmail), e);
        }
    }
}
