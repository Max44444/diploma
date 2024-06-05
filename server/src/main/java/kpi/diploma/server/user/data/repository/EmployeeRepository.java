package kpi.diploma.server.user.data.repository;

import kpi.diploma.server.user.data.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID> {

    boolean existsByUsername(String username);

    Optional<Employee> findByUsername(String username);

    @Query(value = """
        with recursive rec as (
            select * from employee
            where id = :id
            union all select c.* from employee c join rec p on c.id = p.manager_id
        ) select distinct * from rec where id != :id
    """, nativeQuery = true)
    List<Employee> findManagers(UUID id);

}
