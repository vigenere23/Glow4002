package ca.ulaval.glo4002.booking.api.resources;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import javax.ws.rs.core.MediaType;

import ca.ulaval.glo4002.booking.api.dtos.oxygen.OxygenHistoryDto;
import ca.ulaval.glo4002.booking.api.dtos.oxygen.OxygenInventoryDto;
import ca.ulaval.glo4002.booking.api.dtos.oxygen.ReportOxygenResponse;
import ca.ulaval.glo4002.booking.services.dtoMappers.OxygenMapper;
import ca.ulaval.glo4002.booking.services.exposers.OxygenExposer;

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
        List<OxygenInventoryDto> inventory = new OxygenMapper().convertInventoryToDto(oxygenExposer.getInventory());
        List<OxygenHistoryDto> history = new OxygenMapper().convertHistoryToDto(oxygenExposer.getOxygenHistory());
        return new ReportOxygenResponse(inventory, history);
    }
}
