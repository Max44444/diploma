package kpi.diploma.server.vacation.data.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "vacation_template")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VacationTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "vacationTemplate")
    private Set<Vacation> vacations;
}
