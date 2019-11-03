package ca.ulaval.glo4002.booking.api.resources;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ca.ulaval.glo4002.booking.api.dtoMappers.OxygenHistoryMapper;
import ca.ulaval.glo4002.booking.api.dtoMappers.OxygenInventoryMapper;
import ca.ulaval.glo4002.booking.api.dtos.oxygen.OxygenHistoryDto;
import ca.ulaval.glo4002.booking.api.dtos.oxygen.OxygenInventoryDto;
import ca.ulaval.glo4002.booking.api.dtos.oxygen.ReportOxygenResponse;
import ca.ulaval.glo4002.booking.domain.application.OxygenUseCase;

@Path("/report/o2")
@Produces(MediaType.APPLICATION_JSON)
public class ReportOxygenResources {

    private OxygenUseCase oxygenUseCase;
    
    @Inject
    public ReportOxygenResources(OxygenUseCase oxygenUseCase) {
        this.oxygenUseCase = oxygenUseCase;
    }
    
    @GET
    public ReportOxygenResponse getOxygenReport() {
        List<OxygenInventoryDto> inventory = new OxygenInventoryMapper().toDto(oxygenUseCase.getOxygenInventories());
        List<OxygenHistoryDto> history = new OxygenHistoryMapper().toDto(oxygenUseCase.getOxygenHistory());
        return new ReportOxygenResponse(inventory, history);
    }
}
