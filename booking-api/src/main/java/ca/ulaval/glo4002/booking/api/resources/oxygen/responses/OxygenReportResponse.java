package ca.ulaval.glo4002.booking.api.resources.oxygen.responses;

import java.util.List;

import ca.ulaval.glo4002.booking.application.oxygen.dtos.OxygenHistoryEntryDto;
import ca.ulaval.glo4002.booking.application.oxygen.dtos.OxygenInventoryEntryDto;

public class OxygenReportResponse {

    public final List<OxygenInventoryEntryDto> inventory;
    public final List<OxygenHistoryEntryDto> history;
     
    public OxygenReportResponse(List<OxygenInventoryEntryDto> inventory, List<OxygenHistoryEntryDto> history) {
        this.inventory = inventory;
        this.history = history;
    }
}
