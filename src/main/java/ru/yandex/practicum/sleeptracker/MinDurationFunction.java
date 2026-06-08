package ru.yandex.practicum.sleeptracker;

import java.util.List;

public class MinDurationFunction implements SleepAnalysisFunction{
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        long minTime = sessions.stream()
                .mapToLong(SleepingSession::getDurationMinutes)
                .min().orElse(0);
        return new SleepAnalysisResult("Минимальная продолжительность сессии в минутах", minTime);
    }
}
