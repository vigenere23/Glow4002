package ca.ulaval.glo4002.booking.domain.transport;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.profit.OutcomeSaver;

class ShuttleFactoryTest {

    private final static LocalDate DATE = LocalDate.of(2050, 7, 19);
    
    private ShuttleFactory shuttleFactory;
    private OutcomeSaver outcomeSaver;
    
    @BeforeEach
    public void setUpShuttleFactory() {
        outcomeSaver = mock(OutcomeSaver.class);
        shuttleFactory = new ShuttleFactory(outcomeSaver);
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
