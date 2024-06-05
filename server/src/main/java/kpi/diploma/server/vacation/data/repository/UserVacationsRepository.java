package kpi.diploma.server.vacation.data.repository;

import kpi.diploma.server.user.data.domain.Employee;
import kpi.diploma.server.vacation.data.domain.UserVacations;
import kpi.diploma.server.vacation.data.domain.VacationTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface UserVacationsRepository extends JpaRepository<UserVacations, UUID> {

    List<UserVacations> findAllByUser(Employee user);

    UserVacations findByUserAndTemplate(Employee user, VacationTemplate template);

}
