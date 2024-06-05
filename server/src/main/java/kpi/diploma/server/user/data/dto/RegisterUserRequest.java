package kpi.diploma.server.user.data.dto;

import kpi.diploma.server.user.data.domain.UserRole;
import kpi.diploma.server.user.data.dto.EmployeeData;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class RegisterUserRequest extends EmployeeData {
    private String password;
    private UserRole role;
    private String managerEmail;
}
