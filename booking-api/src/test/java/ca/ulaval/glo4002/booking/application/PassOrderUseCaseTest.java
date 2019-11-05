package ca.ulaval.glo4002.booking.application;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import ca.ulaval.glo4002.booking.domain.orders.*;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenRequester;
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
    private OxygenRequester oxygenRequester;
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
        oxygenRequester = mock(OxygenRequester.class);

        somePassOrder = mock(PassOrder.class);
        somePassRequest = mock(PassRequest.class);

        passOrderUseCase = new PassOrderUseCase(passOrderFactory, passOrderRepository, transportReservation, shuttleRepository, oxygenRequester);
    }

//    @Test
//    public void whenOrchestPassCreation_thenPassesAreOrdered() {
//        passOrderUseCase.orchestPassCreation(SOME_ORDER_DATE, SOME_VENDOR_CODE, somePassRequest);
//
//        verify(passOrderFactory).create(SOME_ORDER_DATE, SOME_VENDOR_CODE, somePassRequest);
//    }

    @Test
    public void whenOrchestPassCreation_thenSavePassOrderInRepository() {
        when(passOrderFactory.create(SOME_ORDER_DATE, SOME_VENDOR_CODE, somePassRequest)).thenReturn(somePassOrder);

        passOrderUseCase.orchestPassCreation(SOME_ORDER_DATE, SOME_VENDOR_CODE, somePassRequest);

        verify(passOrderRepository).save(somePassOrder);
    }

    // TODO Finir les test!
}
