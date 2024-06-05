package kpi.diploma.server.vacation.data.domain;

import jakarta.persistence.*;
import kpi.diploma.server.user.data.domain.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "vacation")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vacation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "template_id", referencedColumnName = "id")
    private VacationTemplate vacationTemplate;

    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "approver_id", referencedColumnName = "id")
    private Employee approver;

    private LocalDate startDate;

    private LocalDate endDate;

    private VacationStatus status;
}
