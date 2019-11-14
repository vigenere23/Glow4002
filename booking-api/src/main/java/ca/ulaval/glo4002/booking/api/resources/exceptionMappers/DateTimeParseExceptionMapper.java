package ca.ulaval.glo4002.booking.api.resources.exceptionMappers;

import java.time.format.DateTimeParseException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import ca.ulaval.glo4002.booking.api.exceptions.InvalidFormatException;
import ca.ulaval.glo4002.booking.api.resources.exceptionMappers.dto.ClientErrorResponseBuilder;

@Provider
public class DateTimeParseExceptionMapper implements ExceptionMapper<DateTimeParseException> {

    @Override
    public Response toResponse(DateTimeParseException exception) {
        return new ClientErrorResponseBuilder(new InvalidFormatException()).build();
    }
}
