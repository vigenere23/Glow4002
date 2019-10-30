package ca.ulaval.glo4002.booking.api.exceptionMappers;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import ca.ulaval.glo4002.booking.api.dtos.errors.ClientErrorDto;
import ca.ulaval.glo4002.booking.api.dtos.errors.ClientErrorResponseBuilder;
import ca.ulaval.glo4002.booking.domain.exceptions.OutOfFestivalDatesException;


@Provider
public class OutOfFestivalDatesExceptionMapper implements ExceptionMapper<OutOfFestivalDatesException> {

    @Override
    public Response toResponse(OutOfFestivalDatesException exception) {
        ClientErrorDto clientErrorDto = new ClientErrorDto("INVALID_EVENT_DATE", exception.getMessage());
        return new ClientErrorResponseBuilder(clientErrorDto).build();
    }
}
