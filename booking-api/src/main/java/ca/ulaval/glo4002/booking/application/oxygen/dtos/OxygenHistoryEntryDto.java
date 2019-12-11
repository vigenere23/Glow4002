package ca.ulaval.glo4002.booking.application.oxygen.dtos;

import java.time.LocalDate;

public class OxygenHistoryEntryDto {

    public LocalDate date;
    public int tankBought;
    public int waterQuantity;
    public int candlesQuantity;
    public int tankMade;

    public OxygenHistoryEntryDto(LocalDate date, int tankBought, int waterQuantity, int candlesQuantity, int tankMade) {
        this.date = date;
        this.tankBought = tankBought;
        this.waterQuantity = waterQuantity;
        this.candlesQuantity = candlesQuantity;
        this.tankMade = tankMade;
    }
}
