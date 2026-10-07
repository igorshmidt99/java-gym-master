package ru.yandex.practicum.gym;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertNull;

public class TimetableTest {

    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        EnumMap<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> weeklyTimetable = new EnumMap<>(DayOfWeek.class);
        Timetable timetable = new Timetable(weeklyTimetable);

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);
        List<TrainingSession> actual = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).get(0);

        Assertions.assertEquals(actual.size(), 1);

        assertNull(timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY));
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
        EnumMap<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> weeklyTimetable = new EnumMap<>(DayOfWeek.class);
        Timetable timetable = new Timetable(weeklyTimetable);

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        Assertions.assertEquals(timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).get(0).size(), 1);

        Assertions.assertEquals(timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY).size(), 2);
        Assertions.assertEquals(timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY).get(0).get(0), thursdayChildTrainingSession);
        Assertions.assertEquals(timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY).get(1).get(0), thursdayAdultTrainingSession);

        assertNull(timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY));
    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        EnumMap<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> weeklyTimetable = new EnumMap<>(DayOfWeek.class);
        Timetable timetable = new Timetable(weeklyTimetable);
        TimeOfDay timeOfDay = new TimeOfDay(13, 0);

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, timeOfDay);

        timetable.addNewTrainingSession(singleTrainingSession);

        List<TrainingSession> actual = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, timeOfDay);
        List<TrainingSession> actualEmpty = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(14, 0));

        Assertions.assertEquals(1, actual.size());
        assertNull(actualEmpty);
    }

    @Test
    void testGetTrainingSessionsForDayAndTimeWhereDayAndTimeIsEqualToEachOther() {
        EnumMap<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> weeklyTimetable = new EnumMap<>(DayOfWeek.class);
        Timetable timetable = new Timetable(weeklyTimetable);
        TimeOfDay timeOfDay = new TimeOfDay(13, 30);

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach vasiliev = new Coach("Васильев", "Николай", "Сергеевич");
        Coach kostin = new Coach("Костин", "Николай", "Сергеевич");
        Coach strugachevskiy = new Coach("Стругачевский", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, vasiliev,
                DayOfWeek.MONDAY, timeOfDay);
        TrainingSession singleTrainingSession2 = new TrainingSession(group, kostin, DayOfWeek.MONDAY,
                timeOfDay);
        TrainingSession singleTrainingSession3 = new TrainingSession(group, strugachevskiy, DayOfWeek.MONDAY,
                timeOfDay);

        timetable.addNewTrainingSession(singleTrainingSession);
        timetable.addNewTrainingSession(singleTrainingSession2);
        timetable.addNewTrainingSession(singleTrainingSession3);

        List<TrainingSession> trainingSessionsForMonday = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY,
                timeOfDay);

        Assertions.assertEquals(3, trainingSessionsForMonday.size());
    }

    @Test
    void testSortedOrderForTrainingSessionsForMinutes() {
        EnumMap<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> weeklyTimetable = new EnumMap<>(DayOfWeek.class);
        Timetable timetable = new Timetable(weeklyTimetable);
        TimeOfDay timeOfDay15 = new TimeOfDay(13, 15);
        TimeOfDay timeOfDay30 = new TimeOfDay(13, 30);
        TimeOfDay timeOfDay45 = new TimeOfDay(13, 45);
        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach vasiliev = new Coach("Васильев", "Николай", "Сергеевич");
        Coach kostin = new Coach("Костин", "Николай", "Сергеевич");
        Coach strugachevskiy = new Coach("Стругачевский", "Николай", "Сергеевич");
        TrainingSession trainingSession15 = new TrainingSession(group, vasiliev, DayOfWeek.MONDAY, timeOfDay15);
        TrainingSession trainingSession30 = new TrainingSession(group, kostin, DayOfWeek.MONDAY, timeOfDay30);
        TrainingSession trainingSession45 = new TrainingSession(group, strugachevskiy, DayOfWeek.MONDAY, timeOfDay45);

        timetable.addNewTrainingSession(trainingSession30);
        timetable.addNewTrainingSession(trainingSession45);
        timetable.addNewTrainingSession(trainingSession15);

        List<List<TrainingSession>> trainingSessionsForMonday = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        List<TrainingSession> actualTrainingSessions15 = trainingSessionsForMonday.get(0);
        List<TrainingSession> actualTrainingSessions30 = trainingSessionsForMonday.get(1);
        List<TrainingSession> actualTrainingSessions45 = trainingSessionsForMonday.get(2);

        Assertions.assertEquals(15, actualTrainingSessions15.get(0).getTimeOfDay().getMinutes());
        Assertions.assertEquals(30, actualTrainingSessions30.get(0).getTimeOfDay().getMinutes());
        Assertions.assertEquals(45, actualTrainingSessions45.get(0).getTimeOfDay().getMinutes());
    }

    @Test
    void testSortedOrderForTrainingSessionsForHours() {
        EnumMap<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> weeklyTimetable = new EnumMap<>(DayOfWeek.class);
        Timetable timetable = new Timetable(weeklyTimetable);
        TimeOfDay timeOfDay12 = new TimeOfDay(12, 15);
        TimeOfDay timeOfDay13 = new TimeOfDay(13, 30);
        TimeOfDay timeOfDay14 = new TimeOfDay(14, 45);
        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach vasiliev = new Coach("Васильев", "Николай", "Сергеевич");
        Coach kostin = new Coach("Костин", "Николай", "Сергеевич");
        Coach strugachevskiy = new Coach("Стругачевский", "Николай", "Сергеевич");
        TrainingSession trainingSession12 = new TrainingSession(group, vasiliev, DayOfWeek.MONDAY, timeOfDay12);
        TrainingSession trainingSession13 = new TrainingSession(group, kostin, DayOfWeek.MONDAY, timeOfDay13);
        TrainingSession trainingSession14 = new TrainingSession(group, strugachevskiy, DayOfWeek.MONDAY, timeOfDay14);

        timetable.addNewTrainingSession(trainingSession14);
        timetable.addNewTrainingSession(trainingSession13);
        timetable.addNewTrainingSession(trainingSession12);

        List<List<TrainingSession>> trainingSessionsForMonday = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        List<TrainingSession> actualTrainingSessions12 = trainingSessionsForMonday.get(0);
        List<TrainingSession> actualTrainingSessions13 = trainingSessionsForMonday.get(1);
        List<TrainingSession> actualTrainingSessions14 = trainingSessionsForMonday.get(2);

        Assertions.assertEquals(12, actualTrainingSessions12.get(0).getTimeOfDay().getHours());
        Assertions.assertEquals(13, actualTrainingSessions13.get(0).getTimeOfDay().getHours());
        Assertions.assertEquals(14, actualTrainingSessions14.get(0).getTimeOfDay().getHours());
    }


    @Test
    void testGetCountByCoachesWithLotOfTrainings() {
        EnumMap<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> weeklyTimetable = new EnumMap<>(DayOfWeek.class);
        Timetable timetable = new Timetable(weeklyTimetable);

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach vasiliev = new Coach("Васильев", "Николай", "Сергеевич");
        Coach kostin = new Coach("Костин", "Николай", "Сергеевич");
        Coach strugachevskiy = new Coach("Стругачевский", "Николай", "Сергеевич");

        TimeOfDay timeOfDay1 = new TimeOfDay(9, 15);
        TimeOfDay timeOfDay2 = new TimeOfDay(10, 30);
        TimeOfDay timeOfDay3 = new TimeOfDay(11, 45);
        TimeOfDay timeOfDay4 = new TimeOfDay(12, 45);
        TimeOfDay timeOfDay5 = new TimeOfDay(13, 45);
        TimeOfDay timeOfDay6 = new TimeOfDay(14, 45);
        TimeOfDay timeOfDay7 = new TimeOfDay(15, 45);
        TimeOfDay timeOfDay8 = new TimeOfDay(16, 45);
        TimeOfDay timeOfDay9 = new TimeOfDay(17, 45);
        TimeOfDay timeOfDay10 = new TimeOfDay(18, 45);
        TimeOfDay timeOfDay11 = new TimeOfDay(19, 45);
        TimeOfDay timeOfDay12 = new TimeOfDay(20, 45);
        TimeOfDay timeOfDay13 = new TimeOfDay(21, 45);


        List<TrainingSession> trainingSessions = List.of(
                new TrainingSession(group, vasiliev, DayOfWeek.MONDAY, timeOfDay1),
                new TrainingSession(group, vasiliev, DayOfWeek.TUESDAY, timeOfDay2),
                new TrainingSession(group, kostin, DayOfWeek.MONDAY, timeOfDay3),
                new TrainingSession(group, kostin, DayOfWeek.THURSDAY, timeOfDay4),
                new TrainingSession(group, kostin, DayOfWeek.TUESDAY, timeOfDay5),
                new TrainingSession(group, strugachevskiy, DayOfWeek.MONDAY, timeOfDay6),
                new TrainingSession(group, strugachevskiy, DayOfWeek.TUESDAY, timeOfDay7),
                new TrainingSession(group, strugachevskiy, DayOfWeek.WEDNESDAY, timeOfDay8),
                new TrainingSession(group, strugachevskiy, DayOfWeek.THURSDAY, timeOfDay9),
                new TrainingSession(group, strugachevskiy, DayOfWeek.FRIDAY, timeOfDay10),
                new TrainingSession(group, strugachevskiy, DayOfWeek.SATURDAY, timeOfDay11),
                new TrainingSession(group, strugachevskiy, DayOfWeek.SUNDAY, timeOfDay12),
                new TrainingSession(group, strugachevskiy, DayOfWeek.MONDAY, timeOfDay13)
        );

        trainingSessions.forEach(timetable::addNewTrainingSession);

        PriorityQueue<CounterOfTrainings> countByCoaches = timetable.getCountByCoaches();
        CounterOfTrainings actualStrugachevskiy = countByCoaches.poll();
        CounterOfTrainings actualKostin = countByCoaches.poll();
        CounterOfTrainings actualVasiliev = countByCoaches.poll();

        Assertions.assertEquals(strugachevskiy, actualStrugachevskiy.getCoach());
        Assertions.assertEquals(kostin, actualKostin.getCoach());
        Assertions.assertEquals(vasiliev, actualVasiliev.getCoach());

        Assertions.assertEquals(8, actualStrugachevskiy.getCount());
        Assertions.assertEquals(3, actualKostin.getCount());
        Assertions.assertEquals(2, actualVasiliev.getCount());

    }


    @Test
    void testGetCountByCoachesWithNoTrainings() {
        EnumMap<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> weeklyTimetable = new EnumMap<>(DayOfWeek.class);
        Timetable timetable = new Timetable(weeklyTimetable);

        PriorityQueue<CounterOfTrainings> countByCoaches = timetable.getCountByCoaches();

        Assertions.assertEquals(0, countByCoaches.size());
    }

    @Test
    void testGetCountByCoachesWithOneCoach() {
        EnumMap<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> weeklyTimetable = new EnumMap<>(DayOfWeek.class);
        Timetable timetable = new Timetable(weeklyTimetable);

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach vasiliev = new Coach("Васильев", "Николай", "Сергеевич");

        TimeOfDay timeOfDay1 = new TimeOfDay(9, 15);

        TrainingSession trainingSession = new TrainingSession(group, vasiliev, DayOfWeek.MONDAY, timeOfDay1);
        timetable.addNewTrainingSession(trainingSession);
        PriorityQueue<CounterOfTrainings> countByCoaches = timetable.getCountByCoaches();

        Assertions.assertEquals(1, countByCoaches.size());
        Assertions.assertEquals(vasiliev, countByCoaches.poll().getCoach());
    }

}
