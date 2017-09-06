package com.pgutkowski.github.client;

import com.pgutkowski.github.core.CountryYear;
import com.pgutkowski.github.core.Holiday;
import com.pgutkowski.github.core.HolidayRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class HolidayApiClient implements HolidayRepository {

    private static Logger log = LoggerFactory.getLogger(HolidayApiClient.class);

    private volatile String apiKey;

    private final WebTarget target;

    public HolidayApiClient(String apiKey, WebTarget webTarget) {
        this.apiKey = apiKey;
        this.target = webTarget;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public Map<LocalDate, List<Holiday>> findHolidaysByCountryYear(CountryYear countryYear) {

        log.info("Fetching holidays for {}", countryYear);

        Response response = target
                .queryParam("key", apiKey)
                .queryParam("country", countryYear.getCountry())
                .queryParam("year", Integer.toString(countryYear.getYear().getValue()))
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get();

        if(response.getStatus() == 200){
            HolidayApiResponse apiResponse = response.readEntity(HolidayApiResponse.class);
            return apiResponse.getHolidays();
        } else if(response.getStatus() == 429){
            throw new HolidayApiException("Rate limit exceeded, please change API key", response.getStatus());
        } else {
            HolidayApiError holidayApiError = response.readEntity(HolidayApiError.class);
            throw new HolidayApiException(holidayApiError.getError(), holidayApiError.getStatus());
        }
    }
}
