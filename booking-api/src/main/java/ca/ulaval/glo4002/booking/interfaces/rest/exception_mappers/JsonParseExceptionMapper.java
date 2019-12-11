package ca.ulaval.glo4002.booking.interfaces.rest.exception_mappers;

import javax.annotation.Priority;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.core.JsonParseException;

import ca.ulaval.glo4002.booking.interfaces.rest.exception_mappers.dtos.ClientErrorDto;
import ca.ulaval.glo4002.booking.interfaces.rest.exceptions.ClientException;
import ca.ulaval.glo4002.booking.interfaces.rest.exceptions.InvalidFormatClientException;

@Provider
@Priority(1)
public class JsonParseExceptionMapper implements ExceptionMapper<JsonParseException> {

    @Override
    public Response toResponse(JsonParseException exception) {
        ClientException clientException = new InvalidFormatClientException();
        ClientErrorDto errorDto = new ClientErrorDto(clientException);
        return Response.status(clientException.status).entity(errorDto).build();
    }
}
