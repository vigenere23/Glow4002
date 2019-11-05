package ca.ulaval.glo4002.booking.api.exceptionMappers;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import ca.ulaval.glo4002.booking.api.dtos.errors.ClientErrorDto;
import ca.ulaval.glo4002.booking.api.dtos.errors.ClientErrorResponseBuilder;
import ca.ulaval.glo4002.booking.domain.exceptions.InvalidProgramException;

@Provider
public class InvalidProgramExceptionMapper implements ExceptionMapper<InvalidProgramException> {

    @Override
    public Response toResponse(InvalidProgramException exception) {
        ClientErrorDto clientErrorDto = new ClientErrorDto("INVALID_PROGRAM", exception.getMessage());
        return new ClientErrorResponseBuilder(clientErrorDto).build();
    }
}