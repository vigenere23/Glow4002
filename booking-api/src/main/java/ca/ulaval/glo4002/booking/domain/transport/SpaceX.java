package ca.ulaval.glo4002.booking.domain.transport;

import java.time.LocalDate;

public class SpaceX extends Shuttle {
    
    public SpaceX(LocalDate date) {
        this.capacity = 30;
        this.date = date;
        this.category = ShuttleCategory.SPACE_X;
    }
}
