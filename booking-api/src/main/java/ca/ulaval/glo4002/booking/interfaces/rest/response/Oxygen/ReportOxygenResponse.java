package ca.ulaval.glo4002.booking.interfaces.rest.response.Oxygen;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;

import ca.ulaval.glo4002.booking.domain.festivals.Glow4002;
import ca.ulaval.glo4002.booking.domain.pressurizedGaz.InventoryDto;
import ca.ulaval.glo4002.booking.domain.pressurizedGaz.HistoryDto;


public class ReportOxygenResponse {

	 public final List<InventoryDto> oxygenInventory;
	 public final List<HistoryDto> oxygenHistory;
	 
	@JsonCreator
    public ReportOxygenResponse(Glow4002 festival) {
		this.oxygenInventory = festival.getOxygenRequester().getInventory();
		this.oxygenHistory = festival.getOxygenRequester().getOxygenHistory();
    }
}
