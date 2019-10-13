package ca.ulaval.glo4002.booking.infrastructure.persistance.heap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.orders.OrderNumber;
import ca.ulaval.glo4002.booking.domain.orders.PassOrder;
import ca.ulaval.glo4002.booking.domain.orders.VendorCode;
import ca.ulaval.glo4002.booking.domain.passes.Pass;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import java.util.Arrays;
import java.util.List;

public class HeapPassOrderRepositoryTest {

    private static final OrderNumber INVALID_ORDER_NUMBER = mock(OrderNumber.class);

    private HeapPassOrderRepository passOrderRepository;
    private PassOrder passOrder;

    @BeforeEach
    public void setUp() {
        List<Pass> passes = Arrays.asList(mock(Pass.class));

        passOrderRepository = new HeapPassOrderRepository();
        passOrder = new PassOrder(VendorCode.TEAM, passes);
    }

    @Test
    public void whenGetWithNonExistantOrderNumber_itReturnsAnEmptyOptional() {
        assertThat(passOrderRepository.getByOrderNumber(INVALID_ORDER_NUMBER)).isNotPresent();
    }

    @Test
    public void givenSavingAOrder_whenGetTheOrderByOrderNumber_itReturnsTheSameOrder() throws Exception {
        passOrderRepository.save(passOrder);
        PassOrder savedPassOrder = passOrderRepository.getByOrderNumber(passOrder.getOrderNumber()).get();
        assertThat(savedPassOrder).isEqualTo(passOrder);
    }
}
