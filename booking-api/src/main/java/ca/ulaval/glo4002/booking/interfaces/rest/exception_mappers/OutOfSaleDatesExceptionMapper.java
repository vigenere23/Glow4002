package ca.ulaval.glo4002.booking.interfaces.rest.exception_mappers;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import ca.ulaval.glo4002.booking.domain.dates.exceptions.OutOfSaleDatesException;
import ca.ulaval.glo4002.booking.interfaces.rest.exception_mappers.dtos.ClientErrorDto;

@Provider
public class OutOfSaleDatesExceptionMapper implements ExceptionMapper<OutOfSaleDatesException> {

    @Override
    public Response toResponse(OutOfSaleDatesException exception) {
        ClientErrorDto clientErrorDto = new ClientErrorDto("INVALID_ORDER_DATE", exception.getMessage());
        return Response.status(400).entity(clientErrorDto).build();
    }
}
