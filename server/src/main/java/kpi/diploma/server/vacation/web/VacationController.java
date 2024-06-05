package kpi.diploma.server.vacation.web;

import kpi.diploma.server.vacation.data.dto.CreateVacationRequest;
import kpi.diploma.server.vacation.data.dto.UserVacationsData;
import kpi.diploma.server.vacation.data.dto.VacationDetails;
import kpi.diploma.server.vacation.data.dto.VacationFilters;
import kpi.diploma.server.vacation.facade.VacationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("vacation")
@RequiredArgsConstructor
public class VacationController {

    private final VacationFacade vacationFacade;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VacationDetails create(@RequestBody CreateVacationRequest request) {
        return vacationFacade.createVacation(request);
    }

    @PatchMapping("approve/{id}")
    public void approve(@PathVariable UUID id) {
        vacationFacade.approveVacationRequest(id);
    }

    @PatchMapping("deny/{id}")
    public void deny(@PathVariable UUID id) {
        vacationFacade.denyVacationRequest(id);
    }

    @PatchMapping("cancel/{id}")
    public void cancel(@PathVariable UUID id) {
        vacationFacade.cancelVacationRequest(id);
    }

    @PatchMapping("start/{id}")
    public void start(@PathVariable UUID id) {
        vacationFacade.startVacation(id);
    }

    @GetMapping("{id}")
    public VacationDetails get(@PathVariable UUID id) {
        return vacationFacade.findVacationRequestById(id);
    }

    @GetMapping("all")
    public List<VacationDetails> getAll(VacationFilters filters) {
        return vacationFacade.findFilteredVacations(filters);
    }

    @GetMapping("my")
    public List<VacationDetails> getMy() {
        return vacationFacade.findEmployeeVacations();
    }

    @GetMapping("approve")
    public List<VacationDetails> getRequiresApproval() {
        return vacationFacade.findVacationsRequiresApproval();
    }

    @GetMapping("remaining")
    public List<UserVacationsData> getVacationRemainingVacations() {
        return vacationFacade.findUserVacations();
    }
}
