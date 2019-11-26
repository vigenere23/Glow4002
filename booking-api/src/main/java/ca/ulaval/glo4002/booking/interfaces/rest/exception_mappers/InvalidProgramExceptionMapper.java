package ca.ulaval.glo4002.booking.interfaces.rest.exception_mappers;

import javax.annotation.Priority;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import ca.ulaval.glo4002.booking.interfaces.rest.exception_mappers.dtos.ClientErrorDto;
import ca.ulaval.glo4002.booking.interfaces.rest.exception_mappers.dtos.ClientErrorResponseBuilder;
import ca.ulaval.glo4002.booking.domain.exceptions.InvalidProgramException;

@Provider
@Priority(1)
@Path("/program")
public class InvalidProgramExceptionMapper implements ExceptionMapper<InvalidProgramException> {

    @Override
    public Response toResponse(InvalidProgramException exception) {
        ClientErrorDto clientErrorDto = new ClientErrorDto("INVALID_PROGRAM", exception.getMessage());
        return new ClientErrorResponseBuilder(clientErrorDto).build();
    }
}