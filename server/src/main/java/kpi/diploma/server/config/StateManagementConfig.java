package kpi.diploma.server.config;

import kpi.diploma.server.state.action.NotifyApproverAction;
import kpi.diploma.server.state.action.NotifyEmployeeAction;
import kpi.diploma.server.state.action.OnCancelAction;
import kpi.diploma.server.vacation.data.domain.Vacation;
import kpi.diploma.server.vacation.data.domain.VacationStatus;
import kpi.diploma.server.state.service.StateManagementService;
import kpi.diploma.server.state.service.impl.DefaultStateManagementService;
import kpi.diploma.server.vacation.data.repository.VacationRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StateManagementConfig {

    @Bean
    public StateManagementService<Vacation, VacationStatus> vacationStateManagementService(
            VacationRepository vacationRepository,
            NotifyApproverAction notifyApproverAction,
            NotifyEmployeeAction notifyEmployeeAction,
            OnCancelAction onCancelAction) {
        return DefaultStateManagementService.<Vacation, VacationStatus>builder()
                .initialState(VacationStatus.CREATED, notifyApproverAction)
                .managementAdapter(adapter -> adapter
                        .stateProvider(Vacation::getStatus)
                        .stateApplier(Vacation::setStatus)
                )
                .transition(VacationStatus.CREATED, VacationStatus.REQUIRES_APPROVAL, vacationRepository::save)
                .transition(VacationStatus.CREATED, VacationStatus.CANCELLED, vacationRepository::save)

                .transition(VacationStatus.REQUIRES_APPROVAL, VacationStatus.DENIED, notifyEmployeeAction)
                .transition(VacationStatus.REQUIRES_APPROVAL, VacationStatus.APPROVED, notifyEmployeeAction)
                .transition(VacationStatus.REQUIRES_APPROVAL, VacationStatus.CANCELLED, onCancelAction)

                .transition(VacationStatus.DENIED, VacationStatus.CANCELLED, onCancelAction)

                .transition(VacationStatus.APPROVED, VacationStatus.REQUIRES_ACTION, vacationRepository::save)

                .transition(VacationStatus.REQUIRES_ACTION, VacationStatus.IN_PROGRESS, vacationRepository::save)
                .transition(VacationStatus.REQUIRES_ACTION, VacationStatus.CANCELLED, onCancelAction)

                .transition(VacationStatus.IN_PROGRESS, VacationStatus.CLOSED, vacationRepository::save)
                .transition(VacationStatus.IN_PROGRESS, VacationStatus.CANCELLED, onCancelAction)
                .build();
    }

}
