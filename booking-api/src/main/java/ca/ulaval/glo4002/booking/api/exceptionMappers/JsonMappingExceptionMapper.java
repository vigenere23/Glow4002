package ca.ulaval.glo4002.booking.api.exceptionMappers;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.databind.JsonMappingException;

import ca.ulaval.glo4002.booking.api.dtos.ErrorResponse;
import ca.ulaval.glo4002.booking.api.exceptions.InvalidFormatException;

@Provider
public class JsonMappingExceptionMapper implements ExceptionMapper<JsonMappingException> {

    @Override
    public Response toResponse(JsonMappingException exception) {
        exception.printStackTrace();
        ErrorResponse response = new ErrorResponse(new InvalidFormatException());
        return Response.status(400).entity(response).build();
    }
}