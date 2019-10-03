package ca.ulaval.glo4002.booking.interfaces.rest.request;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import javax.ws.rs.core.MediaType;

import ca.ulaval.glo4002.booking.domain.festivals.Glow4002;
import ca.ulaval.glo4002.booking.interfaces.rest.response.Oxygen.ReportOxygenResponse;

@Path("/report/o2")
@Produces(MediaType.APPLICATION_JSON)
public class ReportOxygenResources {

    private Glow4002 festival;
    
    @Inject
    public ReportOxygenResources(Glow4002 festival) {
        this.festival = festival;
    }
    
    @GET
    public ReportOxygenResponse getOxygenReport() {
        return new ReportOxygenResponse(festival);
    }
}
