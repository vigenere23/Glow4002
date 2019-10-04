package ca.ulaval.glo4002.booking.interfaces.rest.dtos.oxygen;

import java.time.LocalDate;

public class OxygenHistoryDto {
	
    public LocalDate date;
	public int qtyOxygenTankBought;
	public int qtyWaterUsed;
	public int qtyCandlesUsed;
	public int qtyOxygenTankMade;
}