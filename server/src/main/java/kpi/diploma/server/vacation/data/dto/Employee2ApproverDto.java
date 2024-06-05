package kpi.diploma.server.vacation.data.dto;

import kpi.diploma.server.user.data.domain.Employee;

public record Employee2ApproverDto(
        Employee employee,
        Employee approver
) {
}
