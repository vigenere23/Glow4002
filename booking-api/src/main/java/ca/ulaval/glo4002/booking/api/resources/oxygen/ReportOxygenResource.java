package ca.ulaval.glo4002.booking.api.resources.oxygen;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ca.ulaval.glo4002.booking.api.resources.oxygen.responses.OxygenReportResponse;
import ca.ulaval.glo4002.booking.application.oxygen.OxygenUseCase;
import ca.ulaval.glo4002.booking.application.oxygen.dtos.OxygenHistoryEntryDto;
import ca.ulaval.glo4002.booking.application.oxygen.dtos.OxygenInventoryEntryDto;

@Path("/report/o2")
@Produces(MediaType.APPLICATION_JSON)
public class ReportOxygenResource {

    private OxygenUseCase oxygenUseCase;
    
    @Inject
    public ReportOxygenResource(OxygenUseCase oxygenUseCase) {
        this.oxygenUseCase = oxygenUseCase;
    }
    
    @GET
    public Response getOxygenReport() {
        List<OxygenInventoryEntryDto> inventory = oxygenUseCase.getOxygenInventory();
        List<OxygenHistoryEntryDto> history = oxygenUseCase.getOxygenHistory();
        OxygenReportResponse response = new OxygenReportResponse(inventory, history);
        return Response.ok(response).build();
    }
}
