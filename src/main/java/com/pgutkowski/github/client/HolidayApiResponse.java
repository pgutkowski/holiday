package com.pgutkowski.github.client;

import com.pgutkowski.github.core.Holiday;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

class HolidayApiResponse {

    private Map<LocalDate, List<Holiday>> holidays;

    private int status;

    public Map<LocalDate, List<Holiday>> getHolidays() {
        return holidays;
    }

    public void setHolidays(Map<LocalDate, List<Holiday>> holidays) {
        this.holidays = holidays;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
