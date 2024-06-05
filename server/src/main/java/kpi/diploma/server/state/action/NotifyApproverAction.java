package kpi.diploma.server.state.action;

import kpi.diploma.server.notification.data.dto.NotificationTemplate;
import kpi.diploma.server.notification.facade.NotificationSendingFacade;
import kpi.diploma.server.state.service.StateManagementService;
import kpi.diploma.server.vacation.data.domain.Vacation;
import kpi.diploma.server.vacation.data.domain.VacationStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.function.BiConsumer;

@Component
public class NotifyApproverAction implements BiConsumer<Vacation, Object> {

    @Lazy
    @Autowired
    private StateManagementService<Vacation, VacationStatus> stateManagementService;
    @Autowired
    private NotificationSendingFacade notificationSendingFacade;

    @Override
    public void accept(Vacation vacation, Object o) {
        notificationSendingFacade.sendVacationRequestNotification(vacation, NotificationTemplate.APPROVAL_REQUEST);
        stateManagementService.changeState(vacation, VacationStatus.REQUIRES_APPROVAL);
    }
}
