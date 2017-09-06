package com.pgutkowski.github.core;

import com.google.common.base.Throwables;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.util.concurrent.UncheckedExecutionException;
import com.pgutkowski.github.client.HolidayApiException;

import java.time.LocalDate;
import java.time.Year;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class HolidayService {

    private HolidayRepository holidayRepository;

    private Cache<CountryYear, Map<LocalDate, List<Holiday>>> cache = CacheBuilder.newBuilder().maximumSize(100).build();

    public HolidayService(HolidayRepository holidayRepository) {
        this.holidayRepository = holidayRepository;
    }

    public SameDayHolidays findUpcomingSameDayHolidaysForCountries(String countryLeft,
                                                                   String countryRight,
                                                                   LocalDate date
    ){
        Objects.requireNonNull(countryLeft, "countryLeft must not be null");
        Objects.requireNonNull(countryRight, "countryRight must not be null");
        Objects.requireNonNull(date, "date must not be null");

        SameDayHolidays sameDayHolidays = null;
        Year year = Year.of(date.getYear());

        //avoid getting into endless loop with circuit breaker for 100 loops
        while(sameDayHolidays == null && year.getValue() - date.getYear() < 100){
            CountryYear countryYearLeft = new CountryYear(countryLeft, year);
            CountryYear countryYearRight = new CountryYear(countryRight, year);

            try {
                Map<LocalDate, List<Holiday>> holidaysLeft = cache.get(countryYearLeft,
                        () -> holidayRepository.findHolidaysByCountryYear(countryYearLeft));

                Map<LocalDate, List<Holiday>> holidaysRight = cache.get(countryYearRight,
                        () -> holidayRepository.findHolidaysByCountryYear(countryYearRight));

                Objects.requireNonNull(holidaysLeft, "Failed to fetch holidays for " + countryYearLeft);
                Objects.requireNonNull(holidaysRight, "Failed to fetch holidays for " + countryYearRight);

                sameDayHolidays = findSameDateUpcomingHolidays(date, holidaysLeft, holidaysRight);
            } catch (ExecutionException | UncheckedExecutionException e){
                Throwables.propagateIfPossible(e.getCause(), HolidayApiException.class);
                throw new RuntimeException(e);
            }

            //check next year if holidayLookupResult is still null
            year = year.plusYears(1);
        }

        return sameDayHolidays;
    }

    private SameDayHolidays findSameDateUpcomingHolidays(LocalDate sinceDate,
                                                         Map<LocalDate, List<Holiday>> holidaysLeft,
                                                         Map<LocalDate, List<Holiday>> holidaysRight
    ) {
        for (LocalDate date : holidaysLeft.keySet()) {
            if (date.isAfter(sinceDate) && holidaysRight.get(date) != null) {
                return new SameDayHolidays(
                        date, holidaysLeft.get(date).get(0).getName(), holidaysRight.get(date).get(0).getName()
                );
            }
        }
        return null;
    }
}
