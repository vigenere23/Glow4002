package ca.ulaval.glo4002.booking.api.exception_mappers;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import ca.ulaval.glo4002.booking.api.exceptions.InvalidFormatException;
import ca.ulaval.glo4002.booking.api.exception_mappers.dtos.ClientErrorResponseBuilder;

@Provider
public class IllegalArgumentExceptionMapper implements ExceptionMapper<IllegalArgumentException> {

    @Override
    public Response toResponse(IllegalArgumentException exception) {
        return new ClientErrorResponseBuilder(new InvalidFormatException()).build();
    }
}