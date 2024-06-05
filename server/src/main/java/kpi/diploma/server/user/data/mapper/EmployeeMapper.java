package kpi.diploma.server.user.data.mapper;

import kpi.diploma.server.user.data.domain.Employee;
import kpi.diploma.server.user.data.domain.UserRole;
import kpi.diploma.server.user.data.dto.RegisterUserRequest;
import kpi.diploma.server.user.data.dto.EmployeeDetailsResponse;
import kpi.diploma.server.user.data.repository.EmployeeRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Set;

import static java.util.Optional.ofNullable;
import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public abstract class EmployeeMapper {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Mapping(target = "authorities", source = "role", qualifiedByName = "convertToSet")
    @Mapping(target = "manager", source = "managerEmail", qualifiedByName = "resolveManager")
    @Mapping(target = "password", source = "password", qualifiedByName = "encodePassword")
    @Mapping(target = "username", source = "email")
    public abstract Employee mapEmployee(RegisterUserRequest request);

    @Mapping(target = "email", source = "username")
    @Mapping(target = "manager.email", source = "manager.username")
    @Mapping(target = "manager.name", source = "manager.name")
    public abstract EmployeeDetailsResponse mapUserDetails(Employee employee);

    public abstract List<EmployeeDetailsResponse> mapUserDetails(List<Employee> employees);

    @Named("resolveManager")
    protected Employee resolveManager(String managerEmail) {
        return ofNullable(managerEmail)
                .flatMap(employeeRepository::findByUsername)
                .orElse(null);
    }

    @Named("convertToSet")
    protected Set<UserRole> convertToSet(UserRole role) {
        return Set.of(role);
    }

    @Named("encodePassword")
    protected String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }
}
