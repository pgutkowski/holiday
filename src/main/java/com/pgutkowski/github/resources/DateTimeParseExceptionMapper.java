package com.pgutkowski.github.resources;

import com.pgutkowski.github.client.HolidayApiError;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.time.format.DateTimeParseException;

@Provider
public class DateTimeParseExceptionMapper implements ExceptionMapper<DateTimeParseException> {
    @Override
    public Response toResponse(DateTimeParseException e) {
        return Response.status(400)
                .entity(new HolidayApiError(400, e.getMessage()))
                .type(MediaType.APPLICATION_JSON_TYPE)
                .build();
    }
}
