package ca.ulaval.glo4002.booking.api.dtos.oxygen;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;

public class ReportOxygenResponse {

    public final List<OxygenInventoryDto> oxygenInventory;
    public final List<OxygenHistoryDto> oxygenHistory;
     
    @JsonCreator
    public ReportOxygenResponse(List<OxygenInventoryDto> inventory, List<OxygenHistoryDto> history) {
        this.oxygenInventory = inventory;
        this.oxygenHistory = history;
    }
}
