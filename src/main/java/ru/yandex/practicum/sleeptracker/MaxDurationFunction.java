package ru.yandex.practicum.sleeptracker;

import java.util.List;

public class MaxDurationFunction implements SleepAnalysisFunction {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        long avg = sessions.stream()
                .mapToLong(SleepingSession::getDurationMinutes)
                .max().orElse(0);
        return new SleepAnalysisResult("Максимальная продолжительность сна в минутах", avg);
    }
}
