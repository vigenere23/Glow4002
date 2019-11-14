package ca.ulaval.glo4002.booking.api.resources.exceptionMappers;

import javax.annotation.Priority;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import ca.ulaval.glo4002.booking.api.exceptions.ClientException;
import ca.ulaval.glo4002.booking.api.resources.exceptionMappers.dto.ClientErrorResponseBuilder;

@Provider
@Priority(1)
public class ClientExceptionMapper implements ExceptionMapper<ClientException> {

    @Override
    public Response toResponse(ClientException exception) {
        return new ClientErrorResponseBuilder(exception).build();
    }
}
