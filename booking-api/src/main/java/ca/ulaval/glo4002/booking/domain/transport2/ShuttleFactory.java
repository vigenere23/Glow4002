package ca.ulaval.glo4002.booking.domain.transport2;

import java.time.LocalDate;

import javax.inject.Inject;

import ca.ulaval.glo4002.booking.domain.exceptions.NotFoundException;

public class ShuttleFactory {

    @Inject ShuttleRepository shuttleRepository;
    
    public Shuttle create(Direction direction, LocalDate date, ShuttleCategory shuttleCategory) {
        try {
            return shuttleRepository.findNextAvailable(direction, date, shuttleCategory);
        }
        catch (NotFoundException exception) {
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
}
