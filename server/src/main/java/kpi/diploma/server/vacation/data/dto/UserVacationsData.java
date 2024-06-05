package kpi.diploma.server.vacation.data.dto;

import java.util.UUID;

public record UserVacationsData(
        UUID id,
        TemplateData template,
        int days,
        int remainingDays
) {
}
