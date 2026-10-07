package ru.yandex.practicum.gym;

import java.util.*;
import java.util.function.Consumer;

public class Timetable {

    private final Map<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable;

    public Timetable(Map<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable) {
        this.timetable = timetable;
    }

    public void addNewTrainingSession(TrainingSession trainingSession) {
        DayOfWeek dayOfWeek = trainingSession.getDayOfWeek();
        TimeOfDay timeOfDay = trainingSession.getTimeOfDay();
        TreeMap<TimeOfDay, List<TrainingSession>> dayTimetable = timetable.get(dayOfWeek);

        if (dayTimetable == null) {
            List<TrainingSession> trainingSessions = new ArrayList<>();
            trainingSessions.add(trainingSession);
            dayTimetable = createDailyTimetable();
            dayTimetable.put(timeOfDay, trainingSessions);
            timetable.put(dayOfWeek, dayTimetable);
        } else {
            List<TrainingSession> trainingSessions = dayTimetable.get(timeOfDay);
            if (trainingSessions == null) {
                trainingSessions = new ArrayList<>();
            }
            trainingSessions.add(trainingSession);
            dayTimetable.put(timeOfDay, trainingSessions);
        }
    }

    public List<List<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        TreeMap<TimeOfDay, List<TrainingSession>> timeOfDayTrainingSessionTreeMap = timetable.get(dayOfWeek);
        if (timeOfDayTrainingSessionTreeMap == null) {
            return null;
        }
        return timeOfDayTrainingSessionTreeMap.values().stream().toList();
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        TreeMap<TimeOfDay, List<TrainingSession>> dailyTrainingSessions = timetable.get(dayOfWeek);
        if (dailyTrainingSessions == null) {
            return null;
        }
        return dailyTrainingSessions.get(timeOfDay);
    }

    public PriorityQueue<CounterOfTrainings> getCountByCoaches() {
        Map<Coach, Integer> coachesCounts = new HashMap<>();
        PriorityQueue<CounterOfTrainings> counterOfTrainings = new PriorityQueue<>(
                Comparator.comparingInt(CounterOfTrainings::getCount).reversed()
        );
        timetable.values().stream()
                .map(TreeMap::values)
                .flatMap(Collection::stream)
                .flatMap(Collection::stream)
                .forEach(countCoaches(coachesCounts));
        coachesCounts.forEach((key, value) -> counterOfTrainings.add(
                new CounterOfTrainings(key, value)
        ));
        return counterOfTrainings;
    }

    private Consumer<? super TrainingSession> countCoaches(Map<Coach, Integer> coachesCounts) {
        return trainingSession -> {
            Integer count = coachesCounts.get(trainingSession.getCoach());
            if (count == null) {
                coachesCounts.put(trainingSession.getCoach(), 1);
            } else {
                count++;
                coachesCounts.put(trainingSession.getCoach(), count);
            }
        };
    }

    private TreeMap<TimeOfDay, List<TrainingSession>> createDailyTimetable() {
        Comparator<TimeOfDay> comparator =
                Comparator.comparingInt(TimeOfDay::getHours)
                        .thenComparingInt(TimeOfDay::getMinutes);
        return new TreeMap<>(comparator);
    }

}
