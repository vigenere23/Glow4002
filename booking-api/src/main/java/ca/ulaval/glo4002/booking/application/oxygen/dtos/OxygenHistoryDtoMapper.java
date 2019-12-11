package ca.ulaval.glo4002.booking.application.oxygen.dtos;

import java.util.List;
import java.util.stream.Collectors;

import ca.ulaval.glo4002.booking.domain.oxygen.history.OxygenHistoryEntry;

public class OxygenHistoryDtoMapper {

    public OxygenHistoryDto toDto(List<OxygenHistoryEntry> historyEntries) {
        return new OxygenHistoryDto(
            historyEntries
                .stream()
                .map(entry -> toEntryDto(entry))
                .collect(Collectors.toList())
        );
    }

    public OxygenHistoryEntryDto toEntryDto(OxygenHistoryEntry historyEntry) {
        return new OxygenHistoryEntryDto(
            historyEntry.getDate(),
            historyEntry.getTankBought(),
            historyEntry.getWaterUsed(),
            historyEntry.getCandlesUsed(),
            historyEntry.getTankMade()
        );
    }
}
