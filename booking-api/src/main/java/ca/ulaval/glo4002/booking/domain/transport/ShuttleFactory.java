package ca.ulaval.glo4002.booking.domain.transport;

import java.time.LocalDate;

public class ShuttleFactory {
    
    public Shuttle createShuttle(ShuttleCategory shuttleCategory, LocalDate date) {
        switch(shuttleCategory) {
            case SPACE_X:
                return new SpaceX(date);
            case MILLENNIUM_FALCON:
                return new MillenniumFalcon(date);
            case ET_SPACESHIP:
                return new ETSpaceship(date);
            default:
                throw new IllegalArgumentException(
                    String.format("No category implemented for this category %s.", shuttleCategory)
                );
        }
    }
}
