package ru.yandex.practicum.gym;

import java.util.Objects;

public class CounterOfTrainings {

    private Coach coach;
    private Integer count;

    public CounterOfTrainings(Coach coach, Integer count) {
        this.coach = coach;
        this.count = count;
    }

    public Coach getCoach() {
        return coach;
    }

    public Integer getCount() {
        return count;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CounterOfTrainings that = (CounterOfTrainings) o;
        return Objects.equals(coach, that.coach) && Objects.equals(count, that.count);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coach, count);
    }

}
