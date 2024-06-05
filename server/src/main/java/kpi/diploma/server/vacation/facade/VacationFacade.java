package kpi.diploma.server.vacation.facade;

import kpi.diploma.server.user.data.domain.Employee;
import kpi.diploma.server.vacation.data.domain.VacationStatus;
import kpi.diploma.server.vacation.data.dto.CreateVacationRequest;
import kpi.diploma.server.vacation.data.dto.UserVacationsData;
import kpi.diploma.server.vacation.data.dto.VacationDetails;
import kpi.diploma.server.vacation.data.dto.VacationFilters;

import java.util.List;
import java.util.UUID;

public interface VacationFacade {

    VacationDetails createVacation(CreateVacationRequest request);
    void approveVacationRequest(UUID requestId);
    void denyVacationRequest(UUID requestId);
    void cancelVacationRequest(UUID requestId);
    void startVacation(UUID requestId);
    VacationDetails findVacationRequestById(UUID requestId);
    List<VacationDetails> findVacationsRequiresApproval();
    List<VacationDetails> findFilteredVacations(VacationFilters filters);
    List<VacationDetails> findEmployeeVacations();
    List<UserVacationsData> findUserVacations();
}
