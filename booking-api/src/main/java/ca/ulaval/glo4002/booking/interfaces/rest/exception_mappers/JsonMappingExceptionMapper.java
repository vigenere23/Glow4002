package ca.ulaval.glo4002.booking.interfaces.rest.exception_mappers;

import javax.annotation.Priority;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.databind.JsonMappingException;

import ca.ulaval.glo4002.booking.interfaces.rest.exception_mappers.dtos.ClientErrorDto;
import ca.ulaval.glo4002.booking.interfaces.rest.exceptions.ClientException;
import ca.ulaval.glo4002.booking.interfaces.rest.exceptions.InvalidFormatClientException;
import ca.ulaval.glo4002.booking.interfaces.rest.exceptions.InvalidProgramClientException;
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
            ClientException clientException = new InvalidProgramClientException();
            ClientErrorDto clientErrorDto = new ClientErrorDto(clientException);
            return Response.status(clientException.status).entity(clientErrorDto).build();
        }

        ClientException clientException = new InvalidFormatClientException();
        ClientErrorDto errorDto = new ClientErrorDto(new InvalidFormatClientException());
        return Response.status(clientException.status).entity(errorDto).build();
    }
}
