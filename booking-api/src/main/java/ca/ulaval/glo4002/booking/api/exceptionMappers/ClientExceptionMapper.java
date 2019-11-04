package ca.ulaval.glo4002.booking.api.exceptionMappers;

import javax.annotation.Priority;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import ca.ulaval.glo4002.booking.api.dtos.errors.ClientErrorResponseBuilder;
import ca.ulaval.glo4002.booking.api.exceptions.ClientException;


@Provider
@Priority(1)
public class ClientExceptionMapper implements ExceptionMapper<ClientException> {

    @Override
    public Response toResponse(ClientException exception) {
        return new ClientErrorResponseBuilder(exception).build();
    }
}