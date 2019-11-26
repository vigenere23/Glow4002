package ca.ulaval.glo4002.booking.application.oxygen.dtos;

import java.util.List;

public class OxygenInventoryDto {

    public List<OxygenInventoryEntryDto> entries;

    public OxygenInventoryDto(List<OxygenInventoryEntryDto> entries) {
        this.entries = entries;
    }
}
