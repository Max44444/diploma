package kpi.diploma.server.state.action;

import kpi.diploma.server.vacation.data.domain.Vacation;
import kpi.diploma.server.vacation.data.repository.UserVacationsRepository;
import kpi.diploma.server.vacation.data.repository.VacationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.BiConsumer;

import static java.time.temporal.ChronoUnit.DAYS;

@Component
@RequiredArgsConstructor
public class OnCancelAction implements BiConsumer<Vacation, Object> {

    private final VacationRepository vacationRepository;
    private final UserVacationsRepository userVacationsRepository;

    @Override
    public void accept(Vacation vacation, Object o) {
        var userVacations = userVacationsRepository.findByUserAndTemplate(vacation.getEmployee(), vacation.getVacationTemplate());
        userVacations.setRemainingDays((int) (userVacations.getRemainingDays() + DAYS.between(vacation.getStartDate(), vacation.getEndDate()) - 1));
        userVacationsRepository.save(userVacations);
        vacationRepository.save(vacation);
    }
}
