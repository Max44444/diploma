package kpi.diploma.server.user.data.domain;

import jakarta.persistence.*;
import kpi.diploma.server.vacation.data.domain.Vacation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "employee")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private Set<UserRole> authorities;

    private String name;

    @ManyToOne(optional = true)
    @JoinColumn(name = "manager_id", referencedColumnName = "id")
    private Employee manager;

    @ManyToMany(mappedBy = "employees")
    private Set<UserGroup> groups;

    @OneToMany(mappedBy = "employee")
    private Set<Vacation> vacations;

    @ManyToOne
    @JoinColumn(name = "country_id", referencedColumnName = "id")
    private Country country;

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
