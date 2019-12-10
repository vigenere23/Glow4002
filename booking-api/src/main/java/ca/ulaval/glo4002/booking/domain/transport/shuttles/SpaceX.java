package ca.ulaval.glo4002.booking.domain.transport.shuttles;

import java.time.LocalDate;

import ca.ulaval.glo4002.booking.domain.Price;
import ca.ulaval.glo4002.booking.domain.transport.Direction;

public class SpaceX extends Shuttle {
    
    public SpaceX(Direction direction, LocalDate date) {
        super(direction, date, ShuttleCategory.SPACE_X, 30, new Price(30000));
    }
}
