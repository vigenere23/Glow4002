package ca.ulaval.glo4002.booking.infrastructure.persistence.memory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.mock;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.exceptions.ItemNotFound;
import ca.ulaval.glo4002.booking.domain.orders.PassOrder;
import ca.ulaval.glo4002.booking.domain.orders.VendorCode;
import ca.ulaval.glo4002.booking.domain.orders.discounts.NebulaSinglePassDiscount;
import ca.ulaval.glo4002.booking.domain.orders.discounts.OrderDiscount;
import ca.ulaval.glo4002.booking.domain.orders.discounts.OrderDiscountLinker;
import ca.ulaval.glo4002.booking.domain.orders.discounts.SupergiantSinglePassDiscount;
import ca.ulaval.glo4002.booking.domain.orders.order_number.OrderNumber;
import ca.ulaval.glo4002.booking.domain.passes.Pass;

public class InMemoryPassOrderRepositoryTest {

    private final static OrderNumber INVALID_ORDER_NUMBER = mock(OrderNumber.class);
    private final static OrderNumber VALID_ORDER_NUMBER = new OrderNumber(VendorCode.TEAM, 0);

    private InMemoryPassOrderRepository passOrderRepository;
    private PassOrder passOrder;

    @BeforeEach
    public void setUp() {
        List<Pass> passes = Arrays.asList(mock(Pass.class));

        passOrderRepository = new InMemoryPassOrderRepository();
        OrderDiscount orderDiscount = new OrderDiscountLinker().link(
            new SupergiantSinglePassDiscount(), new NebulaSinglePassDiscount()
        );
        passOrder = new PassOrder(VALID_ORDER_NUMBER, passes, Optional.of(orderDiscount));
    }

    @Test
    public void whenFindingWithNonExistantOrderNumber_itThrowsANotFoundException() {
        assertThatExceptionOfType(ItemNotFound.class).isThrownBy(() -> {
            passOrderRepository.findByOrderNumber(INVALID_ORDER_NUMBER);
        });
    }

    @Test
    public void givenASavedOrder_whenFindTheOrderByOrderNumber_itReturnsTheSameOrder() {
        passOrderRepository.add(passOrder);
        PassOrder savedPassOrder = passOrderRepository.findByOrderNumber(passOrder.getOrderNumber());
        assertThat(savedPassOrder).isEqualTo(passOrder);
    }
}
