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
public class NotifyEmployeeAction implements BiConsumer<Vacation, Object> {

    @Lazy
    @Autowired
    private StateManagementService<Vacation, VacationStatus> stateManagementService;
    @Autowired
    private NotificationSendingFacade notificationSendingFacade;

    @Override
    public void accept(Vacation vacation, Object o) {
        if (vacation.getStatus().equals(VacationStatus.APPROVED)) {
            notificationSendingFacade.sendVacationRequestNotification(vacation, NotificationTemplate.VACATION_APPROVED);
            stateManagementService.changeState(vacation, VacationStatus.REQUIRES_ACTION);
        } else {
            notificationSendingFacade.sendVacationRequestNotification(vacation, NotificationTemplate.VACATION_DENIED);
            stateManagementService.changeState(vacation, VacationStatus.CANCELLED);
        }
    }
}
