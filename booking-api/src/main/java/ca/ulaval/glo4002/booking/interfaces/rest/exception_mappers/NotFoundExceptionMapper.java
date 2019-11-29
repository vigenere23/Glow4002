package ca.ulaval.glo4002.booking.interfaces.rest.exception_mappers;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import ca.ulaval.glo4002.booking.domain.exceptions.ItemNotFound;
import ca.ulaval.glo4002.booking.interfaces.rest.exception_mappers.dtos.ClientErrorDto;
import ca.ulaval.glo4002.booking.interfaces.rest.exception_mappers.dtos.ClientErrorResponseBuilder;

@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<ItemNotFound> {

    @Override
    public Response toResponse(ItemNotFound exception) {
        String error = String.format("%s_NOT_FOUND", exception.entityName.toUpperCase());
        String description = exception.getMessage();
        ClientErrorDto errorDto = new ClientErrorDto(error, description);
        return new ClientErrorResponseBuilder(errorDto).status(404).build();
    }
}
