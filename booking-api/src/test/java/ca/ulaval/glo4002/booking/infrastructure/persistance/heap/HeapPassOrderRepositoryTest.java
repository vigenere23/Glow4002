package ca.ulaval.glo4002.booking.infrastructure.persistance.heap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.mock;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.orders.PassOrder;
import ca.ulaval.glo4002.booking.domain.orders.VendorCode;
import ca.ulaval.glo4002.booking.domain.orders.discounts.NebulaSinglePassDiscount;
import ca.ulaval.glo4002.booking.domain.orders.discounts.OrderDiscount;
import ca.ulaval.glo4002.booking.domain.orders.discounts.OrderDiscountLinker;
import ca.ulaval.glo4002.booking.domain.orders.discounts.SupergiantSinglePassDiscount;
import ca.ulaval.glo4002.booking.domain.orders.orderNumber.OrderNumber;
import ca.ulaval.glo4002.booking.domain.passes.Pass;
import ca.ulaval.glo4002.booking.infrastructure.persistance.exceptions.NotFoundException;

public class HeapPassOrderRepositoryTest {

    private final static OrderNumber INVALID_ORDER_NUMBER = mock(OrderNumber.class);
    private final static OrderNumber VALID_ORDER_NUMBER = new OrderNumber(VendorCode.TEAM, 0);

    private HeapPassOrderRepository passOrderRepository;
    private PassOrder passOrder;

    @BeforeEach
    public void setUp() {
        List<Pass> passes = Arrays.asList(mock(Pass.class));

        passOrderRepository = new HeapPassOrderRepository();
        OrderDiscount orderDiscount = new OrderDiscountLinker().link(
            new SupergiantSinglePassDiscount(), new NebulaSinglePassDiscount()
        );
        passOrder = new PassOrder(VALID_ORDER_NUMBER, passes, Optional.of(orderDiscount));
    }

    @Test
    public void whenFindWithNonExistantOrderNumber_itThrowsANotFoundException() {
        assertThatExceptionOfType(NotFoundException.class).isThrownBy(() -> {
            passOrderRepository.findByOrderNumber(INVALID_ORDER_NUMBER);
        });
    }

    @Test
    public void givenSavingAOrder_whenFindTheOrderByOrderNumber_itReturnsTheSameOrder() {
        passOrderRepository.save(passOrder);

        OrderNumber orderNumber = OrderNumber.of(passOrder.getOrderNumber().getValue());
        PassOrder savedPassOrder = passOrderRepository.findByOrderNumber(orderNumber);

        assertThat(savedPassOrder).isEqualTo(passOrder);
    }
}
