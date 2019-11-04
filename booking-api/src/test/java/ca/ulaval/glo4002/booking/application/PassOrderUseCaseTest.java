package ca.ulaval.glo4002.booking.application;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.LinkedList;
import java.util.List;

import ca.ulaval.glo4002.booking.domain.orders.*;
import ca.ulaval.glo4002.booking.domain.transport.*;
import ca.ulaval.glo4002.booking.infrastructure.persistance.heap.HeapPassOrderRepository;
import ca.ulaval.glo4002.booking.infrastructure.persistance.heap.HeapShuttleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.api.dtos.orders.PassRequest;

public class PassOrderUseCaseTest {

    private static final VendorCode SOME_VENDOR_CODE = VendorCode.TEAM;
    private static final LocalDate SOME_DATE = LocalDate.of(2050, 1, 1);
    private static final OffsetDateTime SOME_ORDER_DATE = OffsetDateTime.of(SOME_DATE, LocalTime.MIDNIGHT, ZoneOffset.UTC);
    private List<Shuttle> shuttlesEarth = new LinkedList<>();
    private List<Shuttle> shuttlesUlavalogy = new LinkedList<>();
    private PassUtilities passUtilities;
    private PassOrder somePassOrder;
    private PassRequest somePassRequest;
    private PassOrderRepository passOrderRepository;
    private ShuttleRepository shuttleRepository;
    private PassOrderUseCase passOrderUseCase;

    @BeforeEach
    public void setUp() {
        mockShuttles();
        mockShuttleRepository();
        passUtilities = mock(PassUtilities.class);
        passOrderRepository = mock(HeapPassOrderRepository.class);

        somePassOrder = mock(PassOrder.class);
        somePassRequest = mock(PassRequest.class);

        passOrderUseCase = new PassOrderUseCase(passUtilities, passOrderRepository, shuttleRepository);
    }

    @Test
    public void whenOrchestPassCreation_thenGetDepartureShuttleFromRepository() {
        passOrderUseCase.orchestPassCreation(SOME_ORDER_DATE, SOME_VENDOR_CODE, somePassRequest);

        verify(shuttleRepository).findShuttlesByLocation(Location.EARTH);
    }

    @Test
    public void whenOrchestPassCreation_thenSaveDepartureShuttleInRepository() {
        passOrderUseCase.orchestPassCreation(SOME_ORDER_DATE, SOME_VENDOR_CODE, somePassRequest);

        verify(shuttleRepository).saveDeparture(shuttlesEarth);
    }

    @Test
    public void whenOrchestPassCreation_thenGetArrivalShuttleFromRepository() {
        passOrderUseCase.orchestPassCreation(SOME_ORDER_DATE, SOME_VENDOR_CODE, somePassRequest);

        verify(shuttleRepository).findShuttlesByLocation(Location.ULAVALOGY);
    }

    @Test
    public void whenOrchestPassCreation_thenSaveArrivalShuttleInRepository() {
        passOrderUseCase.orchestPassCreation(SOME_ORDER_DATE, SOME_VENDOR_CODE, somePassRequest);

        verify(shuttleRepository).saveArrival(shuttlesUlavalogy);
    }

    @Test
    public void whenOrchestPassCreation_thenPassesAreOrdered() {
        passOrderUseCase.orchestPassCreation(SOME_ORDER_DATE, SOME_VENDOR_CODE, somePassRequest);

        verify(passUtilities).orderPasses(SOME_ORDER_DATE, SOME_VENDOR_CODE, somePassRequest, shuttlesEarth, shuttlesUlavalogy);
    }

    @Test
    public void whenOrchestPassCreation_thenSavePassOrderInRepository() {
        when(passUtilities.orderPasses(SOME_ORDER_DATE, SOME_VENDOR_CODE, somePassRequest, shuttlesEarth, shuttlesUlavalogy)).thenReturn(somePassOrder);

        passOrderUseCase.orchestPassCreation(SOME_ORDER_DATE, SOME_VENDOR_CODE, somePassRequest);

        verify(passOrderRepository).save(somePassOrder);
    }

    private void mockShuttles() {
        Shuttle mockedShuttle = mock(SpaceX.class);

        shuttlesEarth.add(mockedShuttle);
        shuttlesUlavalogy.add(mockedShuttle);
    }

    private void mockShuttleRepository() {
        shuttleRepository = mock(HeapShuttleRepository.class);

        when(shuttleRepository.findShuttlesByLocation(Location.EARTH)).thenReturn(shuttlesEarth);
        when(shuttleRepository.findShuttlesByLocation(Location.ULAVALOGY)).thenReturn(shuttlesUlavalogy);
    }
}
