package ca.ulaval.glo4002.booking.domain.transport;

import java.time.LocalDate;

import ca.ulaval.glo4002.booking.domain.Price;
import ca.ulaval.glo4002.booking.domain.profit.OutcomeSaver;

public class SpaceX extends Shuttle {
    
    public SpaceX(LocalDate date, OutcomeSaver outcomeSaver) {
        this.date = date;
        this.outcomeSaver = outcomeSaver;
        capacity = 30;
        category = ShuttleCategory.SPACE_X;
        price = new Price(30000);
    }
}
