package ca.ulaval.glo4002.booking.interfaces.rest.exception_mappers;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import ca.ulaval.glo4002.booking.domain.dates.exceptions.OutOfSaleDatesException;
import ca.ulaval.glo4002.booking.interfaces.rest.exception_mappers.dtos.ClientErrorDto;
import ca.ulaval.glo4002.booking.interfaces.rest.exception_mappers.dtos.ClientErrorResponseBuilder;

@Provider
public class OutOfSaleDatesExceptionMapper implements ExceptionMapper<OutOfSaleDatesException> {

    @Override
    public Response toResponse(OutOfSaleDatesException exception) {
        ClientErrorDto clientErrorDto = new ClientErrorDto("INVALID_ORDER_DATE", exception.getMessage());
        return new ClientErrorResponseBuilder(clientErrorDto).build();
    }
}
