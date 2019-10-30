package ca.ulaval.glo4002.booking.api.exceptionMappers;

import javax.annotation.Priority;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.databind.JsonMappingException;

import ca.ulaval.glo4002.booking.api.dtos.errors.ClientErrorResponseBuilder;
import ca.ulaval.glo4002.booking.api.exceptions.InvalidFormatException;

@Provider
@Priority(1)
public class JsonMappingExceptionMapper implements ExceptionMapper<JsonMappingException> {

    @Override
    public Response toResponse(JsonMappingException exception) {
        return new ClientErrorResponseBuilder(new InvalidFormatException()).build();
    }
}
