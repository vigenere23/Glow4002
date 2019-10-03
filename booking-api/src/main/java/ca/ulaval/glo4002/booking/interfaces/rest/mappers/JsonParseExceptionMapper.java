package ca.ulaval.glo4002.booking.interfaces.rest.mappers;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.core.JsonParseException;

import ca.ulaval.glo4002.booking.interfaces.exceptions.ClientErrorResponse;
import ca.ulaval.glo4002.booking.interfaces.exceptions.InvalidFormatException;

@Provider
public class JsonParseExceptionMapper implements ExceptionMapper<JsonParseException> {

    @Override
    public Response toResponse(JsonParseException exception) {
        exception.printStackTrace();
        ClientErrorResponse response = new ClientErrorResponse(new InvalidFormatException());
        return Response.status(400).entity(response).build();
    }
}
