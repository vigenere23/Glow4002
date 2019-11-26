package ca.ulaval.glo4002.booking.api.exception_mappers.dtos;

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

    public ClientErrorResponseBuilder status(int status) {
        this.status = status;
        return this;
    }

    public Response build() {
        return Response.status(status).entity(entity).build();
    }
}
