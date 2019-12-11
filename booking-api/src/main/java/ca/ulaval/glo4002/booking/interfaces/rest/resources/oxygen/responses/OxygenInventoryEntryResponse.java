package ca.ulaval.glo4002.booking.interfaces.rest.resources.oxygen.responses;

import ca.ulaval.glo4002.booking.application.oxygen.dtos.OxygenInventoryEntryDto;

public class OxygenInventoryEntryResponse {

    public String gradeTankOxygen;
    public int quantity;

    public OxygenInventoryEntryResponse(OxygenInventoryEntryDto dto) {
        gradeTankOxygen = dto.oxygenGrade.toString();
        quantity = dto.quantity;
    }
}
