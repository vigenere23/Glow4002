package ca.ulaval.glo4002.booking.domain.transport;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.ulaval.glo4002.booking.domain.Price;
import ca.ulaval.glo4002.booking.domain.profit.OutcomeSaver;

@ExtendWith(MockitoExtension.class)
public class ShuttleTest {

    private final static int ONE_PLACE = 1;
    private final static int CAPACITY = 2;
    private final static Direction DIRECTION = Direction.ARRIVAL;
    private final static PassengerNumber PASSENGER_NUMBER = new PassengerNumber(0);
    private final static Price PRICE = new Price(100);
	
    private ShuttleImplementationTest shuttle;

    @Mock OutcomeSaver outcomeSaver;

    private class ShuttleImplementationTest extends Shuttle {

        public ShuttleImplementationTest(Direction direction, LocalDate date, Price price) {
            super(direction, date, ShuttleCategory.SPACE_X, CAPACITY, price);
        }
    }    

    @BeforeEach
    public void setUp() {
        shuttle = new ShuttleImplementationTest(DIRECTION, LocalDate.of(2050, 7, 17), PRICE);
    }

    @Test
    public void whenCreating_noPassengerIsPresent() {
        assertThat(shuttle.getPassengerNumbers()).isEmpty();
    }

    @Test
    public void givenEnoughSpace_whenAddingPassengers_itAddsThePassengersToTheList() {
        List<PassengerNumber> expectedPassengerNumbers = new ArrayList<>();
        for (int i = 0; i < CAPACITY; i++) { expectedPassengerNumbers.add(PASSENGER_NUMBER); }

        shuttle.addPassengers(PASSENGER_NUMBER, CAPACITY);

        assertThat(shuttle.getPassengerNumbers()).isEqualTo(expectedPassengerNumbers);
    }
    
    @Test
    public void givenPartiallyFullShuttle_whenCheckingIfFull_itReturnsFalse() {
        shuttle.addPassengers(PASSENGER_NUMBER, ONE_PLACE);
        assertThat(shuttle.isFull()).isFalse();
    }

    @Test
    public void givenFullShuttle_whenCheckingIfFull_itReturnsTrue() {
        shuttle.addPassengers(PASSENGER_NUMBER, CAPACITY);

        assertThat(shuttle.isFull()).isTrue();
    }

    @Test
    public void whenAddingTooManyPassengers_itThrowsAnIllegalArgumentException() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            shuttle.addPassengers(PASSENGER_NUMBER, CAPACITY + 1);
        });
    }

    @Test
    public void givenOutcomeSaver_whenSaveOutcome_thenSaveOutcomeFromOutcomeSaverWithShuttlePriceIsCalled() {
        shuttle.saveOutcome(outcomeSaver);

        verify(outcomeSaver).saveOutcome(PRICE);
    }
}
