package kpi.diploma.server.notification.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.mail.Session;
import java.util.Properties;

@Configuration
@EnableConfigurationProperties(MailSendingPropertySource.class)
public class MailSendingConfig {

    @Value("${mail.smtp.host}")
    private String smtpHost;

    @Value("${mail.smtp.port}")
    private String smtpPort;

    @Bean
    public Session mailSession(MailSendingPropertySource mailSendingPropertySource) {
        Properties properties = new Properties();
        properties.setProperty("mail.smtp.host", mailSendingPropertySource.getSmtpHost());
        properties.setProperty("mail.smtp.port", mailSendingPropertySource.getSmtpPort());
        return Session.getDefaultInstance(properties);
    }

}
