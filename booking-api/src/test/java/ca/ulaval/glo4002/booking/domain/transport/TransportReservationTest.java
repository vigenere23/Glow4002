package ca.ulaval.glo4002.booking.domain.transport;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.mock;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.passes.PassNumber;

class TransportReservationTest {

    private final static ShuttleCategory SOME_SHUTTLE_CATEGORY = ShuttleCategory.ET_SPACESHIP;
    private final static PassNumber SOME_PASS_NUMBER = mock(PassNumber.class);
    private final static LocalDate SOME_DATE = LocalDate.of(2050, 7, 18);
    private List<Shuttle> someShuttles = new LinkedList<>();
    private TransportReservation transportReservation;

    @BeforeEach
    public void setUp() {
        Shuttle mockedShuttle = mock(SpaceX.class);
        someShuttles.add(mockedShuttle);

        transportReservation = new TransportReservation();
    }

    @Test
    public void whenReserveDeparture_thenShuttleFillerIsCalled() {
        transportReservation.reserveDeparture(SOME_SHUTTLE_CATEGORY, SOME_DATE, SOME_PASS_NUMBER, someShuttles);

        // TODO
    }

    @Test
    public void whenReserveArrival_thenShuttleFillerIsCalled() {
        transportReservation.reserveArrival(SOME_SHUTTLE_CATEGORY, SOME_DATE, SOME_PASS_NUMBER, someShuttles);

        // TODO
}
}
