package ca.ulaval.glo4002.booking.interfaces.rest.dtos;

import java.time.OffsetDateTime;

public class OxygenHistoryDto {
    public OffsetDateTime date;
	public int qtyOxygenTankBought;
	public int qtyWaterUsed;
	public int qtyCandlesUsed;
	public int qtyOxygenTankMade;
}