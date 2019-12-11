package ca.ulaval.glo4002.booking.interfaces.rest.resources.oxygen.responses;

import java.util.List;
import java.util.stream.Collectors;

import ca.ulaval.glo4002.booking.application.oxygen.dtos.OxygenHistoryEntryDto;
import ca.ulaval.glo4002.booking.application.oxygen.dtos.OxygenInventoryEntryDto;

public class OxygenReportResponse {

    public final List<OxygenInventoryEntryResponse> inventory;
    public final List<OxygenHistoryEntryResponse> history;
     
    public OxygenReportResponse(List<OxygenInventoryEntryDto> inventoryEntries, List<OxygenHistoryEntryDto> historyEntries) {
        inventory = inventoryEntries
            .stream()
            .filter(entry -> entry.quantity > 0)
            .map(dto -> new OxygenInventoryEntryResponse(dto))
            .collect(Collectors.toList());
        history = historyEntries
            .stream()
            .map(dto -> new OxygenHistoryEntryResponse(dto))
            .collect(Collectors.toList());;
    }
}
