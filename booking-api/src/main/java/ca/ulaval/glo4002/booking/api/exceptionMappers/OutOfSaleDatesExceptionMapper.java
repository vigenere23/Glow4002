package ca.ulaval.glo4002.booking.api.exceptionMappers;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import ca.ulaval.glo4002.booking.api.dtos.ErrorDto;
import ca.ulaval.glo4002.booking.domain.exceptions.OutOfSaleDatesException;


@Provider
public class OutOfSaleDatesExceptionMapper implements ExceptionMapper<OutOfSaleDatesException> {

    @Override
    public Response toResponse(OutOfSaleDatesException exception) {
        return Response.status(400).entity(new ErrorDto("INVALID_ORDER_DATE", exception.getMessage())).build();
    }
}
