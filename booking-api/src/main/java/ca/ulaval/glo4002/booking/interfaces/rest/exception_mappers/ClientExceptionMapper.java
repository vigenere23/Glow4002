package ca.ulaval.glo4002.booking.interfaces.rest.exception_mappers;

import javax.annotation.Priority;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import ca.ulaval.glo4002.booking.interfaces.rest.exception_mappers.dtos.ClientErrorDto;
import ca.ulaval.glo4002.booking.interfaces.rest.exceptions.ClientException;

@Provider
@Priority(1)
public class ClientExceptionMapper implements ExceptionMapper<ClientException> {

    @Override
    public Response toResponse(ClientException clientException) {
        ClientErrorDto clientErrorDto = new ClientErrorDto(clientException.errorType, clientException.description);
        return Response.status(clientException.status).entity(clientErrorDto).build();
    }
}
