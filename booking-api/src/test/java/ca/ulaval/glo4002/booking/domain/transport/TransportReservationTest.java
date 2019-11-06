package ca.ulaval.glo4002.booking.domain.transport;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    private final static int SOME_PLACES = 1;
    private final static List<Shuttle> SOME_SHUTTLES = new LinkedList<>();

    private TransportReservation transportReservation;

    @BeforeEach
    public void setUpTransportReservation() {
        transportReservation = new TransportReservation();
    }

    @Test
    public void givenShuttleReservationInformation_whenReserveShuttle_thenFillShuttle() {
        List<Shuttle> filledShuttles = transportReservation.reserveShuttle(SOME_SHUTTLE_CATEGORY, SOME_DATE, SOME_PASS_NUMBER, SOME_SHUTTLES, SOME_PLACES);
        
        assertEquals(1, filledShuttles.size());
    }
}
