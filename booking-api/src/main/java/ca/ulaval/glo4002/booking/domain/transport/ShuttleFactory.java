package ca.ulaval.glo4002.booking.domain.transport;

import java.time.LocalDate;

public class ShuttleFactory {

    public Shuttle create(Direction direction, LocalDate date, ShuttleCategory shuttleCategory) {
        switch (shuttleCategory) {
            case SPACE_X:
                return new SpaceX(direction, date);
            case MILLENNIUM_FALCON:
                return new MillenniumFalcon(direction, date);
            case ET_SPACESHIP:
                return new ETSpaceship(direction, date);
            default:
                throw new IllegalArgumentException(
                    String.format("No Shuttle implemented for shuttle category %s.", shuttleCategory)
                );
        }
    }
}
