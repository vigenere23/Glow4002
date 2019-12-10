package ca.ulaval.glo4002.booking.interfaces.rest.resources.oxygen;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ca.ulaval.glo4002.booking.application.oxygen.OxygenUseCase;
import ca.ulaval.glo4002.booking.application.oxygen.dtos.OxygenHistoryDto;
import ca.ulaval.glo4002.booking.application.oxygen.dtos.OxygenInventoryDto;
import ca.ulaval.glo4002.booking.interfaces.rest.resources.oxygen.responses.OxygenReportResponse;

@Path("/report/o2")
@Produces(MediaType.APPLICATION_JSON)
public class ReportOxygenResource {

    @Inject private OxygenUseCase oxygenUseCase;
    
    @GET
    public Response getOxygenReport() {
        OxygenInventoryDto inventory = oxygenUseCase.getOxygenInventory();
        OxygenHistoryDto history = oxygenUseCase.getOxygenHistory();
        OxygenReportResponse response = new OxygenReportResponse(inventory.entries, history.entries);
        return Response.ok(response).build();
    }
}
