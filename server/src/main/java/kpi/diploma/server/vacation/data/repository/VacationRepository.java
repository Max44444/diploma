package kpi.diploma.server.vacation.data.repository;

import kpi.diploma.server.user.data.domain.Employee;
import kpi.diploma.server.vacation.data.domain.Vacation;
import kpi.diploma.server.vacation.data.domain.VacationStatus;
import kpi.diploma.server.vacation.data.dto.AverageVacationDays;
import kpi.diploma.server.vacation.data.dto.VacationStatisticByCountry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface VacationRepository extends JpaRepository<Vacation, UUID> {

    List<Vacation> findAllByEmployee(Employee employee);

    List<Vacation> findAllByApprover(Employee approver);

    @Query("""
            select v from Vacation v 
            where (:employee is null or v.employee = :employee)
            and (:approver is null or v.approver = :approver)
            and (:statuses is null or v.status in :statuses)
            """)
    List<Vacation> findAllByEmployeeAndApproverAndStatusIn(@Param("employee") Employee employee,
                                                           @Param("approver") Employee approver,
                                                           @Param("statuses") List<VacationStatus> statuses);

    @Query(value = """

            select d.month as month, avg(d.days) as days from (
                select to_char(start_date, 'Month') as month, to_char(start_date, 'Year') as year, count(*) as days from vacation
                group by to_char(start_date, 'Month'), to_char(start_date, 'Year')
            ) as d
            group by d.month
            order by to_date(d.month, 'Month')
            """,nativeQuery = true)
    List<AverageVacationDays> findAverageVacationDays();

    List<Vacation> findAllByStartDateGreaterThan(LocalDate date);

    @Query(value = """
            select c.name as country, vt.name as type, count(*) as days from vacation as v
            join employee as e on e.id = v.employee_id
            join country as c on c.id = e.country_id
            join vacation_template as vt on v.template_id = vt.id
            where date_part('year', v.start_date) = date_part('year', CURRENT_DATE)
            group by c.name, vt.name
            """, nativeQuery = true)
    List<VacationStatisticByCountry> findVacationStatistics();
}
