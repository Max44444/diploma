package kpi.diploma.server.notification.service;

public interface MailSendingService {
    void sendEmail(String toEmail, String subject, String body);
}
