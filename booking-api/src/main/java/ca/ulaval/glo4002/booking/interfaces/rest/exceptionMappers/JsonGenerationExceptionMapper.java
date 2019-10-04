package ca.ulaval.glo4002.booking.interfaces.rest.exceptionMappers;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.core.JsonGenerationException;

import ca.ulaval.glo4002.booking.interfaces.rest.dtos.ErrorDto;
import ca.ulaval.glo4002.booking.interfaces.rest.exceptions.InvalidFormatException;


@Provider
public class JsonGenerationExceptionMapper implements ExceptionMapper<JsonGenerationException> {

    @Override
    public Response toResponse(JsonGenerationException exception) {
        exception.printStackTrace();
        ErrorDto response = new ErrorDto(new InvalidFormatException());
        return Response.status(400).entity(response).build();
    }
}
