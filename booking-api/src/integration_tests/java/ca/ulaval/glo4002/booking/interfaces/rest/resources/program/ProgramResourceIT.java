package ca.ulaval.glo4002.booking.interfaces.rest.resources.program;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.interfaces.rest.resources.JerseyTestBookingServer;

public class ProgramResourceIT extends JerseyTestBookingServer {

    private static final String PROGRAM_URL = "/program";
    private String invalidProgram = "{\"program\": [{}]}";

    @Test
    public void giveninvalidProgramPosted_thenReturnsCorrectStatusCode() {
        Response response = target(PROGRAM_URL).request().post(Entity.json(invalidProgram));

        assertEquals(Status.BAD_REQUEST.getStatusCode(), response.getStatus(), "Http Response should be 400 ");
    }

    @Test
    public void givenValidProgramPosted_thenReturnsResponseWithCorrectLocationHeader() {
        Response response = target(PROGRAM_URL).request().post(Entity.json(invalidProgram));

        assertEquals("application/json", response.getHeaderString(HttpHeaders.CONTENT_TYPE));
    }
}
