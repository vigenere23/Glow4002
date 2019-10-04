package ca.ulaval.glo4002.booking.interfaces.rest.exceptionMappers;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import ca.ulaval.glo4002.booking.interfaces.rest.dtos.ErrorDto;
import ca.ulaval.glo4002.booking.interfaces.rest.exceptions.ClientError;

@Provider
public class ClientErrorMapper implements ExceptionMapper<ClientError> {

    @Override
    public Response toResponse(ClientError exception) {
        return Response.status(exception.status).entity(new ErrorDto(exception.errorType, exception.description)).build();
    }
}