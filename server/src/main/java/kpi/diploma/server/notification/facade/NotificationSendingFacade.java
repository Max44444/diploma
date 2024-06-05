package kpi.diploma.server.notification.facade;

import kpi.diploma.server.notification.data.dto.NotificationTemplate;
import kpi.diploma.server.vacation.data.domain.Vacation;

public interface NotificationSendingFacade {

    void sendVacationRequestNotification(Vacation vacation, NotificationTemplate template);

}
