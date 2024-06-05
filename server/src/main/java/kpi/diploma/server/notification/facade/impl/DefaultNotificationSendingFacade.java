package kpi.diploma.server.notification.facade.impl;

import kpi.diploma.server.notification.data.dto.NotificationTemplate;
import kpi.diploma.server.notification.facade.NotificationSendingFacade;
import kpi.diploma.server.notification.service.MailSendingService;
import kpi.diploma.server.notification.service.TemplateResolverService;
import kpi.diploma.server.vacation.data.domain.Vacation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DefaultNotificationSendingFacade implements NotificationSendingFacade {

    private final MailSendingService mailSendingService;
    private final TemplateResolverService templateResolverService;

    @Override
    public void sendVacationRequestNotification(Vacation vacation, NotificationTemplate template) {
        var content = templateResolverService.readContent(template.getContentTemplateName());
        var receiverEmail = resolveReceiverEmail(vacation, template);
        mailSendingService.sendEmail(receiverEmail, template.getSubject(), content);
    }

    private String resolveReceiverEmail(Vacation vacation, NotificationTemplate template) {
        if (template.equals(NotificationTemplate.APPROVAL_REQUEST)) {
            return vacation.getApprover().getUsername();
        }
        return vacation.getEmployee().getUsername();
    }
}
