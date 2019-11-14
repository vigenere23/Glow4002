package ca.ulaval.glo4002.booking.domain.transport;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShuttleFactoryTest {

    private final static LocalDate DATE = LocalDate.of(2050, 7, 19);
    
    private ShuttleFactory shuttleFactory;
    
    @BeforeEach
    public void setUpShuttleFactory() {
        shuttleFactory = new ShuttleFactory();
    }
    
    @Test
    void givenETSpaceshipCategory_whenCreateShuttle_thenCreatesNewETSpaceship() {
        Shuttle shuttleTest = shuttleFactory.createShuttle(ShuttleCategory.ET_SPACESHIP, DATE);
        assertTrue(shuttleTest instanceof ETSpaceship);
    }

    @Test
    void givenMillenniumFalconCategory_whenCreateShuttle_thenCreatesNewMillenniumFalcon() {
        Shuttle shuttleTest = shuttleFactory.createShuttle(ShuttleCategory.MILLENNIUM_FALCON, DATE);
        assertTrue(shuttleTest instanceof MillenniumFalcon);
    }
    
    @Test
    void givenSpaceXCategory_whenCreateShuttle_thenCreatesNewSpaceX() {
        Shuttle shuttleTest = shuttleFactory.createShuttle(ShuttleCategory.SPACE_X, DATE);
        assertTrue(shuttleTest instanceof SpaceX);
    }
}
