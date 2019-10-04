package ca.ulaval.glo4002.booking.interfaces.rest.dtos.oxygen;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;

import ca.ulaval.glo4002.booking.domain.pressurizedGaz.OxygenExposer;
import ca.ulaval.glo4002.booking.interfaces.rest.dtoMappers.OxygenMapper;
import ca.ulaval.glo4002.booking.interfaces.rest.dtos.oxygen.OxygenHistoryDto;
import ca.ulaval.glo4002.booking.interfaces.rest.dtos.oxygen.OxygenInventoryDto;


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