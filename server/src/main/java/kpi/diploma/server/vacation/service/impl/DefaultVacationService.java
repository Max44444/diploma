package kpi.diploma.server.vacation.service.impl;

import kpi.diploma.server.state.service.StateManagementService;
import kpi.diploma.server.user.data.domain.Employee;
import kpi.diploma.server.vacation.data.domain.UserVacations;
import kpi.diploma.server.vacation.data.domain.Vacation;
import kpi.diploma.server.vacation.data.domain.VacationStatus;
import kpi.diploma.server.vacation.data.dto.CreateVacationRequest;
import kpi.diploma.server.vacation.data.dto.Employee2ApproverDto;
import kpi.diploma.server.vacation.data.dto.UserVacationsData;
import kpi.diploma.server.vacation.data.dto.VacationDetails;
import kpi.diploma.server.vacation.data.repository.UserVacationsRepository;
import kpi.diploma.server.vacation.data.repository.VacationRepository;
import kpi.diploma.server.vacation.data.mapper.VacationMapper;
import kpi.diploma.server.vacation.service.VacationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.util.Arrays.asList;

@Service
@RequiredArgsConstructor
public class DefaultVacationService implements VacationService {

    private final VacationRepository vacationRepository;
    private final VacationMapper vacationMapper;
    private final UserVacationsRepository userVacationsRepository;
    private final StateManagementService<Vacation, VacationStatus> vacationStateManagementService;


    @Override
    public VacationDetails createVacationForUser(CreateVacationRequest request, Employee2ApproverDto employee2Approver) {
        Vacation vacation = vacationMapper.mapVacation(request, employee2Approver);
        vacationStateManagementService.initiate(vacation);
        vacation = vacationRepository.save(vacation);
        var userVacation = userVacationsRepository.findByUserAndTemplate(vacation.getEmployee(), vacation.getVacationTemplate());
        userVacation.setRemainingDays((int) (userVacation.getRemainingDays() - DAYS.between(vacation.getStartDate(), vacation.getEndDate()) + 1));
        userVacationsRepository.save(userVacation);
        return vacationMapper.mapVacationDetails(vacation);
    }

    @Override
    public void approve(Vacation vacation) {
        vacationStateManagementService.changeState(vacation, VacationStatus.APPROVED);
    }

    @Override
    public void deny(Vacation vacation) {
        vacationStateManagementService.changeState(vacation, VacationStatus.DENIED);
    }

    @Override
    public void cancel(Vacation vacation) {
        vacationStateManagementService.changeState(vacation, VacationStatus.CANCELLED);
    }

    @Override
    public void start(Vacation vacation) {
        vacationStateManagementService.changeState(vacation, VacationStatus.IN_PROGRESS);
    }

    @Override
    public Optional<Vacation> findVacation(UUID vacationId) {
        return vacationRepository.findById(vacationId);
    }

    @Override
    public VacationDetails findVacationById(UUID vacationId) {
        return findVacation(vacationId).map(vacationMapper::mapVacationDetails).orElseThrow();
    }

    @Override
    public List<VacationDetails> findVacationsForEmployee(Employee employee) {
        return vacationMapper.mapVacationDetails(vacationRepository.findAllByEmployee(employee));
    }

    @Override
    public List<VacationDetails> findVacationsWithApprover(Employee approver) {
        return vacationMapper.mapVacationDetails(vacationRepository.findAllByApprover(approver));
    }

    @Override
    public List<VacationDetails> findFilteredVacations(Employee employee, Employee approver, List<VacationStatus> statuses) {
        var requests = vacationRepository.findAllByEmployeeAndApproverAndStatusIn(employee, approver, statuses);
        return vacationMapper.mapVacationDetails(requests);
    }

    @Override
    public List<UserVacationsData> findUserVacations(Employee employee) {
        return vacationMapper.mapUserVacations(userVacationsRepository.findAllByUser(employee));
    }
}
