package com.pgutkowski.github.core;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Holiday implements Comparable<Holiday> {
    private final String name;

    private final LocalDate date;

    @JsonCreator
    public Holiday(@JsonProperty("name") String name, @JsonProperty("date") LocalDate date) {
        this.name = name;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Holiday holiday = (Holiday) o;
        return Objects.equals(name, holiday.name) &&
                Objects.equals(date, holiday.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, date);
    }

    @Override
    public String toString() {
        return "Holiday{" +
                "name='" + name + '\'' +
                ", date=" + date +
                '}';
    }

    @Override
    public int compareTo(Holiday o) {
        return date.compareTo(o.date);
    }
}
