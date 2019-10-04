package ca.ulaval.glo4002.booking.interfaces.rest.exceptionMappers;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import ca.ulaval.glo4002.booking.interfaces.exceptions.ClientError;
import ca.ulaval.glo4002.booking.interfaces.exceptions.ClientErrorResponse;

@Provider
public class ClientErrorMapper implements ExceptionMapper<ClientError> {

    @Override
    public Response toResponse(ClientError exception) {
        return Response.status(exception.status).entity(new ClientErrorResponse(exception.error, exception.description)).build();
    }
}
