package com.pgutkowski.github.core;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.Objects;


public class SameDayHolidays {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private final LocalDate date;

    private final String name1;

    private final String name2;

    public SameDayHolidays(LocalDate date, String name1, String name2) {
        this.date = date;
        this.name1 = name1;
        this.name2 = name2;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getName1() {
        return name1;
    }

    public String getName2() {
        return name2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SameDayHolidays that = (SameDayHolidays) o;
        return Objects.equals(date, that.date) &&
                Objects.equals(name1, that.name1) &&
                Objects.equals(name2, that.name2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, name1, name2);
    }

    @Override
    public String toString() {
        return "HolidayLookupResult{" +
                "date=" + date +
                ", name1='" + name1 + '\'' +
                ", name2='" + name2 + '\'' +
                '}';
    }
}
