package kpi.diploma.server.calculation.web;

import kpi.diploma.server.calculation.service.HoltWintersVacationService;
import kpi.diploma.server.vacation.data.dto.VacationStatisticByCountry;
import kpi.diploma.server.vacation.data.repository.VacationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("calculation")
public class CalculationController {

    private final HoltWintersVacationService holtWintersVacationService;
    private final VacationRepository vacationRepository;

    @GetMapping("forecast")
    public Map<String, Double> calculate() {
        var vacations = vacationRepository.findAllByStartDateGreaterThan(LocalDate.of(2024, 1, 1));
        Map<LocalDate, Double> fit = holtWintersVacationService.fit(vacations, 12);
        return fit.entrySet()
                .stream()
                .collect(groupingBy(k -> k.getKey().getMonth().toString(), averagingDouble(Map.Entry::getValue)));
    }

    @GetMapping("current")
    public Map<String, Double> current() {
        var vacations = vacationRepository.findAllByStartDateGreaterThan(LocalDate.of(2023, 1, 1));
        Map<LocalDate, Double> fit = holtWintersVacationService.fit(vacations, 12);
        return fit.entrySet()
                .stream()
                .filter(e -> e.getKey().getMonth().compareTo(LocalDate.now().getMonth()) <= 0)
                .collect(groupingBy(k -> k.getKey().getMonth().toString(), averagingDouble(Map.Entry::getValue)));
    }

    @GetMapping("statistics")
    public Map<String, List<VacationStatisticByCountry>> statistics() {
        var vacationStatistics = vacationRepository.findVacationStatistics();
        return vacationStatistics.stream().collect(groupingBy(VacationStatisticByCountry::getType));
    }
}
