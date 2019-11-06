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

import ca.ulaval.glo4002.booking.domain.orders.*;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenRequester;
import ca.ulaval.glo4002.booking.domain.passes.Pass;
import ca.ulaval.glo4002.booking.domain.passes.PassCategory;
import ca.ulaval.glo4002.booking.domain.passes.PassOption;
import ca.ulaval.glo4002.booking.domain.transport.*;
import ca.ulaval.glo4002.booking.infrastructure.persistance.heap.HeapPassOrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.api.dtos.orders.PassRequest;

public class PassOrderUseCaseTest {

    private static final VendorCode VENDOR_CODE = VendorCode.TEAM;
    private static final LocalDate DATE = LocalDate.of(2050, 1, 1);
    private static final OffsetDateTime ORDER_DATE = OffsetDateTime.of(DATE, LocalTime.MIDNIGHT, ZoneOffset.UTC);
    private static final PassOption PASS_OPTION = PassOption.SINGLE_PASS;
    private static final PassCategory PASS_CATEGORY = PassCategory.NEBULA;

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

        mockPassOrder();
        somePassRequest = mock(PassRequest.class);
        when(somePassRequest.getPassOption()).thenReturn(PASS_OPTION);
        when(somePassRequest.getPassCategory()).thenReturn(PASS_CATEGORY);
        when(passOrderFactory.create(
            any(OffsetDateTime.class),
            any(VendorCode.class),
            any(PassOption.class),
            any(PassCategory.class),
            any(List.class)
        )).thenReturn(somePassOrder);

        passOrderUseCase = new PassOrderUseCase(passOrderFactory, passOrderRepository, transportReservation, shuttleRepository, oxygenRequester);
    }

    @Test
    public void whenOrchestPassCreation_thenPassesAreOrdered() {
        passOrderUseCase.orchestPassCreation(ORDER_DATE, VENDOR_CODE, somePassRequest);

        verify(passOrderFactory).create(ORDER_DATE, VENDOR_CODE, PASS_OPTION, PASS_CATEGORY, new ArrayList<>());
    }

    @Test
    public void whenOrchestPassCreation_thenSavePassOrderInRepository() {
        passOrderUseCase.orchestPassCreation(ORDER_DATE, VENDOR_CODE, somePassRequest);

        verify(passOrderRepository).save(somePassOrder);
    }

    @Test
    public void whenOrchestPassCreation_thenShuttlesAreReserved() {
        passOrderUseCase.orchestPassCreation(ORDER_DATE, VENDOR_CODE, somePassRequest);

        verify(pass).reserveShuttles(transportReservation, shuttleRepository);
    }

    @Test
    public void whenOrchestPassCreation_thenOxygenIsOrdered() {
        passOrderUseCase.orchestPassCreation(ORDER_DATE, VENDOR_CODE, somePassRequest);

        verify(pass).orderOxygen(DATE, oxygenRequester);
    }

    private void mockPassOrder() {
        somePassOrder = mock(PassOrder.class);

        List<Pass> passes = new ArrayList<>();
        passes.add(pass);

        when(somePassOrder.getPasses()).thenReturn(passes);
    }
}
