package ca.ulaval.glo4002.booking.api.exceptionMappers;

import java.time.format.DateTimeParseException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import ca.ulaval.glo4002.booking.api.dtos.ErrorDto;
import ca.ulaval.glo4002.booking.api.exceptions.InvalidFormatException;


@Provider
public class DateTimeParseExceptionMapper implements ExceptionMapper<DateTimeParseException> {

    @Override
    public Response toResponse(DateTimeParseException exception) {
        ErrorDto response = new ErrorDto(new InvalidFormatException());
        return Response.status(400).entity(response).build();
    }
}
