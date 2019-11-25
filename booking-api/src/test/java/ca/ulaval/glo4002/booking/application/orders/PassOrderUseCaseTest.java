package ca.ulaval.glo4002.booking.application.orders;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.ulaval.glo4002.booking.api.resources.orders.requests.PassRequest;
import ca.ulaval.glo4002.booking.application.orders.dtos.PassOrderDtoMapper;
import ca.ulaval.glo4002.booking.domain.orders.PassOrder;
import ca.ulaval.glo4002.booking.domain.orders.PassOrderFactory;
import ca.ulaval.glo4002.booking.domain.orders.PassOrderRepository;
import ca.ulaval.glo4002.booking.domain.orders.VendorCode;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenRequester;
import ca.ulaval.glo4002.booking.domain.passes.Pass;
import ca.ulaval.glo4002.booking.domain.passes.PassCategory;
import ca.ulaval.glo4002.booking.domain.passes.PassOption;
import ca.ulaval.glo4002.booking.domain.passes.PassRepository;
import ca.ulaval.glo4002.booking.domain.profit.IncomeSaver;
import ca.ulaval.glo4002.booking.domain.transport.TransportReserver;

@ExtendWith(MockitoExtension.class)
public class PassOrderUseCaseTest {

    final static VendorCode SOME_VENDOR_CODE = VendorCode.TEAM;
    final static LocalDate SOME_DATE = LocalDate.of(2050, 1, 1);
    final static OffsetDateTime SOME_ORDER_DATE = OffsetDateTime.of(SOME_DATE, LocalTime.MIDNIGHT, ZoneOffset.UTC);
    final static PassOption SOME_PASS_OPTION = PassOption.SINGLE_PASS;
    final static PassCategory SOME_PASS_CATEGORY = PassCategory.NEBULA;

    @Mock PassOrderFactory passOrderFactory;
    @Mock TransportReserver transportReserver;
    @Mock OxygenRequester oxygenRequester;
    @Mock Pass pass;
    @Mock PassOrder passOrder;
    @Mock PassRequest passRequest;
    @Mock PassOrderRepository passOrderRepository;
    @Mock PassRepository passRepository;
    @Mock IncomeSaver incomeSaver;
    @Mock PassOrderDtoMapper passOrderDtoMapper;
    @InjectMocks PassOrderUseCase passOrderUseCase;
    
    @BeforeEach
    public void setUpPassOrderUseCase() {
        mockPassOrder();
        mockPassRequest();
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
        List<Pass> passes = new ArrayList<>();
        passes.add(pass);

        when(passOrder.getPasses()).thenReturn(passes);
    }

    private void mockPassRequest() {
        when(passRequest.getPassOption()).thenReturn(SOME_PASS_OPTION);
        when(passRequest.getPassCategory()).thenReturn(SOME_PASS_CATEGORY);
        when(passRequest.getEventDates()).thenReturn(Optional.empty());
        when(passOrderFactory.create(
            any(OffsetDateTime.class), any(VendorCode.class), any(PassOption.class), any(PassCategory.class), any())
            ).thenReturn(passOrder);
    }
}
