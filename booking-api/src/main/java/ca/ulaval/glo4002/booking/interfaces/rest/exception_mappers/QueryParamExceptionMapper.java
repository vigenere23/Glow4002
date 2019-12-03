package ca.ulaval.glo4002.booking.interfaces.rest.exception_mappers;

import javax.annotation.Priority;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.glassfish.jersey.server.ParamException.QueryParamException;

import ca.ulaval.glo4002.booking.interfaces.rest.exception_mappers.dtos.ClientErrorResponseBuilder;
import ca.ulaval.glo4002.booking.interfaces.rest.exceptions.InvalidFormatException;

@Provider
@Priority(1)
public class QueryParamExceptionMapper implements ExceptionMapper<QueryParamException> {

    @Override
    public Response toResponse(QueryParamException exception) {
        return new ClientErrorResponseBuilder(new InvalidFormatException()).build();
    }
}
