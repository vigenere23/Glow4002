package ca.ulaval.glo4002.booking.api.exceptionMappers;

import javax.annotation.Priority;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import ca.ulaval.glo4002.booking.api.resources.exceptionMappers.dto.ClientErrorDto;
import ca.ulaval.glo4002.booking.api.resources.exceptionMappers.dto.ClientErrorResponseBuilder;
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