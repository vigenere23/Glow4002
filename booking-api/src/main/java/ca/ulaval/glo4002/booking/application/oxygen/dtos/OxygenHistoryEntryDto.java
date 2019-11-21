package ca.ulaval.glo4002.booking.application.oxygen.dtos;

import java.time.LocalDate;

public class OxygenHistoryEntryDto {

    public LocalDate date;
    public int qtyOxygenTankBought;
    public int qtyWaterUsed;
    public int qtyCandlesUsed;
    public int qtyOxygenTankMade;

    public OxygenHistoryEntryDto() {}

    public OxygenHistoryEntryDto(LocalDate date, int qtyOxygenTankBought, int qtyWaterUsed, int qtyCandlesUsed, int qtyOxygenTankMade) {
        this.date = date;
        this.qtyOxygenTankBought = qtyOxygenTankBought;
        this.qtyWaterUsed = qtyWaterUsed;
        this.qtyCandlesUsed = qtyCandlesUsed;
        this.qtyOxygenTankMade = qtyOxygenTankMade;
    }
}
