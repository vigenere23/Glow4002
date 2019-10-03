package ca.ulaval.glo4002.booking.interfaces.rest.mappers;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.databind.JsonMappingException;

import ca.ulaval.glo4002.booking.interfaces.exceptions.ApiExceptionResponse;
import ca.ulaval.glo4002.booking.interfaces.exceptions.InvalidFormatApiException;

@Provider
public class JsonMappingExceptionMapper implements ExceptionMapper<JsonMappingException> {

    @Override
    public Response toResponse(JsonMappingException exception) {
        exception.printStackTrace();
        ApiExceptionResponse response = new ApiExceptionResponse(new InvalidFormatApiException());
        return Response.status(400).entity(response).build();
    }
}
