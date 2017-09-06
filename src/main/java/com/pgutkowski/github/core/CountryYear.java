package com.pgutkowski.github.core;

import java.time.Year;
import java.util.Objects;

public class CountryYear {
    private final String country;

    private final Year year;

    public CountryYear(String country, Year year) {
        this.country = country;
        this.year = year;
    }

    public String getCountry() {
        return country;
    }

    public Year getYear() {
        return year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CountryYear that = (CountryYear) o;
        return Objects.equals(country, that.country) &&
                Objects.equals(year, that.year);
    }

    @Override
    public int hashCode() {
        return Objects.hash(country, year);
    }

    @Override
    public String toString() {
        return "CountryYearPair{" +
                "country='" + country + '\'' +
                ", year=" + year +
                '}';
    }
}
