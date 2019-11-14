package ca.ulaval.glo4002.booking.infrastructure.persistance.heap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.orders.orderNumber.OrderNumber;
import ca.ulaval.glo4002.booking.domain.orders.PassOrder;
import ca.ulaval.glo4002.booking.domain.orders.VendorCode;
import ca.ulaval.glo4002.booking.domain.passes.Pass;
import ca.ulaval.glo4002.booking.domain.profit.IncomeSaver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class HeapPassOrderRepositoryTest {

    private static final OrderNumber INVALID_ORDER_NUMBER = mock(OrderNumber.class);
    private static final OrderNumber VALID_ORDER_NUMBER = new OrderNumber(VendorCode.TEAM, 0);

    private HeapPassOrderRepository passOrderRepository;
    private PassOrder passOrder;
    private IncomeSaver incomeSaver;

    @BeforeEach
    public void setUp() {
        List<Pass> passes = Arrays.asList(mock(Pass.class));
        incomeSaver = mock(IncomeSaver.class);

        passOrderRepository = new HeapPassOrderRepository();
        passOrder = new PassOrder(VALID_ORDER_NUMBER, passes, incomeSaver);
    }

    @Test
    public void whenFindWithNonExistantOrderNumber_itReturnsAnEmptyOptional() {
        assertEquals(Optional.empty(), passOrderRepository.findByOrderNumber(INVALID_ORDER_NUMBER));
    }

    @Test
    public void givenSavingAOrder_whenFindTheOrderByOrderNumber_itReturnsTheSameOrder() throws Exception {
        passOrderRepository.save(passOrder);
        OrderNumber orderNumber = OrderNumber.of(passOrder.getOrderNumber().getValue());
        PassOrder savedPassOrder = passOrderRepository.findByOrderNumber(orderNumber).get();
        assertEquals(passOrder, savedPassOrder);
    }
}
