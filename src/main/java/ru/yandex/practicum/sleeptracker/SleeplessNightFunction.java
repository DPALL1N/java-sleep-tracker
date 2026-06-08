package ru.yandex.practicum.sleeptracker;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class SleeplessNightFunction implements SleepAnalysisFunction {

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        if (sessions.isEmpty()) return new SleepAnalysisResult("Количество бессонных ночей", 0);

        List<LocalDate> allNights = sessions.stream()
                .map(s -> s.startTime.getHour() >= 12 ? s.startTime.toLocalDate().plusDays(1)
                        : s.startTime.toLocalDate())
                .distinct()
                .sorted()
                .collect(Collectors.toList());

        long sleeplessCount = allNights.stream()
                .filter(night -> !hasNightSleep(sessions, night))
                .count();

        return new SleepAnalysisResult("Количество бессонных ночей", sleeplessCount);
    }

    private boolean hasNightSleep(List<SleepingSession> sessions, LocalDate nightDate) {
        LocalDateTime nightStart = nightDate.atTime(0, 0);
        LocalDateTime nightEnd = nightDate.atTime(6, 0);

        return sessions.stream().anyMatch(s -> {

            LocalDate sessionNight = (s.startTime.getHour() >= 12)
                    ? s.startTime.toLocalDate().plusDays(1)
                    : s.startTime.toLocalDate();

            return sessionNight.equals(nightDate) && s.endTime.isAfter(nightStart) && s.startTime.isBefore(nightEnd);
        });
    }
}