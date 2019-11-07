package ca.ulaval.glo4002.booking.application;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import ca.ulaval.glo4002.booking.domain.orders.*;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenHistoryRepository;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenInventoryRepository;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenProducer;
import ca.ulaval.glo4002.booking.domain.passes.Pass;
import ca.ulaval.glo4002.booking.domain.passes.PassCategory;
import ca.ulaval.glo4002.booking.domain.passes.PassOption;
import ca.ulaval.glo4002.booking.domain.transport.*;
import ca.ulaval.glo4002.booking.infrastructure.persistance.heap.HeapPassOrderRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.api.dtos.orders.PassRequest;

public class PassOrderUseCaseTest {

    private static final VendorCode SOME_VENDOR_CODE = VendorCode.TEAM;
    private static final LocalDate SOME_DATE = LocalDate.of(2050, 1, 1);
    private static final OffsetDateTime SOME_ORDER_DATE = OffsetDateTime.of(SOME_DATE, LocalTime.MIDNIGHT, ZoneOffset.UTC);
    private static final PassOption PASS_OPTION = PassOption.SINGLE_PASS;
    private static final PassCategory PASS_CATEGORY = PassCategory.NEBULA;

    private PassOrderFactory passOrderFactory;
    private TransportReservation transportReservation;
    private ShuttleRepository shuttleRepository;
    private OxygenProducer oxygenProducer;
    private OxygenInventoryRepository oxygenInventoryRepository;
    private OxygenHistoryRepository oxygenHistoryRepository;
    private Pass pass;
    private PassOrder passOrder;
    private PassRequest passRequest;
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
        passRequest = mock(PassRequest.class);
        when(passRequest.getPassOption()).thenReturn(PASS_OPTION);
        when(passRequest.getPassCategory()).thenReturn(PASS_CATEGORY);
        when(passRequest.getEventDates()).thenReturn(Optional.empty());
        when(passOrderFactory.create(
            any(OffsetDateTime.class), any(VendorCode.class), any(PassOption.class), any(PassCategory.class), any())
        ).thenReturn(passOrder);

        passOrderUseCase = new PassOrderUseCase(passOrderFactory, passOrderRepository, transportReservation, shuttleRepository, oxygenProducer, oxygenInventoryRepository, oxygenHistoryRepository);
    }

    @Test
    public void whenOrchestPassCreation_thenPassesAreOrdered() {
        passOrderUseCase.orchestPassCreation(SOME_ORDER_DATE, SOME_VENDOR_CODE, passRequest);

        verify(passOrderFactory).create(SOME_ORDER_DATE, SOME_VENDOR_CODE, PASS_OPTION, PASS_CATEGORY, Optional.empty());
    }

    @Test
    public void whenOrchestPassCreation_thenSavePassOrderInRepository() {
        passOrderUseCase.orchestPassCreation(SOME_ORDER_DATE, SOME_VENDOR_CODE, passRequest);

        verify(passOrderRepository).save(passOrder);
    }

    @Test
    public void whenOrchestPassCreation_thenShuttlesAreReserved() {
        passOrderUseCase.orchestPassCreation(SOME_ORDER_DATE, SOME_VENDOR_CODE, passRequest);

        verify(pass).reserveShuttles(transportReservation, shuttleRepository);
    }

    @Test
    public void whenOrchestPassCreation_thenOxygenIsOrdered() {
        passOrderUseCase.orchestPassCreation(SOME_ORDER_DATE, SOME_VENDOR_CODE, passRequest);

        verify(pass).orderOxygen(SOME_DATE, oxygenProducer, oxygenInventoryRepository, oxygenHistoryRepository);
    }

    private void mockPassOrder() {
        passOrder = mock(PassOrder.class);

        List<Pass> passes = new ArrayList<>();
        passes.add(pass);

        when(passOrder.getPasses()).thenReturn(passes);
    }
}
