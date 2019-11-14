package ca.ulaval.glo4002.booking.domain.transport;

import java.time.LocalDate;

import ca.ulaval.glo4002.booking.domain.Price;

public class SpaceX extends Shuttle {
    
    public SpaceX(LocalDate date) {
        this.date = date;
        capacity = 30;
        category = ShuttleCategory.SPACE_X;
        price = new Price(30000);
    }
}
