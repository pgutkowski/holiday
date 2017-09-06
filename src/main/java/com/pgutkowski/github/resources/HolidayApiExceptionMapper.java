package com.pgutkowski.github.resources;

import com.pgutkowski.github.client.HolidayApiError;
import com.pgutkowski.github.client.HolidayApiException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class HolidayApiExceptionMapper implements ExceptionMapper<HolidayApiException> {
    @Override
    public Response toResponse(HolidayApiException e) {
        return Response
                .status(e.getStatus())
                .entity(new HolidayApiError(e.getStatus(), e.getMessage()))
                .type(MediaType.APPLICATION_JSON_TYPE)
                .build();
    }
}
