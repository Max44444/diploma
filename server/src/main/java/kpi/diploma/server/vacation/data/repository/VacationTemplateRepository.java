package kpi.diploma.server.vacation.data.repository;

import kpi.diploma.server.vacation.data.domain.VacationTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface VacationTemplateRepository extends JpaRepository<VacationTemplate, UUID> {
}
