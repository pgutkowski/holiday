package com.pgutkowski.github;

import io.dropwizard.Configuration;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.*;
import javax.validation.constraints.*;


public class HolidayConfiguration extends Configuration {

    private String apiKey;

    private String holidayUri;

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getHolidayUri() {
        return holidayUri;
    }

    public void setHolidayUri(String holidayUri) {
        this.holidayUri = holidayUri;
    }
}
