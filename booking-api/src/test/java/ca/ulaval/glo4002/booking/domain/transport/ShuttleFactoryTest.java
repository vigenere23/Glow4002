package ca.ulaval.glo4002.booking.domain.transport;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShuttleFactoryTest {

    private final static LocalDate DATE = LocalDate.now();
    private final static Direction DIRECTION = Direction.ARRIVAL;
    
    private ShuttleFactory shuttleFactory;
    
    @BeforeEach
    public void setUpShuttleFactory() {
        shuttleFactory = new ShuttleFactory();
    }
    
    @Test
    void givenETSpaceshipCategory_whenCreateShuttle_thenCreatesNewETSpaceship() {
        Shuttle shuttleTest = shuttleFactory.create(DIRECTION, DATE, ShuttleCategory.ET_SPACESHIP);
        assertThat(shuttleTest).isInstanceOf(ETSpaceship.class);
    }

    @Test
    void givenMillenniumFalconCategory_whenCreateShuttle_thenCreatesNewMillenniumFalcon() {
        Shuttle shuttleTest = shuttleFactory.create(DIRECTION, DATE, ShuttleCategory.MILLENNIUM_FALCON);
        assertThat(shuttleTest).isInstanceOf(MillenniumFalcon.class);

    }
    
    @Test
    void givenSpaceXCategory_whenCreateShuttle_thenCreatesNewSpaceX() {
        Shuttle shuttleTest = shuttleFactory.create(DIRECTION, DATE, ShuttleCategory.SPACE_X);
        assertThat(shuttleTest).isInstanceOf(SpaceX.class);

    }
}
