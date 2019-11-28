package ca.ulaval.glo4002.booking.domain.transport2;

import java.time.LocalDate;

import ca.ulaval.glo4002.booking.domain.Price;

public class ETSpaceship extends Shuttle {
    
    public ETSpaceship(Direction direction, LocalDate date) {
        super(direction, date, ShuttleCategory.ET_SPACESHIP, 1, new Price(100000));
    }
}
