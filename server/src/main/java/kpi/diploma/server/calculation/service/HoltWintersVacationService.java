package kpi.diploma.server.calculation.service;

import kpi.diploma.server.user.service.EmployeeService;
import kpi.diploma.server.vacation.data.domain.Vacation;
import kpi.diploma.server.vacation.service.VacationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class HoltWintersVacationService {

    private final VacationService vacationService;
    private final EmployeeService employeeService;

    private final double alpha = 0.3;
    private final double beta = 0.2;
    private final double gamma = 0.4;
    private final int period = 12;

    public Map<LocalDate, Double> fit(List<Vacation> vacations, int forecastPeriods) {
        Map<LocalDate, Double> data = aggregateData(vacations);
        List<LocalDate> dates = new ArrayList<>(data.keySet());
        dates.sort(LocalDate::compareTo);

        int len = dates.size();
        List<Double> level = new ArrayList<>(len);
        List<Double> trend = new ArrayList<>(len);
        List<Double> seasonal = new ArrayList<>(len + period);
        Map<LocalDate, Double> forecast = new HashMap<>();

        // Ініціалізація початкових значень
        level.add(data.get(dates.get(0)));
        trend.add(initialTrend(data, dates, period));
        for (int i = 0; i < period; i++) {
            seasonal.add(data.get(dates.get(i)) / level.get(0));
        }

        // Застосування алгоритму Holt-Winters
        for (int t = 1; t < len; t++) {
            int season = (t % period);
            double prevLevel = level.get(t - 1);
            double currentData = data.get(dates.get(t));
            double currentSeasonal = seasonal.get(t);

            double newLevel = alpha * (currentData / currentSeasonal) + (1 - alpha) * (prevLevel + trend.get(t - 1));
            level.add(newLevel);

            double newTrend = beta * (newLevel - prevLevel) + (1 - beta) * trend.get(t - 1);
            trend.add(newTrend);

            double newSeasonal = gamma * (currentData / newLevel) + (1 - gamma) * currentSeasonal;
            seasonal.add(newSeasonal);

            forecast.put(dates.get(t).plusMonths(period), (newLevel + newTrend) * newSeasonal);
        }

        // Генерація прогнозу
        for (int t = len; t < len + forecastPeriods; t++) {
            LocalDate futureDate = dates.get(len - 1).plusMonths(t - len + 1);
            double forecastValue = (level.get(len - 1) + (t - len + 1) * trend.get(len - 1)) * seasonal.get(t - period);
            forecast.put(futureDate, forecastValue);
        }

        return forecast;
    }

    private double initialTrend(Map<LocalDate, Double> data, List<LocalDate> dates, int period) {
        double sum = 0.0;
        for (int i = 0; i < period; i++) {
            sum += (data.get(dates.get(period + i)) - data.get(dates.get(i))) / period;
        }
        return sum / period;
    }

    private Map<LocalDate, Double> aggregateData(List<Vacation> vacations) {
        Map<LocalDate, Double> data = new HashMap<>();
        for (Vacation vacation : vacations) {
            LocalDate start = vacation.getStartDate();
            LocalDate end = vacation.getEndDate();
            long days = ChronoUnit.DAYS.between(start, end) + 1;

            for(var current = start; !current.isAfter(end); current = current.plusDays(1)) {
                data.put(current, data.getOrDefault(current, 0.0) + 1.0 / days);
            }
        }
        return data;
    }

}
