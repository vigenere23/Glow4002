package ca.ulaval.glo4002.booking.application;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import ca.ulaval.glo4002.booking.domain.orders.*;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenHistoryRepository;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenInventoryRepository;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenProducer;
import ca.ulaval.glo4002.booking.domain.passes.Pass;
import ca.ulaval.glo4002.booking.domain.transport.*;
import ca.ulaval.glo4002.booking.infrastructure.persistance.heap.HeapPassOrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.api.dtos.orders.PassRequest;

public class PassOrderUseCaseTest {

    private static final VendorCode SOME_VENDOR_CODE = VendorCode.TEAM;
    private static final LocalDate SOME_DATE = LocalDate.of(2050, 1, 1);
    private static final OffsetDateTime SOME_ORDER_DATE = OffsetDateTime.of(SOME_DATE, LocalTime.MIDNIGHT, ZoneOffset.UTC);

    private PassOrderFactory passOrderFactory;
    private TransportReservation transportReservation;
    private ShuttleRepository shuttleRepository;
    private OxygenProducer oxygenProducer;
    private OxygenInventoryRepository oxygenInventoryRepository;
    private OxygenHistoryRepository oxygenHistoryRepository;
    private Pass pass;
    private PassOrder somePassOrder;
    private PassRequest somePassRequest;
    private PassOrderRepository passOrderRepository;
    private PassOrderUseCase passOrderUseCase;

    @BeforeEach
    public void setUp() {
        pass = mock(Pass.class);
        passOrderFactory = mock(PassOrderFactory.class);
        passOrderRepository = mock(HeapPassOrderRepository.class);
        transportReservation = mock(TransportReservation.class);
        shuttleRepository = mock(ShuttleRepository.class);
        oxygenProducer = mock(OxygenProducer.class);
        oxygenInventoryRepository = mock(OxygenInventoryRepository.class);
        oxygenHistoryRepository = mock(OxygenHistoryRepository.class);

        mockPassOrder();
        somePassRequest = mock(PassRequest.class);
        when(passOrderFactory.create(SOME_ORDER_DATE, SOME_VENDOR_CODE, somePassRequest)).thenReturn(somePassOrder);

        passOrderUseCase = new PassOrderUseCase(passOrderFactory, passOrderRepository, transportReservation, shuttleRepository, oxygenProducer, oxygenInventoryRepository, oxygenHistoryRepository);
    }

    @Test
    public void whenOrchestPassCreation_thenPassesAreOrdered() {
        passOrderUseCase.orchestPassCreation(SOME_ORDER_DATE, SOME_VENDOR_CODE, somePassRequest);

        verify(passOrderFactory).create(SOME_ORDER_DATE, SOME_VENDOR_CODE, somePassRequest);
    }

    @Test
    public void whenOrchestPassCreation_thenSavePassOrderInRepository() {
        passOrderUseCase.orchestPassCreation(SOME_ORDER_DATE, SOME_VENDOR_CODE, somePassRequest);

        verify(passOrderRepository).save(somePassOrder);
    }

    @Test
    public void whenOrchestPassCreation_thenShuttlesAreReserved() {
        passOrderUseCase.orchestPassCreation(SOME_ORDER_DATE, SOME_VENDOR_CODE, somePassRequest);

        verify(pass).reserveShuttles(transportReservation, shuttleRepository);
    }

    @Test
    public void whenOrchestPassCreation_thenOxygenIsOrdered() {
        passOrderUseCase.orchestPassCreation(SOME_ORDER_DATE, SOME_VENDOR_CODE, somePassRequest);

        verify(pass).orderOxygen(SOME_DATE, oxygenProducer, oxygenInventoryRepository, oxygenHistoryRepository);
    }

    private void mockPassOrder() {
        somePassOrder = mock(PassOrder.class);

        List<Pass> passes = new ArrayList<>();
        passes.add(pass);

        when(somePassOrder.getPasses()).thenReturn(passes);
    }
}
