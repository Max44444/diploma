package kpi.diploma.server.vacation.facade.impl;

import kpi.diploma.server.user.data.domain.Employee;
import kpi.diploma.server.vacation.data.domain.Vacation;
import kpi.diploma.server.vacation.data.domain.VacationStatus;
import kpi.diploma.server.vacation.data.dto.*;
import kpi.diploma.server.vacation.facade.VacationFacade;
import kpi.diploma.server.user.service.EmployeeService;
import kpi.diploma.server.vacation.service.VacationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static java.util.Arrays.asList;
import static java.util.Optional.ofNullable;

@Component
@RequiredArgsConstructor
public class DefaultVacationFacade implements VacationFacade {

    private final EmployeeService employeeService;
    private final VacationService vacationService;

    @Override
    public VacationDetails createVacation(CreateVacationRequest request) {
        return vacationService.createVacationForUser(request, resolveApprover(request));
    }

    @Override
    public void approveVacationRequest(UUID requestId) {
        executeOperationWithUserVerification(requestId, vacationService::approve, Vacation::getApprover);
    }

    @Override
    public void denyVacationRequest(UUID requestId) {
        executeOperationWithUserVerification(requestId, vacationService::deny, Vacation::getApprover);
    }

    @Override
    public void cancelVacationRequest(UUID requestId) {
        executeOperationWithUserVerification(requestId, vacationService::cancel, Vacation::getEmployee);
    }

    @Override
    public void startVacation(UUID requestId) {
        executeOperationWithUserVerification(requestId, vacationService::start, Vacation::getEmployee);
    }

    @Override
    public VacationDetails findVacationRequestById(UUID requestId) {
        return vacationService.findVacationById(requestId);
    }

    @Override
    public List<VacationDetails> findVacationsRequiresApproval() {
        return vacationService.findVacationsWithApprover(employeeService.getCurrentUser());
    }

    @Override
    public List<VacationDetails> findFilteredVacations(VacationFilters filters) {
        var employee = ofNullable(filters.employee()).map(e -> "me".equals(e) ? employeeService.getCurrentUser() : employeeService.findUser(e)).orElse(null);
        var approver = ofNullable(filters.approver()).map(e -> "me".equals(e) ? employeeService.getCurrentUser() : employeeService.findUser(e)).orElse(null);
        var statuses = ofNullable(filters.statuses()).orElse(asList(VacationStatus.values()));
        return vacationService.findFilteredVacations(employee, approver, statuses);
    }

    @Override
    public List<VacationDetails> findEmployeeVacations() {
        return vacationService.findVacationsForEmployee(employeeService.getCurrentUser());
    }

    @Override
    public List<UserVacationsData> findUserVacations() {
        return vacationService.findUserVacations(employeeService.getCurrentUser());
    }

    private void executeOperationWithUserVerification(UUID vacationId, Consumer<Vacation> operation,
                                                      Function<Vacation, Employee> usernameProvider) {
        Vacation vacation = vacationService.findVacation(vacationId).orElseThrow();
        employeeService.executeWithUserVerification(() -> operation.accept(vacation), usernameProvider.apply(vacation));
    }

    private Employee2ApproverDto resolveApprover(CreateVacationRequest request) {
        Employee approver = ofNullable(request.approverEmail()).map(employeeService::findUser).orElse(null);
        return new Employee2ApproverDto(employeeService.getCurrentUser(), approver);
    }
}
