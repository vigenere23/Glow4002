package ca.ulaval.glo4002.booking.api.resources.oxygen.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;

public class ReportOxygenResponse {

    public final List<OxygenInventoryDto> inventory;
    public final List<OxygenHistoryDto> history;
     
    @JsonCreator
    public ReportOxygenResponse(List<OxygenInventoryDto> inventory, List<OxygenHistoryDto> history) {
        this.inventory = inventory;
        this.history = history;
    }
}
