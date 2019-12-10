package ca.ulaval.glo4002.booking.domain.transport;

import static org.mockito.Mockito.verify;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.ulaval.glo4002.booking.domain.transport.shuttles.ShuttleCategory;
import ca.ulaval.glo4002.booking.domain.transport.shuttles.ShuttleFiller;

@ExtendWith(MockitoExtension.class)
class TransportReserverTest {

    private final static ShuttleCategory A_SHUTTLE_CATEGORY = ShuttleCategory.SPACE_X;
    private final static LocalDate A_DATE = LocalDate.now();
    private final static PassengerNumber A_PASSENGER_NUMBER = new PassengerNumber(0);
    private final static int A_NUMBER_OF_PASSENGERS = 6;

    @Mock ShuttleFiller shuttleFiller;
    @InjectMocks TransportReserver transportReserver;

    @Test
    public void whenReservingArrivals_itDelegatesToShuttleFiller() {
        transportReserver.reserveArrivals(A_SHUTTLE_CATEGORY, A_DATE, A_PASSENGER_NUMBER, A_NUMBER_OF_PASSENGERS);
        verify(shuttleFiller).fillShuttles(Direction.ARRIVAL, A_DATE, A_SHUTTLE_CATEGORY, A_PASSENGER_NUMBER, A_NUMBER_OF_PASSENGERS);
    }

    @Test
    public void whenReservingDepartures_itDelegatesToShuttleFiller() {
        transportReserver.reserveDepartures(A_SHUTTLE_CATEGORY, A_DATE, A_PASSENGER_NUMBER, A_NUMBER_OF_PASSENGERS);
        verify(shuttleFiller).fillShuttles(Direction.DEPARTURE, A_DATE, A_SHUTTLE_CATEGORY, A_PASSENGER_NUMBER, A_NUMBER_OF_PASSENGERS);
    }

    @Test
    public void whenReservingArrivalWithoutNumberOfPassengers_itDelegatesToShuttleFillerWithOnePlace() {
        transportReserver.reserveArrival(A_SHUTTLE_CATEGORY, A_DATE, A_PASSENGER_NUMBER);
        verify(shuttleFiller).fillShuttles(Direction.ARRIVAL, A_DATE, A_SHUTTLE_CATEGORY, A_PASSENGER_NUMBER, 1);
    }

    @Test
    public void whenReservingDeparturesWithoutNumberOfPassengers_itDelegatesToShuttleFillerWithOnePlace() {
        transportReserver.reserveDeparture(A_SHUTTLE_CATEGORY, A_DATE, A_PASSENGER_NUMBER);
        verify(shuttleFiller).fillShuttles(Direction.DEPARTURE, A_DATE, A_SHUTTLE_CATEGORY, A_PASSENGER_NUMBER, 1);
    }
}
