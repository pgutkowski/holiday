package com.pgutkowski.github.resources;

import com.pgutkowski.github.core.SameDayHolidays;
import com.pgutkowski.github.core.HolidayService;
import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;


@Path("/v1/holiday")
public class HolidayResource {

    private static Logger log = LoggerFactory.getLogger(HolidayResource.class);

    private final HolidayService holidayService;

    public HolidayResource(HolidayService holidayService) {
        this.holidayService = holidayService;
    }

    /**
     *  Accepts two country codes and date, returns next holiday which happens in both specified countries
     */
    @GET
    @Produces("application/json")
    public Response getUpcomingHolidayForTwoCountries (
            @QueryParam("country1") @NotEmpty String country1,
            @QueryParam("country2") @NotEmpty String country2,
            @QueryParam("date") @NotEmpty String date
    ){
        LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        log.info("Handling request for {} and {} after {}", country1, country2, localDate);

        Optional<SameDayHolidays> result = holidayService.findUpcomingSameDayHolidaysForCountries(
                country1, country2, localDate
        );

        if(result.isPresent()){
            return Response.ok(result).build();
        } else {
            return Response.status(400).build();
        }
    }
}
