package ca.ulaval.glo4002.booking.interfaces.rest.exception_mappers;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import ca.ulaval.glo4002.booking.interfaces.rest.exceptions.InvalidFormatException;
import ca.ulaval.glo4002.booking.interfaces.rest.exception_mappers.dtos.ClientErrorResponseBuilder;

@Provider
public class NumberFormatExceptionMapper implements ExceptionMapper<NumberFormatException> {

    @Override
    public Response toResponse(NumberFormatException exception) {
        return new ClientErrorResponseBuilder(new InvalidFormatException()).build();
    }
}
