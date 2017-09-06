package com.pgutkowski.github;

import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.pgutkowski.github.client.HolidayApiClient;
import com.pgutkowski.github.core.HolidayService;
import com.pgutkowski.github.resources.ApiKeyResource;
import com.pgutkowski.github.resources.DateTimeParseExceptionMapper;
import com.pgutkowski.github.resources.HolidayApiExceptionMapper;
import com.pgutkowski.github.resources.HolidayResource;
import io.dropwizard.Application;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import javax.ws.rs.client.Client;

public class HolidayApplication extends Application<HolidayConfiguration> {

    public static void main(final String[] args) throws Exception {
        new HolidayApplication().run(args);
    }

    @Override
    public String getName() {
        return "Holiday";
    }

    @Override
    public void initialize(final Bootstrap<HolidayConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final HolidayConfiguration configuration,
                    final Environment environment
    ) {
        //necessary to deserialize LocalDate instances as JSON object keys
        environment.getObjectMapper().registerModule(new Jdk8Module());

        Client client = new JerseyClientBuilder(environment).build("Holiday API v1 client");
        HolidayApiClient holidayApiClient = new HolidayApiClient(
                configuration.getApiKey(),
                client.target(configuration.getHolidayUri())
        );
        HolidayService holidayService = new HolidayService(holidayApiClient);

        environment.jersey().register(new HolidayResource(holidayService));
        environment.jersey().register(new ApiKeyResource(holidayApiClient));

        environment.jersey().register(new HolidayApiExceptionMapper());
        environment.jersey().register(new DateTimeParseExceptionMapper());
    }

}
