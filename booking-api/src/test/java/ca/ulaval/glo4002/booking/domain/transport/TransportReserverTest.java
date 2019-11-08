package ca.ulaval.glo4002.booking.domain.transport;

import static org.mockito.Mockito.mock;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;

import ca.ulaval.glo4002.booking.domain.passes.PassNumber;

class transportReserverTest {

    private final static ShuttleCategory SOME_SHUTTLE_CATEGORY = ShuttleCategory.ET_SPACESHIP;
    private final static PassNumber SOME_PASS_NUMBER = mock(PassNumber.class);
    private final static LocalDate SOME_DATE = LocalDate.of(2050, 7, 18);
    private List<Shuttle> someShuttles = new LinkedList<>();
    private TransportReserver transportReserver;

    @BeforeEach
    public void setUp() {
        Shuttle mockedShuttle = mock(SpaceX.class);
        someShuttles.add(mockedShuttle);

        transportReserver = new TransportReserver();
    }

    // TODO (issue #144)
}
