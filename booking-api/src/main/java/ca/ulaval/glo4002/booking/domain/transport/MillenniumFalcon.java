package ca.ulaval.glo4002.booking.domain.transport;

import java.time.LocalDate;

public class MillenniumFalcon extends Shuttle {
    
    public MillenniumFalcon(LocalDate date) {
        this.date = date;
        capacity = 20;
        category = ShuttleCategory.MILLENNIUM_FALCON;
    }
}
