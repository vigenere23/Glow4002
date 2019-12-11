package ca.ulaval.glo4002.booking.interfaces.rest.exception_mappers;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import ca.ulaval.glo4002.booking.domain.dates.exceptions.OutOfFestivalDatesException;
import ca.ulaval.glo4002.booking.interfaces.rest.exception_mappers.dtos.ClientErrorDto;

@Provider
public class OutOfFestivalDatesExceptionMapper implements ExceptionMapper<OutOfFestivalDatesException> {

    @Override
    public Response toResponse(OutOfFestivalDatesException exception) {
        ClientErrorDto clientErrorDto = new ClientErrorDto("INVALID_EVENT_DATE", exception.getMessage());
        return Response.status(400).entity(clientErrorDto).build();
    }
}
