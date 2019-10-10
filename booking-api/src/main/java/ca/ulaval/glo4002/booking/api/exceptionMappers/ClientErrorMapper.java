package ca.ulaval.glo4002.booking.api.exceptionMappers;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import ca.ulaval.glo4002.booking.api.dtos.ErrorResponse;
import ca.ulaval.glo4002.booking.api.exceptions.ClientError;


@Provider
public class ClientErrorMapper implements ExceptionMapper<ClientError> {

    @Override
    public Response toResponse(ClientError exception) {
        return Response.status(exception.status).entity(new ErrorResponse(exception.errorType, exception.description)).build();
    }
}
