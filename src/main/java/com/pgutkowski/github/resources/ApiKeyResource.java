package com.pgutkowski.github.resources;

import com.pgutkowski.github.client.HolidayApiClient;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Objects;

/**
 * Allows updating holiday api key in runtime
 */
@Path("/v1/key")
public class ApiKeyResource {
    private final HolidayApiClient apiClient;

    public ApiKeyResource(HolidayApiClient apiClient) {
        this.apiClient = apiClient;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response setKey(ApiKeyRequestBody requestBody){
        Objects.requireNonNull(requestBody.getKey(), "Cannot use null api key to retrieve data from holidays api");
        apiClient.setApiKey(requestBody.getKey());
        return Response.ok(buildApiKeyJson()).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getKey(){
        return buildApiKeyJson();
    }

    private String buildApiKeyJson(){
        return "{\"key\":\""+apiClient.getApiKey()+"\"}";
    }
}
