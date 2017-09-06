package com.pgutkowski.github.core;

import com.google.common.collect.ImmutableMap;
import org.junit.Test;

import java.time.LocalDate;
import java.time.Year;
import java.util.*;

import static java.util.Collections.singletonList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class HolidayServiceTest {

    public static final CountryYear PL_2000 = new CountryYear("PL", Year.of(2000));
    public static final CountryYear PL_2001 = new CountryYear("PL", Year.of(2001));
    public static final CountryYear US_2000 = new CountryYear("US", Year.of(2000));
    public static final CountryYear US_2001 = new CountryYear("US", Year.of(2001));

    public static final LocalDate PL_2000_03 = LocalDate.of(2000, 3, 22);
    public static final LocalDate PL_2001_03 = LocalDate.of(2001, 3, 22);

    public static final LocalDate BOTH_2000_08 = LocalDate.of(2000, 8, 12);
    public static final LocalDate BOTH_2001_08 = LocalDate.of(2001, 8, 12);

    public static final LocalDate US_2000_05 = LocalDate.of(2000, 5, 22);
    public static final LocalDate US_2001_05 = LocalDate.of(2001, 5, 12);

    static class HolidayRepositoryMock implements HolidayRepository {

        Map<CountryYear, Map<LocalDate, List<Holiday>>> map = ImmutableMap.of(
                PL_2000, ImmutableMap.of (
                        PL_2000_03, singletonList(holiday(PL_2000_03)),
                        BOTH_2000_08, singletonList(holiday(BOTH_2000_08))
                ),
                PL_2001, ImmutableMap.of (
                        PL_2001_03, singletonList(holiday(PL_2001_03)),
                        BOTH_2001_08, singletonList(holiday(BOTH_2001_08))
                ),
                US_2000, ImmutableMap.of (
                        US_2000_05, singletonList(holiday(US_2000_05)),
                        BOTH_2000_08, singletonList(holiday(BOTH_2000_08))
                ),
                US_2001, ImmutableMap.of (
                        US_2001_05, singletonList(holiday(US_2001_05)),
                        BOTH_2001_08, singletonList(holiday(BOTH_2001_08))
                )
        );

        private Holiday holiday(LocalDate date){
            return new Holiday(date.toString(), date);
        }

        @Override
        public Map<LocalDate, List<Holiday>> findHolidaysByCountryYear(CountryYear countryYear) {
            return map.get(countryYear);
        }
    }

    HolidayService testedService = new HolidayService(new HolidayRepositoryMock());

    @Test
    public void testGetUpcomingHolidays(){
        SameDayHolidays holidaysForCountries = testedService.findUpcomingSameDayHolidaysForCountries(
                "PL", "US", LocalDate.of(2000, 2, 12)
        );

        assertThat(holidaysForCountries.getDate(), equalTo(BOTH_2000_08));
        assertThat(holidaysForCountries.getName1(), equalTo(BOTH_2000_08.toString()));
        assertThat(holidaysForCountries.getName2(), equalTo(BOTH_2000_08.toString()));
    }

    @Test
    public void testGetUpcomingHolidaysNextYear(){
        SameDayHolidays holidaysForCountries = testedService.findUpcomingSameDayHolidaysForCountries(
                "PL", "US", LocalDate.of(2000, 12, 12)
        );

        assertThat(holidaysForCountries.getDate(), equalTo(BOTH_2001_08));
        assertThat(holidaysForCountries.getName1(), equalTo(BOTH_2001_08.toString()));
        assertThat(holidaysForCountries.getName2(), equalTo(BOTH_2001_08.toString()));
    }
}
