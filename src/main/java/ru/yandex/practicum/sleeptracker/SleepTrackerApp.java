package ru.yandex.practicum.sleeptracker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SleepTrackerApp {

    private static final List<SleepAnalysisFunction> FUNCTION = List.of(
            new TotalSessionsFunction(),
            new MinDurationFunction(),
            new MaxDurationFunction(),
            new AverageDurationFunction(),
            new BadQualityCountFunction(),
            new SleeplessNightFunction(),
            new UserTypeFunction()
    );

    private static List<SleepingSession> loadSessions() {
        try (Stream<String> lines = Files.lines(Paths.get("src/main/resources/sleep_log.txt"))) {
            return lines.map(SleepingSession::fromLogFile)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла: " + e.getMessage());
            return List.of();
        }
    }

    public static void main(String[] args) {

        List<SleepingSession> sessions = loadSessions();

        FUNCTION.forEach(function -> System.out.println(function.apply(sessions)));

    }
}