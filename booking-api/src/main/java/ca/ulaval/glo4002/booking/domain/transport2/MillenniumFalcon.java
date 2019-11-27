package ca.ulaval.glo4002.booking.domain.transport2;

import java.time.LocalDate;

import ca.ulaval.glo4002.booking.domain.Price;

public class MillenniumFalcon extends Shuttle {
    
    public MillenniumFalcon(LocalDate date) {
        this.date = date;
        capacity = 20;
        category = ShuttleCategory.MILLENNIUM_FALCON;
        price = new Price(65000);
    }
}
