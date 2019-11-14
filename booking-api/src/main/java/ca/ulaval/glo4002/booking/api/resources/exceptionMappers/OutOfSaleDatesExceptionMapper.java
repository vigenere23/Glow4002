package ca.ulaval.glo4002.booking.api.resources.exceptionMappers;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import ca.ulaval.glo4002.booking.api.resources.exceptionMappers.dto.ClientErrorDto;
import ca.ulaval.glo4002.booking.api.resources.exceptionMappers.dto.ClientErrorResponseBuilder;
import ca.ulaval.glo4002.booking.domain.exceptions.OutOfSaleDatesException;

@Provider
public class OutOfSaleDatesExceptionMapper implements ExceptionMapper<OutOfSaleDatesException> {

    @Override
    public Response toResponse(OutOfSaleDatesException exception) {
        ClientErrorDto clientErrorDto = new ClientErrorDto("INVALID_ORDER_DATE", exception.getMessage());
        return new ClientErrorResponseBuilder(clientErrorDto).build();
    }
}
