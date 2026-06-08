package ru.yandex.practicum.sleeptracker;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SleepingSession {
    LocalDateTime startTime;
    LocalDateTime endTime;
    Quality sleepingQuality;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");


    public SleepingSession(LocalDateTime startTime, LocalDateTime endTime, Quality sleepingQuality) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.sleepingQuality = sleepingQuality;
    }

    public static SleepingSession fromLogFile(String logFile) {
        String[] parts = logFile.split(";");
        LocalDateTime startTime = LocalDateTime.parse(parts[0], formatter);
        LocalDateTime endTime = LocalDateTime.parse(parts[1], formatter);
        Quality quality = Quality.valueOf(parts[2]);
        return  new SleepingSession(startTime, endTime, quality);
    }

    public long getDurationMinutes() {
        return Duration.between(startTime, endTime).toMinutes();
    }
}
