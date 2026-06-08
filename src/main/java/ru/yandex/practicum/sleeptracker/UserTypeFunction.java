package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserTypeFunction implements SleepAnalysisFunction {

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        Map<UserType, Long> counts = sessions.stream()
                .filter(this::isNightSleep)
                .map(this::classifyNight)
                .collect(Collectors.groupingBy(type -> type, Collectors.counting()));

        long maxCount = counts.values().stream().max(Long::compare).orElse(0L);

        long winnersCount = counts.values().stream().filter(c -> c == maxCount).count();

        if (maxCount == 0 || winnersCount > 1) {
            return new SleepAnalysisResult("Ваш хронотип", UserType.ГОЛУБЬ);
        }

        UserType result = counts.entrySet().stream()
                .filter(entry -> entry.getValue() == maxCount)
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(UserType.ГОЛУБЬ);

        return new SleepAnalysisResult("Ваш хронотип", result);
    }

    private boolean isNightSleep(SleepingSession s) {
        return s.startTime.isBefore(s.startTime.toLocalDate().atTime(6, 0)) ||
                s.endTime.isAfter(s.startTime.toLocalDate().atTime(0, 0));
    }

    private UserType classifyNight(SleepingSession s) {
        int start = s.startTime.getHour();
        int end = s.endTime.getHour();

        if (start >= 23 && end >= 9) return UserType.СОВА;
        if (start < 22 && end < 7) return UserType.ЖАВОРОНОК;
        return UserType.ГОЛУБЬ;
    }
}