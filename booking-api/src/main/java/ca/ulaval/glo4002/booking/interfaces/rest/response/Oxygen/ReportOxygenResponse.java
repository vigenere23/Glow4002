package ca.ulaval.glo4002.booking.interfaces.rest.response.Oxygen;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;

import ca.ulaval.glo4002.booking.interfaces.rest.mappers.OxygenHistoryDto;
import ca.ulaval.glo4002.booking.interfaces.rest.mappers.OxygenInventoryDto;
import ca.ulaval.glo4002.booking.interfaces.rest.mappers.OxygenMapper;
import ca.ulaval.glo4002.booking.domain.pressurizedGaz.OxygenExposer;


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
