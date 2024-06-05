package kpi.diploma.server.vacation.data.dto;

import kpi.diploma.server.vacation.data.domain.VacationStatus;
import kpi.diploma.server.user.data.dto.EmployeeData;

import java.time.LocalDate;
import java.util.UUID;

public record VacationDetails(

        UUID id,
        TemplateData template,
        EmployeeData approver,
        EmployeeData employee,
        LocalDate startDate,
        LocalDate endDate,
        VacationStatus status

) {
}
