package ca.ulaval.glo4002.booking.domain.transport;

import java.time.LocalDate;

import ca.ulaval.glo4002.booking.domain.profit.OutcomeSaver;

public class ShuttleFactory {

    private OutcomeSaver outcomeSaver;

    public ShuttleFactory(OutcomeSaver outcomeSaver) {
        this.outcomeSaver = outcomeSaver;
    }
    
    public Shuttle createShuttle(ShuttleCategory shuttleCategory, LocalDate date) {
        switch (shuttleCategory) {
            case SPACE_X:
                return new SpaceX(date, outcomeSaver);
            case MILLENNIUM_FALCON:
                return new MillenniumFalcon(date, outcomeSaver);
            case ET_SPACESHIP:
                return new ETSpaceship(date, outcomeSaver);
            default:
                throw new IllegalArgumentException(
                    String.format("No category implemented for this category %s.", shuttleCategory)
                );
        }
    }
}
