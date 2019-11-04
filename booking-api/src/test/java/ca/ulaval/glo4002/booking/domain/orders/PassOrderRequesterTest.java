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
import ca.ulaval.glo4002.booking.domain.festivals.FestivalDates;
import ca.ulaval.glo4002.booking.domain.orders.PassOrder;
import ca.ulaval.glo4002.booking.infrastructure.persistance.heap.HeapPassOrderRepository;

public class PassOrderRequesterTest {

    private PassOrderRequester passOrderService;
    private PassOrderRepository passOrderRepository;
    private PassRequest passRequest;

    @BeforeEach
    public void setUp() throws Exception {
        passOrderRepository = mock(HeapPassOrderRepository.class);

        FestivalDates festivalDates = mock(FestivalDates.class);
        when(festivalDates.isDuringSaleTime(any(OffsetDateTime.class))).thenReturn(true);
        when(festivalDates.isDuringEventTime(any(LocalDate.class))).thenReturn(true);
        when(festivalDates.getStartDate()).thenReturn(LocalDate.now());
        when(festivalDates.getEndDate()).thenReturn(LocalDate.now());
        
        passOrderService = new PassOrderRequester(passOrderRepository, festivalDates);
        passRequest = new PassRequest("package", "nebula", null);
    }

    @Test
    public void whenCreatingAnOrder_itSavesTheOrderInTheRepository() throws Exception {
        PassOrder passOrder = passOrderService.orderPasses(OffsetDateTime.now(), VendorCode.TEAM, passRequest);
        verify(passOrderRepository).save(passOrder);
    }
}
