package ca.ulaval.glo4002.booking.domain.transport;

import java.time.LocalDate;

import ca.ulaval.glo4002.booking.domain.Price;
import ca.ulaval.glo4002.booking.domain.profit.OutcomeSaver;

public class MillenniumFalcon extends Shuttle {
    
    public MillenniumFalcon(LocalDate date, OutcomeSaver outcomeSaver) {
        this.date = date;
        this.outcomeSaver = outcomeSaver;
        capacity = 20;
        category = ShuttleCategory.MILLENNIUM_FALCON;
        price = new Price(65000);
    }
}
