package ca.ulaval.glo4002.booking.api.resources;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import javax.ws.rs.core.MediaType;

import ca.ulaval.glo4002.booking.api.dtos.oxygen.ReportOxygenResponse;
import ca.ulaval.glo4002.booking.services.oxygen.OxygenExposer;

@Path("/report/o2")
@Produces(MediaType.APPLICATION_JSON)
public class ReportOxygenResources {

    private OxygenExposer oxygenExposer;
    
    @Inject
    public ReportOxygenResources(OxygenExposer oxygenExposer) {
        this.oxygenExposer = oxygenExposer;
    }
    
    @GET
    public ReportOxygenResponse getOxygenReport() {
        return new ReportOxygenResponse(oxygenExposer);
    }
}
