package ca.ulaval.glo4002.booking.domain.transport;

import java.time.LocalDate;

public class ETSpaceship extends Shuttle {
    
    public ETSpaceship(LocalDate date) {
        this.date = date;
        capacity = 1;
        category = ShuttleCategory.ET_SPACESHIP;
    }
}
