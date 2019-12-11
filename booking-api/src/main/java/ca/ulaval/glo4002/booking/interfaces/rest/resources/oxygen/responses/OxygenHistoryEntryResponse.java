package ca.ulaval.glo4002.booking.interfaces.rest.resources.oxygen.responses;

import java.time.LocalDate;

import ca.ulaval.glo4002.booking.application.oxygen.dtos.OxygenHistoryEntryDto;

public class OxygenHistoryEntryResponse {

    public LocalDate date;
    public int qtyOxygenTankBought;
    public int qtyWaterUsed;
    public int qtyCandlesUsed;
    public int qtyOxygenTankMade;

    public OxygenHistoryEntryResponse(OxygenHistoryEntryDto dto) {
        date = dto.date;
        qtyOxygenTankBought = dto.tankBought;
        qtyWaterUsed = dto.waterQuantity;
        qtyCandlesUsed = dto.candlesQuantity;
        qtyOxygenTankMade = dto.tankMade;
    }
}
