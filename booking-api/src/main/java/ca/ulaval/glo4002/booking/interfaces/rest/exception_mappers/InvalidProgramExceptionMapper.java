package ca.ulaval.glo4002.booking.interfaces.rest.exception_mappers;

import javax.annotation.Priority;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import ca.ulaval.glo4002.booking.domain.program.exceptions.InvalidProgramException;
import ca.ulaval.glo4002.booking.interfaces.rest.exception_mappers.dtos.ClientErrorDto;
import ca.ulaval.glo4002.booking.interfaces.rest.exceptions.ClientException;
import ca.ulaval.glo4002.booking.interfaces.rest.exceptions.InvalidProgramClientException;

@Provider
@Priority(1)
public class InvalidProgramExceptionMapper implements ExceptionMapper<InvalidProgramException> {

    @Override
    public Response toResponse(InvalidProgramException exception) {
        ClientException clientException = new InvalidProgramClientException();
        ClientErrorDto clientErrorDto = new ClientErrorDto(clientException);
        return Response.status(clientException.status).entity(clientErrorDto).build();
    }
}
