package ca.ulaval.glo4002.booking.interfaces.rest.exceptionMappers;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import ca.ulaval.glo4002.booking.interfaces.rest.dtos.ErrorDto;
import ca.ulaval.glo4002.booking.interfaces.rest.exceptions.InvalidFormatException;


@Provider
public class NumberFormatExceptionMapper implements ExceptionMapper<NumberFormatException> {

    @Override
    public Response toResponse(NumberFormatException exception) {
        exception.printStackTrace();
        ErrorDto response = new ErrorDto(new InvalidFormatException());
        return Response.status(400).entity(response).build();
    }
}
