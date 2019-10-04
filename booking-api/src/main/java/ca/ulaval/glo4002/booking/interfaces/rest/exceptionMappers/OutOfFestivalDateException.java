package ca.ulaval.glo4002.booking.interfaces.rest.exceptionMappers;

import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.annotation.JsonCreator;

import ca.ulaval.glo4002.booking.interfaces.rest.exceptionMappers.OutOfFestivalDateExceptionMessage;

import javax.ws.rs.core.MediaType;

@Provider
@Produces(MediaType.APPLICATION_JSON)
public class OutOfFestivalDateException extends Exception implements ExceptionMapper<OutOfFestivalDateException> {

    public OutOfFestivalDateExceptionMessage msg = new OutOfFestivalDateExceptionMessage();

    @JsonCreator
    public OutOfFestivalDateException() {
        super();
        msg.error = "INVALID_EVENT_DATE";
        msg.description = "event date should be between July 17 2050 and July 24 2050";
    }

    @Override
    public Response toResponse(OutOfFestivalDateException exception) {
        return Response.status(400).entity(msg).type(MediaType.APPLICATION_JSON).build();
    }
}