package ca.ulaval.glo4002.booking.application.oxygen.dtos;

import ca.ulaval.glo4002.booking.domain.oxygen.history.OxygenHistoryEntry;

import java.util.List;
import java.util.stream.Collectors;

public class OxygenHistoryEntryDtoMapper {

    public List<OxygenHistoryEntryDto> toDtos(List<OxygenHistoryEntry> historyEntries) {
        return historyEntries
            .stream()
            .map(entry -> toDto(entry))
            .collect(Collectors.toList());
    }

    public OxygenHistoryEntryDto toDto(OxygenHistoryEntry historyEntry) {
        return new OxygenHistoryEntryDto(
            historyEntry.getDate(),
            historyEntry.getTankBought(),
            historyEntry.getWaterUsed(),
            historyEntry.getCandlesUsed(),
            historyEntry.getTankMade()
        );
    }
}
