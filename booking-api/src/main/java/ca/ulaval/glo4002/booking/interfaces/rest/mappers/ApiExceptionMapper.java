package ca.ulaval.glo4002.booking.interfaces.rest.mappers;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import ca.ulaval.glo4002.booking.interfaces.exceptions.ApiException;
import ca.ulaval.glo4002.booking.interfaces.exceptions.ApiExceptionResponse;

@Provider
public class ApiExceptionMapper implements ExceptionMapper<ApiException> {

    @Override
    public Response toResponse(ApiException exception) {
        return Response.status(400).entity(new ApiExceptionResponse(exception.error, exception.description)).build();
    }
}
