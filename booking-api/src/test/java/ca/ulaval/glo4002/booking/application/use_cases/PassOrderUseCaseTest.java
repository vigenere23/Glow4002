package ca.ulaval.glo4002.booking.application.use_cases;

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

import ca.ulaval.glo4002.booking.api.resources.passOrder.PassRequest;
import ca.ulaval.glo4002.booking.domain.orders.*;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenRequester;
import ca.ulaval.glo4002.booking.domain.passes.Pass;
import ca.ulaval.glo4002.booking.domain.passes.PassCategory;
import ca.ulaval.glo4002.booking.domain.passes.PassOption;
import ca.ulaval.glo4002.booking.domain.passes.PassRepository;
import ca.ulaval.glo4002.booking.domain.profit.IncomeSaver;
import ca.ulaval.glo4002.booking.domain.transport.*;
import ca.ulaval.glo4002.booking.infrastructure.persistance.heap.HeapPassOrderRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PassOrderUseCaseTest {

    private final static VendorCode SOME_VENDOR_CODE = VendorCode.TEAM;
    private final static LocalDate SOME_DATE = LocalDate.of(2050, 1, 1);
    private final static OffsetDateTime SOME_ORDER_DATE = OffsetDateTime.of(SOME_DATE, LocalTime.MIDNIGHT, ZoneOffset.UTC);
    private final static PassOption SOME_PASS_OPTION = PassOption.SINGLE_PASS;
    private final static PassCategory SOME_PASS_CATEGORY = PassCategory.NEBULA;

    private PassOrderFactory passOrderFactory;
    private TransportReserver transportReserver;
    private OxygenRequester oxygenRequester;
    private Pass pass;
    private PassOrder passOrder;
    private PassRequest passRequest;
    private PassOrderRepository passOrderRepository;
    private PassRepository passRepository;
    private IncomeSaver incomeSaver;
    private PassOrderUseCase passOrderUseCase;

    @BeforeEach
    public void setUpPassOrderUseCase() {
        pass = mock(Pass.class);
        passOrderFactory = mock(PassOrderFactory.class);
        passOrderRepository = mock(HeapPassOrderRepository.class);
        transportReserver = mock(TransportReserver.class);
        oxygenRequester = mock(OxygenRequester.class);
        passRepository = mock(PassRepository.class);
        incomeSaver = mock(IncomeSaver.class);

        mockPassOrder();
        mockPassRequest();

        passOrderUseCase = new PassOrderUseCase(passOrderFactory, passOrderRepository, transportReserver, oxygenRequester, passRepository, incomeSaver);
    }

    @Test
    public void whenOrchestPassCreation_thenPassesAreOrdered() {
        passOrderUseCase.orchestPassCreation(SOME_ORDER_DATE, SOME_VENDOR_CODE, passRequest);
        verify(passOrderFactory).create(SOME_ORDER_DATE, SOME_VENDOR_CODE, SOME_PASS_OPTION, SOME_PASS_CATEGORY, Optional.empty());
    }

    @Test
    public void whenOrchestPassCreation_thenPassOrderIsSavedInRepository() {
        passOrderUseCase.orchestPassCreation(SOME_ORDER_DATE, SOME_VENDOR_CODE, passRequest);
        verify(passOrderRepository).save(passOrder);
    }

    @Test
    public void whenOrchestPassCreation_thenPassIsSavedInRepository() {
        passOrderUseCase.orchestPassCreation(SOME_ORDER_DATE, SOME_VENDOR_CODE, passRequest);
        verify(passRepository).save(pass);
    }

    @Test
    public void whenOrchestPassCreation_thenShuttlesAreReserved() {
        passOrderUseCase.orchestPassCreation(SOME_ORDER_DATE, SOME_VENDOR_CODE, passRequest);
        verify(pass).reserveShuttles(transportReserver);
    }

    @Test
    public void whenOrchestPassCreation_thenOxygenIsOrdered() {
        passOrderUseCase.orchestPassCreation(SOME_ORDER_DATE, SOME_VENDOR_CODE, passRequest);
        verify(pass).reserveOxygen(SOME_ORDER_DATE, oxygenRequester);
    }

    private void mockPassOrder() {
        passOrder = mock(PassOrder.class);

        List<Pass> passes = new ArrayList<>();
        passes.add(pass);

        when(passOrder.getPasses()).thenReturn(passes);
    }

    private void mockPassRequest() {
        passRequest = mock(PassRequest.class);
        when(passRequest.getPassOption()).thenReturn(SOME_PASS_OPTION);
        when(passRequest.getPassCategory()).thenReturn(SOME_PASS_CATEGORY);
        when(passRequest.getEventDates()).thenReturn(Optional.empty());
        when(passOrderFactory.create(
            any(OffsetDateTime.class), any(VendorCode.class), any(PassOption.class), any(PassCategory.class), any())
            ).thenReturn(passOrder);
    }
}
