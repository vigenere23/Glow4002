package ca.ulaval.glo4002.booking.infrastructure.persistance.heap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.orders.PassOrder;
import ca.ulaval.glo4002.booking.domain.passes.Pass;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import java.util.Arrays;
import java.util.List;

public class HeapPassOrderRepositoryTest {

    private static final long INVALID_ID = -1L;

    private HeapPassOrderRepository passOrderRepository;
    private PassOrder passOrder;
    private PassOrder otherPassOrder;

    @BeforeEach
    public void setUp() {
        List<Pass> passes1 = Arrays.asList(mock(Pass.class));
        List<Pass> passes2 = Arrays.asList(mock(Pass.class));

        passOrderRepository = new HeapPassOrderRepository();
        passOrder = new PassOrder(passes1);
        otherPassOrder = new PassOrder(passes2);
    }

    @Test
    public void whenGetWithNonExistantId_itReturnsAnEmptyOptional() {
        assertThat(passOrderRepository.findById(INVALID_ID)).isNotPresent();
    }

    @Test
    public void givenSavingAOrder_whenGetTheOrderById_itReturnsTheSameOrder() throws Exception {
        passOrder.setId(null);
        passOrderRepository.save(passOrder);
        PassOrder savedPassOrder = passOrderRepository.findById(passOrder.getOrderNumber().getId()).get();
        assertThat(savedPassOrder).isEqualTo(passOrder);
    }

    @Test
    public void whenSavingOrderWithIdNull_itSetsAnId() throws Exception {
        passOrder.setId(null);
        passOrderRepository.save(passOrder);
        assertThat(passOrder.getOrderNumber()).isNotNull();
    }

    @Test
    public void whenSavingTwoOrders_itIncrementsTheIdByOne() throws Exception {
        passOrderRepository.save(passOrder);
        Long firstPassOrderId = passOrder.getOrderNumber().getId();

        passOrderRepository.save(otherPassOrder);
        Long secondPassOrderId = otherPassOrder.getOrderNumber().getId();

        assertThat(secondPassOrderId - firstPassOrderId).isEqualTo(1L);
    }
}
