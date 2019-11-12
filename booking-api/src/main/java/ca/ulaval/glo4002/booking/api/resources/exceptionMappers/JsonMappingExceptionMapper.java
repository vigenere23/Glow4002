package ca.ulaval.glo4002.booking.api.resources.exceptionMappers;

import javax.annotation.Priority;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.databind.JsonMappingException;

import ca.ulaval.glo4002.booking.api.exceptions.InvalidFormatException;
import ca.ulaval.glo4002.booking.api.resources.ProgramResource;
import ca.ulaval.glo4002.booking.api.resources.exceptionMappers.dto.ClientErrorDto;
import ca.ulaval.glo4002.booking.api.resources.exceptionMappers.dto.ClientErrorResponseBuilder;

@Provider
@Priority(2)
public class JsonMappingExceptionMapper implements ExceptionMapper<JsonMappingException> {

    @Context
    private ResourceInfo resourceInfo;
    
    @Override
    public Response toResponse(JsonMappingException exception) {

        Class<?> Object = resourceInfo.getResourceClass();
        if(Object.equals(ProgramResource.class)) {
            ClientErrorDto clientErrorDto = new ClientErrorDto("INVALID_PROGRAM", "the program is invalid");
            return new ClientErrorResponseBuilder(clientErrorDto).build();
        }

        return new ClientErrorResponseBuilder(new InvalidFormatException()).build();
    }
}
