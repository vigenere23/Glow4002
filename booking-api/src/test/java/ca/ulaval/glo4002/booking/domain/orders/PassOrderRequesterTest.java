package ca.ulaval.glo4002.booking.domain.orders;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.OffsetDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.api.dtos.orders.PassRequest;
import ca.ulaval.glo4002.booking.domain.festivals.Glow4002;
import ca.ulaval.glo4002.booking.domain.orders.PassOrder;
import ca.ulaval.glo4002.booking.infrastructure.persistance.heap.HeapPassOrderRepository;

public class PassOrderRequesterTest {

    private PassOrderRequester passOrderService;
    private PassOrderRepository passOrderRepository;
    private PassRequest passRequest;

    @BeforeEach
    public void setUp() throws Exception {
        passOrderRepository = mock(HeapPassOrderRepository.class);

        Glow4002 festival = mock(Glow4002.class);
        // TODO #129 inject factory in requester, so that only the factory will need to be mocked
        when(festival.isDuringSaleTime(any(OffsetDateTime.class))).thenReturn(true);
        when(festival.isDuringEventTime(any(LocalDate.class))).thenReturn(true);
        when(festival.getStartDate()).thenReturn(LocalDate.now());
        when(festival.getEndDate()).thenReturn(LocalDate.now());
        
        passOrderService = new PassOrderRequester(passOrderRepository, festival);
        passRequest = new PassRequest("package", "nebula", null);
    }

    @Test
    public void whenCreatingAnOrder_itSavesTheOrderInTheRepository() throws Exception {
        PassOrder passOrder = passOrderService.orderPasses(OffsetDateTime.now(), VendorCode.TEAM, passRequest);
        verify(passOrderRepository).save(passOrder);
    }
}
