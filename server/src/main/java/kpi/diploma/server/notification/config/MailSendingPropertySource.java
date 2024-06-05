package kpi.diploma.server.notification.config;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "notification")
@Data
public class MailSendingPropertySource {

    @NotBlank
    private String smtpPort;
    @NotBlank
    private String smtpHost;
    @NotBlank
    private String senderEmail;
}
