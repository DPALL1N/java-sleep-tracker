package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SleepTrackerAppTest {

    @Test
    public void testTotalSessions() {
        assertEquals("Общее количество сессий сна: 2", new TotalSessionsFunction().apply(List.of(
                new SleepingSession(LocalDateTime.now(), LocalDateTime.now(), Quality.GOOD),
                new SleepingSession(LocalDateTime.now(), LocalDateTime.now(), Quality.GOOD))).toString());
    }

    @Test
    public void testTotalSessionsEmpty() {
        assertEquals("Общее количество сессий сна: 0", new TotalSessionsFunction().apply(List.of())
                .toString());
    }

    @Test
    public void testMinDuration() {
        List<SleepingSession> s = List.of(
                new SleepingSession(LocalDateTime.now(), LocalDateTime.now().plusMinutes(100), Quality.GOOD),
                new SleepingSession(LocalDateTime.now(), LocalDateTime.now().plusMinutes(50), Quality.NORMAL));
        assertEquals("Минимальная продолжительность сессии в минутах: 50", new MinDurationFunction()
                .apply(s).toString());
    }

    @Test
    public void testMinDurationSingle() {
        assertEquals("Минимальная продолжительность сессии в минутах: 200", new MinDurationFunction()
                .apply(List.of(
                new SleepingSession(LocalDateTime.now(), LocalDateTime.now().plusMinutes(200), Quality.GOOD)))
                .toString());
    }

    @Test
    public void testMaxDuration() {
        List<SleepingSession> s = List.of(
                new SleepingSession(LocalDateTime.now(), LocalDateTime.now().plusMinutes(100), Quality.GOOD),
                new SleepingSession(LocalDateTime.now(), LocalDateTime.now().plusMinutes(50), Quality.NORMAL));
        assertEquals("Максимальная продолжительность сна в минутах: 100", new MaxDurationFunction()
                .apply(s).toString());
    }

    @Test
    public void testMaxDurationSame() {
        assertEquals("Максимальная продолжительность сна в минутах: 100", new MaxDurationFunction()
                .apply(List.of(
                new SleepingSession(LocalDateTime.now(), LocalDateTime.now().plusMinutes(100), Quality.GOOD),
                new SleepingSession(LocalDateTime.now(), LocalDateTime.now().plusMinutes(100), Quality.GOOD)))
                .toString());
    }

    @Test
    public void testAverageDuration() {
        List<SleepingSession> s = List.of(
                new SleepingSession(LocalDateTime.now(), LocalDateTime.now().plusMinutes(100), Quality.GOOD),
                new SleepingSession(LocalDateTime.now(), LocalDateTime.now().plusMinutes(200), Quality.NORMAL));
        assertEquals("Средняя продолжительность сна в минутах: 150.0", new AverageDurationFunction().apply(s)
                .toString());
    }

    @Test
    public void testAverageDurationSingle() {
        assertEquals("Средняя продолжительность сна в минутах: 150.0", new AverageDurationFunction()
                .apply(List.of(
                new SleepingSession(LocalDateTime.now(), LocalDateTime.now().plusMinutes(150), Quality.GOOD)))
                .toString());
    }

    @Test
    public void testBadQualityMultiple() {
        List<SleepingSession> s = List.of(
                new SleepingSession(LocalDateTime.now(), LocalDateTime.now(), Quality.BAD),
                new SleepingSession(LocalDateTime.now(), LocalDateTime.now(), Quality.BAD));
        assertEquals("Количество сессий с плохим качеством сна: 2", new BadQualityCountFunction().apply(s)
                .toString());
    }

    @Test
    public void testBadQualityNone() {
        assertEquals("Количество сессий с плохим качеством сна: 0", new BadQualityCountFunction()
                .apply(List.of(
                new SleepingSession(LocalDateTime.now(), LocalDateTime.now(), Quality.GOOD))).toString());
    }

    @Test
    public void testUserTypeOwl() {
        List<SleepingSession> s = List.of(new SleepingSession(LocalDateTime.of(2025, 1, 1,
                23, 30), LocalDateTime.of(2025, 1, 2, 9, 30),
                Quality.GOOD));
        assertEquals("Ваш хронотип: СОВА", new UserTypeFunction().apply(s).toString());
    }

    @Test
    public void testUserTypeDove() {
        List<SleepingSession> s = List.of(
                new SleepingSession(LocalDateTime.of(2025, 1, 1, 23, 30),
                        LocalDateTime.of(2025, 1, 2, 9, 30), Quality.GOOD),
                new SleepingSession(LocalDateTime.of(2025, 1, 2, 21, 0),
                        LocalDateTime.of(2025, 1, 3, 6, 0), Quality.GOOD));
        assertEquals("Ваш хронотип: ГОЛУБЬ", new UserTypeFunction().apply(s).toString());
    }

    @Test
    public void testSleeplessNight() {
        List<SleepingSession> s = List.of(new SleepingSession(LocalDateTime.of(2025, 10, 3,
                14, 0), LocalDateTime.of(2025, 10, 3, 15, 0),
                Quality.NORMAL));
        assertEquals("Количество бессонных ночей: 1", new SleeplessNightFunction().apply(s).toString());
    }

    @Test
    public void testSleeplessNightNone() {
        List<SleepingSession> s = List.of(new SleepingSession(LocalDateTime.of(2025, 10, 3,
                23, 0), LocalDateTime.of(2025, 10, 4, 2, 0),
                Quality.GOOD));
        assertEquals("Количество бессонных ночей: 0", new SleeplessNightFunction().apply(s).toString());
    }
}