package ca.ulaval.glo4002.booking.interfaces.rest.mappers;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.databind.JsonMappingException;

import ca.ulaval.glo4002.booking.interfaces.exceptions.ClientErrorResponse;
import ca.ulaval.glo4002.booking.interfaces.exceptions.InvalidFormat;

@Provider
public class JsonMappingExceptionMapper implements ExceptionMapper<JsonMappingException> {

    @Override
    public Response toResponse(JsonMappingException exception) {
        exception.printStackTrace();
        ClientErrorResponse response = new ClientErrorResponse(new InvalidFormat());
        return Response.status(400).entity(response).build();
    }
}
