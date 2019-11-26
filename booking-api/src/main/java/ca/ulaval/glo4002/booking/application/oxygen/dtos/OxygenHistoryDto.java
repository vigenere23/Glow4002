package ca.ulaval.glo4002.booking.application.oxygen.dtos;

import java.util.List;

public class OxygenHistoryDto {

    public List<OxygenHistoryEntryDto> entries;

    public OxygenHistoryDto(List<OxygenHistoryEntryDto> entries) {
        this.entries = entries;
    }
}
