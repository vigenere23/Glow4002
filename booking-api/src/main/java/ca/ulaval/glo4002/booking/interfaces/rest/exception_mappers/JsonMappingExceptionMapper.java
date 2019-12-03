package ca.ulaval.glo4002.booking.interfaces.rest.exception_mappers;

import javax.annotation.Priority;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.databind.JsonMappingException;

import ca.ulaval.glo4002.booking.interfaces.rest.exceptions.InvalidFormatException;
import ca.ulaval.glo4002.booking.interfaces.rest.exception_mappers.dtos.ClientErrorDto;
import ca.ulaval.glo4002.booking.interfaces.rest.exception_mappers.dtos.ClientErrorResponseBuilder;
import ca.ulaval.glo4002.booking.interfaces.rest.resources.program.ProgramResource;

@Provider
@Priority(2)
public class JsonMappingExceptionMapper implements ExceptionMapper<JsonMappingException> {

    @Context
    private ResourceInfo resourceInfo;
    
    @Override
    public Response toResponse(JsonMappingException exception) {
        Class<?> resourceClass = resourceInfo.getResourceClass();
        if (resourceClass.equals(ProgramResource.class)) {
            ClientErrorDto clientErrorDto = new ClientErrorDto("INVALID_PROGRAM", "the program is invalid");
            return new ClientErrorResponseBuilder(clientErrorDto).build();
        }

        return new ClientErrorResponseBuilder(new InvalidFormatException()).build();
    }
}
