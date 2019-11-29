package ca.ulaval.glo4002.booking.domain.transport;

import java.time.LocalDate;

import ca.ulaval.glo4002.booking.domain.Price;

public class MillenniumFalcon extends Shuttle {
    
    public MillenniumFalcon(Direction direction, LocalDate date) {
        super(direction, date, ShuttleCategory.MILLENNIUM_FALCON, 20, new Price(65000));
    }
}
