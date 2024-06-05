package kpi.diploma.server.user.service.impl;

import kpi.diploma.server.config.security.jwt.JwtUtils;
import kpi.diploma.server.user.data.domain.Employee;
import kpi.diploma.server.user.data.dto.LoginUserRequest;
import kpi.diploma.server.user.data.dto.RegisterUserRequest;
import kpi.diploma.server.user.data.repository.EmployeeRepository;
import kpi.diploma.server.user.exception.UserAlreadyExistsException;
import kpi.diploma.server.user.exception.UserNotFoundException;
import kpi.diploma.server.user.data.mapper.EmployeeMapper;
import kpi.diploma.server.user.exception.OperationForbidenException;
import kpi.diploma.server.user.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DefaultEmployeeService implements UserDetailsService, EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @Override
    public String login(LoginUserRequest request) {
        return employeeRepository.findByUsername(request.email())
                .filter(user -> passwordEncoder.matches(request.password(), user.getPassword()))
                .map(jwtUtils::generateToken)
                .orElseThrow(() -> new BadCredentialsException("User does not exist"));
    }

    @Override
    public String createUser(RegisterUserRequest request) {
        if (employeeRepository.existsByUsername(request.getEmail())) {
            throw new UserAlreadyExistsException();
        }
        return jwtUtils.generateToken(employeeRepository.save(employeeMapper.mapEmployee(request)));
    }

    @Override
    public Employee findUser(UUID userId) {
        return employeeRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public Employee findUser(String username) {
        return employeeRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return employeeRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username " + username + " not found"));
    }

    @Override
    public Employee getCurrentUser() {
        return employeeRepository.findByUsername(getCurrentUserUsername()).orElse(null);
    }

    @Override
    public List<Employee> findManagers() {
        return employeeRepository.findManagers(getCurrentUser().getId());
    }

    @Override
    public void executeWithUserVerification(Runnable task, Employee user) {
        if (!getCurrentUserUsername().equals(user.getUsername())) {
            throw new OperationForbidenException();
        }

        task.run();
    }

    private String getCurrentUserUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
