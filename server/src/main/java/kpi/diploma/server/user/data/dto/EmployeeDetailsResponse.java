package kpi.diploma.server.user.data.dto;

import kpi.diploma.server.user.data.dto.EmployeeData;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class EmployeeDetailsResponse extends EmployeeData {
    private UUID id;
    private EmployeeData manager;
}
