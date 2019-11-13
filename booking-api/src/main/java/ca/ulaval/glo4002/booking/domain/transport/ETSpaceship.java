package ca.ulaval.glo4002.booking.domain.transport;

import java.time.LocalDate;

import ca.ulaval.glo4002.booking.domain.Price;
import ca.ulaval.glo4002.booking.domain.profit.OutcomeSaver;

public class ETSpaceship extends Shuttle {
    
    public ETSpaceship(LocalDate date, OutcomeSaver outcomeSaver) {
        this.date = date;
        this.outcomeSaver = outcomeSaver;
        capacity = 1;
        category = ShuttleCategory.ET_SPACESHIP;
        price = new Price(100000);
    }
}
