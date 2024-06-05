package kpi.diploma.server.vacation.service;

import kpi.diploma.server.user.data.domain.Employee;
import kpi.diploma.server.vacation.data.domain.Vacation;
import kpi.diploma.server.vacation.data.domain.VacationStatus;
import kpi.diploma.server.vacation.data.dto.CreateVacationRequest;
import kpi.diploma.server.vacation.data.dto.Employee2ApproverDto;
import kpi.diploma.server.vacation.data.dto.UserVacationsData;
import kpi.diploma.server.vacation.data.dto.VacationDetails;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VacationService {
    VacationDetails createVacationForUser(CreateVacationRequest request, Employee2ApproverDto employee2Approver);

    void approve(Vacation vacation);

    void deny(Vacation vacation);

    void cancel(Vacation vacation);

    void start(Vacation vacation);

    Optional<Vacation> findVacation(UUID vacationId);

    VacationDetails findVacationById(UUID vacationId);

    List<VacationDetails> findVacationsForEmployee(Employee employee);

    List<VacationDetails> findVacationsWithApprover(Employee approver);

    List<VacationDetails> findFilteredVacations(Employee employee, Employee approver, List<VacationStatus> statuses);

    List<UserVacationsData> findUserVacations(Employee employee);
}
