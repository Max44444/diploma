package kpi.diploma.server.vacation.data.dto;

import kpi.diploma.server.vacation.data.domain.VacationStatus;

import java.util.List;

public record VacationFilters(
        String employee,
        String approver,
        List<VacationStatus> statuses
) {
}
