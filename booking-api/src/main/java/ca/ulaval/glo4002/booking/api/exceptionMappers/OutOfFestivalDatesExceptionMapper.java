package ca.ulaval.glo4002.booking.api.exceptionMappers;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import ca.ulaval.glo4002.booking.api.dtos.ErrorDto;
import ca.ulaval.glo4002.booking.domain.exceptions.OutOfFestivalDatesException;


@Provider
public class OutOfFestivalDatesExceptionMapper implements ExceptionMapper<OutOfFestivalDatesException> {

    @Override
    public Response toResponse(OutOfFestivalDatesException exception) {
        return Response.status(400).entity(new ErrorDto("INVALID_EVENT_DATE", exception.getMessage())).build();
    }
}
