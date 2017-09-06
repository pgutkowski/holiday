package com.pgutkowski.github.core;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface HolidayRepository {
    Map<LocalDate, List<Holiday>> findHolidaysByCountryYear(CountryYear countryYear);
}
