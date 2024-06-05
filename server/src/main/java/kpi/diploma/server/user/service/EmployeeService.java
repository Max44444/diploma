package kpi.diploma.server.user.service;

import kpi.diploma.server.user.data.domain.Employee;
import kpi.diploma.server.user.data.dto.LoginUserRequest;
import kpi.diploma.server.user.data.dto.RegisterUserRequest;

import java.util.List;
import java.util.UUID;

public interface EmployeeService {
    String login(LoginUserRequest request);

    String createUser(RegisterUserRequest request);

    Employee findUser(UUID userId);

    Employee findUser(String username);

    Employee getCurrentUser();

    List<Employee> findManagers();

    void executeWithUserVerification(Runnable task, Employee user);
}
