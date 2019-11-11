package ca.ulaval.glo4002.booking.api.resources.exceptionMappers.dto;

import javax.ws.rs.core.Response;

import ca.ulaval.glo4002.booking.api.exceptions.ClientException;

public class ClientErrorResponseBuilder {

    private ClientErrorDto entity;
    private int status;

    public ClientErrorResponseBuilder(ClientErrorDto entity) {
        this.entity = entity;
        status = 400;
    }

    public ClientErrorResponseBuilder(ClientException exception) {
        entity = new ClientErrorDto(exception);
        status = exception.status;
    }

    public Response build() {
        return Response.status(status).entity(entity).build();
    }
}
