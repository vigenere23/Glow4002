package ca.ulaval.glo4002.booking.api.exceptionMappers;

import javax.annotation.Priority;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.core.JsonParseException;

import ca.ulaval.glo4002.booking.api.dtos.errors.ClientErrorResponseBuilder;
import ca.ulaval.glo4002.booking.api.exceptions.InvalidFormatException;

@Provider
@Priority(1)
public class JsonParseExceptionMapper implements ExceptionMapper<JsonParseException> {

    @Override
    public Response toResponse(JsonParseException exception) {
        return new ClientErrorResponseBuilder(new InvalidFormatException()).build();        
    }
}
