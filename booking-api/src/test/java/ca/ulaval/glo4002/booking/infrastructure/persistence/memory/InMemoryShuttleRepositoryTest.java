package ca.ulaval.glo4002.booking.infrastructure.persistence.memory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.ulaval.glo4002.booking.domain.exceptions.ItemAlreadyExists;
import ca.ulaval.glo4002.booking.domain.exceptions.ItemNotFound;
import ca.ulaval.glo4002.booking.domain.transport.Direction;
import ca.ulaval.glo4002.booking.domain.transport.ETSpaceship;
import ca.ulaval.glo4002.booking.domain.transport.PassengerNumber;
import ca.ulaval.glo4002.booking.domain.transport.Shuttle;
import ca.ulaval.glo4002.booking.domain.transport.ShuttleCategory;

@ExtendWith(MockitoExtension.class)
class InMemoryShuttleRepositoryTest {

    private static final Direction A_DIRECTION = Direction.DEPARTURE;
    private static final LocalDate A_DATE = LocalDate.now();
    private static final LocalDate SOME_OTHER_DATE = LocalDate.now().plusDays(3);
    private static final PassengerNumber A_PASSENGER_NUMBER = new PassengerNumber(0);
    private static final ShuttleCategory A_SHUTTLE_CATEGORY = ShuttleCategory.ET_SPACESHIP;
    private static final int NUMBER_OF_ARRIVALS = 10;
    private static final int NUMBER_OF_DEPARTURES = 8;

    Shuttle shuttle;
    private InMemoryShuttleRepository shuttleRepository;
    
    @BeforeEach
    public void setup() {
        shuttleRepository = new InMemoryShuttleRepository();
        shuttle = createShuttle(A_DIRECTION);
    }

    @Test
    public void whenCreating_ArrivalsAreEmpty() {
        List<Shuttle> arrivals = shuttleRepository.findAllByDirection(Direction.ARRIVAL);
        assertThat(arrivals).isEmpty();
    }

    @Test
    public void whenCreating_DeparturesAreEmpty() {
        List<Shuttle> departures = shuttleRepository.findAllByDirection(Direction.DEPARTURE);
        assertThat(departures).isEmpty();
    }

    @Test
    public void givenNonEmptyArrivalsAndDepartures_whenFindingAllArrivals_itReturnsOnlyArrivals() {
        addShuttles(NUMBER_OF_DEPARTURES, Direction.DEPARTURE);
        List<Shuttle> expectedShuttles = addShuttles(NUMBER_OF_ARRIVALS, Direction.ARRIVAL);

        List<Shuttle> returnedShuttles = shuttleRepository.findAllByDirection(Direction.ARRIVAL);

        assertThat(returnedShuttles).isEqualTo(expectedShuttles);
    }

    @Test
    public void givenNonEmptyArrivalsAndDepartures_whenFindingAllDepartures_itReturnsOnlyDepartures() {
        addShuttles(NUMBER_OF_ARRIVALS, Direction.ARRIVAL);
        List<Shuttle> expectedShuttles = addShuttles(NUMBER_OF_DEPARTURES, Direction.DEPARTURE);

        List<Shuttle> returnedShuttles = shuttleRepository.findAllByDirection(Direction.DEPARTURE);

        assertThat(returnedShuttles).isEqualTo(expectedShuttles);
    }

    @Test
    public void givenNonEmptyArrivalsAndDepartures_whenFindingAllArrivalsByDate_itReturnsOnlyArrivalsByDate() {
        addShuttles(NUMBER_OF_DEPARTURES, Direction.DEPARTURE, A_DATE);
        addShuttles(NUMBER_OF_ARRIVALS, Direction.ARRIVAL, SOME_OTHER_DATE);
        List<Shuttle> expectedShuttles = addShuttles(NUMBER_OF_ARRIVALS, Direction.ARRIVAL, A_DATE);

        List<Shuttle> returnedShuttles = shuttleRepository.findAllByDirectionAndDate(Direction.ARRIVAL, A_DATE);

        assertThat(returnedShuttles).isEqualTo(expectedShuttles);
    }

    @Test
    public void givenNonEmptyArrivalsAndDepartures_whenFindingAllDeparturesByDate_itReturnsOnlyDeparturesByDate() {
        addShuttles(NUMBER_OF_ARRIVALS, Direction.ARRIVAL, A_DATE);
        addShuttles(NUMBER_OF_DEPARTURES, Direction.DEPARTURE, SOME_OTHER_DATE);
        List<Shuttle> expectedShuttles = addShuttles(NUMBER_OF_DEPARTURES, Direction.DEPARTURE, A_DATE);

        List<Shuttle> returnedShuttles = shuttleRepository.findAllByDirectionAndDate(Direction.DEPARTURE, A_DATE);

        assertThat(returnedShuttles).isEqualTo(expectedShuttles);
    }

