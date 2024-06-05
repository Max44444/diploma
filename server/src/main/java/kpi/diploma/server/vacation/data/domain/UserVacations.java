package kpi.diploma.server.vacation.data.domain;

import jakarta.persistence.*;
import kpi.diploma.server.user.data.domain.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "user_vacations")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVacations {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Employee user;

    @OneToOne
    @JoinColumn(name = "template_id", referencedColumnName = "id")
    private VacationTemplate template;

    @Column(name = "days")
    private int days;

    @Column(name = "remaining_days")
    private int remainingDays;

}
