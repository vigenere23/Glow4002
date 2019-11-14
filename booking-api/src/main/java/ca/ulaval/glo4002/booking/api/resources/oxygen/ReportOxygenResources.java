package ca.ulaval.glo4002.booking.api.resources.oxygen;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ca.ulaval.glo4002.booking.api.resources.oxygen.dto.OxygenHistoryDto;
import ca.ulaval.glo4002.booking.api.resources.oxygen.dto.OxygenHistoryMapper;
import ca.ulaval.glo4002.booking.api.resources.oxygen.dto.OxygenInventoryDto;
import ca.ulaval.glo4002.booking.api.resources.oxygen.dto.OxygenInventoryMapper;
import ca.ulaval.glo4002.booking.api.resources.oxygen.dto.ReportOxygenResponse;
import ca.ulaval.glo4002.booking.application.OxygenUseCase;

@Path("/report/o2")
@Produces(MediaType.APPLICATION_JSON)
public class ReportOxygenResources {

    private OxygenUseCase oxygenUseCase;
    private final OxygenInventoryMapper oxygenInventoryMapper;
    private final OxygenHistoryMapper oxygenHistoryMapper;
    
    @Inject
    public ReportOxygenResources(OxygenUseCase oxygenUseCase) {
        this.oxygenUseCase = oxygenUseCase;
        oxygenInventoryMapper = new OxygenInventoryMapper();
        oxygenHistoryMapper = new OxygenHistoryMapper();
    }
    
    @GET
    public Response getOxygenReport() {
        List<OxygenInventoryDto> inventory = oxygenInventoryMapper.toDto(oxygenUseCase.getOxygenInventories());
        List<OxygenHistoryDto> history = oxygenHistoryMapper.toDto(oxygenUseCase.getOxygenHistory());
        ReportOxygenResponse response = new ReportOxygenResponse(inventory, history);
        return Response.ok(response).build();
    }
}
