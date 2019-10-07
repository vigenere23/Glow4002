package ca.ulaval.glo4002.booking.api.dtos.oxygen;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;

import ca.ulaval.glo4002.booking.services.dtoMappers.OxygenMapper;
import ca.ulaval.glo4002.booking.services.oxygen.OxygenExposer;

public class ReportOxygenResponse {

    public final List<OxygenInventoryDto> oxygenInventory;
    public final List<OxygenHistoryDto> oxygenHistory;
     
    @JsonCreator
    public ReportOxygenResponse(OxygenExposer exposer) {
        OxygenMapper mapper = new OxygenMapper();
        
        this.oxygenInventory = mapper.convertInventoryToDto(exposer.getInventory());
        this.oxygenHistory = mapper.convertHistoryToDto(exposer.getOxygenHistory());
    }
}
