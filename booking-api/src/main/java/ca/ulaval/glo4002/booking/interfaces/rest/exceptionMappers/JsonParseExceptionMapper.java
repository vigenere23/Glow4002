package ca.ulaval.glo4002.booking.interfaces.rest.exceptionMappers;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.core.JsonParseException;

import ca.ulaval.glo4002.booking.interfaces.exceptions.ErrorDto;
import ca.ulaval.glo4002.booking.interfaces.exceptions.InvalidFormatException;

@Provider
public class JsonParseExceptionMapper implements ExceptionMapper<JsonParseException> {

    @Override
    public Response toResponse(JsonParseException exception) {
        exception.printStackTrace();
        ErrorDto response = new ErrorDto(new InvalidFormatException());
        return Response.status(400).entity(response).build();
    }
}
