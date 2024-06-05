package kpi.diploma.server.vacation.data.dto;

import java.time.LocalDate;
import java.util.UUID;

public record CreateVacationRequest(
        String approverEmail,
        LocalDate startDate,
        LocalDate endDate,
        UUID templateId
) {
}
