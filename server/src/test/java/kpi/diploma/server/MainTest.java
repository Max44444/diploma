package kpi.diploma.server;

import kpi.diploma.server.notification.config.MailSendingPropertySource;
import kpi.diploma.server.notification.service.impl.SimpleMailSendingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.mail.Session;
import java.time.LocalDate;
import java.util.Properties;

import static java.time.temporal.ChronoUnit.DAYS;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MainTest {

    @Mock
    MailSendingPropertySource mailSendingPropertySource;

    @Test
    public void test() {
//        when(mailSendingPropertySource.getSenderEmail()).thenReturn("no_replay@email.com");
//
//        Properties properties = new Properties();
//        properties.setProperty("mail.smtp.host", "127.0.0.1");
//        properties.setProperty("mail.smtp.port", "25");
//
//        SimpleMailSendingService notificationService = new SimpleMailSendingService(Session.getDefaultInstance(properties), mailSendingPropertySource);
//        notificationService.sendEmail("test@gmail.com", "Test message", "<h1>Hello</h1>");

        System.out.println(DAYS.between(LocalDate.now(), LocalDate.now().plusDays(0)));
    }

}
