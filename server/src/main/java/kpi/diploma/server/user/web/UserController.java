package kpi.diploma.server.user.web;

import kpi.diploma.server.user.data.dto.LoginUserRequest;
import kpi.diploma.server.user.data.dto.RegisterUserRequest;
import kpi.diploma.server.user.data.dto.EmployeeDetailsResponse;
import kpi.diploma.server.user.data.dto.JwtAuthenticationResponse;
import kpi.diploma.server.user.data.mapper.EmployeeMapper;
import kpi.diploma.server.user.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {

    private final EmployeeService employeeService;
    private final EmployeeMapper employeeMapper;

    @PostMapping("create")
    @ResponseStatus(CREATED)
    public JwtAuthenticationResponse create(@RequestBody RegisterUserRequest request) {
        return new JwtAuthenticationResponse(employeeService.createUser(request));
    }

    @PostMapping("login")
    public JwtAuthenticationResponse login(@RequestBody LoginUserRequest request) {
        return new JwtAuthenticationResponse(employeeService.login(request));
    }

    @GetMapping
    public EmployeeDetailsResponse me() {
        return employeeMapper.mapUserDetails(employeeService.getCurrentUser());
    }

    @GetMapping("{userId}")
    public EmployeeDetailsResponse getUserInfo(@PathVariable UUID userId) {
        return employeeMapper.mapUserDetails(employeeService.findUser(userId));
    }

    @GetMapping("managers")
    public List<EmployeeDetailsResponse> getManagers() {
        return employeeMapper.mapUserDetails(employeeService.findManagers());
    }
}
