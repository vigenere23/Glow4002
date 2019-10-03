package ca.ulaval.glo4002.booking.domain.transport;

import java.time.LocalDate;

public class ETSpaceship extends Shuttle {
    
    public ETSpaceship(LocalDate date) {
        this.capacity = 1;
        this.date = date;
        this.category = ShuttleCategory.ET_SPACESHIP;
    }
}
