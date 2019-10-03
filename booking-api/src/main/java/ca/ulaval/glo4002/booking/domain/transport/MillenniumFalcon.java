package ca.ulaval.glo4002.booking.domain.transport;

import java.time.LocalDate;

public class MillenniumFalcon extends Shuttle {
    
    public MillenniumFalcon(LocalDate date) {
        this.capacity = 20;
        this.date = date;
        this.category = ShuttleCategory.MILLENNIUM_FALCON;
    }
}