    @Test
    public void givenNonEmptyArrivalsAndDepartures_whenFindingAvailableArrivalShuttles_itReturnsOnlyNonFullShuttlesWithMatchingCriterias() {
        addShuttles(NUMBER_OF_ARRIVALS, Direction.ARRIVAL, A_DATE, 1);
        addShuttles(NUMBER_OF_DEPARTURES, Direction.DEPARTURE, A_DATE);
        List<Shuttle> expectedShuttles = addShuttles(NUMBER_OF_ARRIVALS, Direction.ARRIVAL, A_DATE);

        List<Shuttle> returnedShuttles = shuttleRepository.findAllAvailable(Direction.ARRIVAL, A_DATE, A_SHUTTLE_CATEGORY);

        assertThat(returnedShuttles).isEqualTo(expectedShuttles);
    }

    @Test
    public void givenNonEmptyArrivalsAndDepartures_whenFindingAvailableDepartureShuttles_itReturnsOnlyNonFullShuttlesWithMatchingCriterias() {
        addShuttles(NUMBER_OF_DEPARTURES, Direction.DEPARTURE, A_DATE, 1);
        addShuttles(NUMBER_OF_ARRIVALS, Direction.ARRIVAL, A_DATE);
        List<Shuttle> expectedShuttles = addShuttles(NUMBER_OF_DEPARTURES, Direction.DEPARTURE, A_DATE);

        List<Shuttle> returnedShuttles = shuttleRepository.findAllAvailable(Direction.DEPARTURE, A_DATE, A_SHUTTLE_CATEGORY);

        assertThat(returnedShuttles).isEqualTo(expectedShuttles);
    }

    @Test
    public void whenReplacingInexistentItem_itThrowItemNotFoundException() {
        assertThatExceptionOfType(ItemNotFound.class).isThrownBy(() -> {
            shuttleRepository.replace(shuttle);
        });
    }

    @Test
    public void whenReplacingExistentItem_itReplacesTheItem() {
        shuttleRepository.add(shuttle);

        shuttle.addPassengers(A_PASSENGER_NUMBER, 1);
        shuttleRepository.replace(shuttle);

        Shuttle returnedShuttle = shuttleRepository.findAllByDirection(A_DIRECTION).get(0);
        assertThat(returnedShuttle).isEqualTo(shuttle);
        assertThat(returnedShuttle.getPassengerNumbers()).containsOnly(A_PASSENGER_NUMBER);
    }

    @Test
    public void whenAddingItem_thenTheItemCanBeRetreived() {
        shuttleRepository.add(shuttle);

        Shuttle returnedShuttle = shuttleRepository.findAllByDirection(A_DIRECTION).get(0);
        assertThat(returnedShuttle).isEqualTo(shuttle);
    }

    @Test
    public void whenAddingItemTwoTimes_itThrowsAnItemAlreadyExistsException() {
        shuttleRepository.add(shuttle);
        
        assertThatExceptionOfType(ItemAlreadyExists.class).isThrownBy(() -> {
            shuttleRepository.add(shuttle);
        });
    }

    private Shuttle createShuttle(Direction direction) {
        return createShuttle(direction, A_DATE);
    }

    private Shuttle createShuttle(Direction direction, LocalDate date) {
        return new ETSpaceship(direction, date);
    }

    private List<Shuttle> addShuttles(int quantity, Direction direction) {
        return addShuttles(quantity, direction, A_DATE);
    }

    private List<Shuttle> addShuttles(int quantity, Direction direction, LocalDate date) {
        List<Shuttle> shuttlesAdded = new ArrayList<>();
        for (int i = 0; i < quantity; i++) {
            Shuttle shuttle = createShuttle(direction, date);
            shuttleRepository.add(shuttle);
            shuttlesAdded.add(shuttle);
        }

        return shuttlesAdded;
    }

    private List<Shuttle> addShuttles(int quantity, Direction direction, LocalDate date, int numberOfPassengers) {
        List<Shuttle> shuttlesAdded = new ArrayList<>();
        for (int i = 0; i < quantity; i++) {
            Shuttle shuttle = createShuttle(direction, date);
            shuttle.addPassengers(A_PASSENGER_NUMBER, numberOfPassengers);
            shuttleRepository.add(shuttle);
            shuttlesAdded.add(shuttle);
        }
        return shuttlesAdded;
    }
}
