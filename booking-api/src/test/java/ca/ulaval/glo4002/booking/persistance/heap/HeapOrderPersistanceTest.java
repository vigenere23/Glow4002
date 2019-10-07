package ca.ulaval.glo4002.booking.persistance.heap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.orders.PassOrder;
import ca.ulaval.glo4002.booking.domain.passes.Pass;
import ca.ulaval.glo4002.booking.domain.persistanceInterface.PassOrderPersistance;
import ca.ulaval.glo4002.booking.domain.persistanceInterface.Repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import java.util.Arrays;
import java.util.List;

public class HeapOrderPersistanceTest {

    private static final long INVALID_ID = -1L;

    private PassOrderPersistance passOrderPersistance;
    private PassOrder passOrder;
    private PassOrder otherPassOrder;

    @BeforeEach
    public void setUp() {
        Repository repository = new HeapRepository();
        List<Pass> passes1 = Arrays.asList(mock(Pass.class));
        List<Pass> passes2 = Arrays.asList(mock(Pass.class));

        passOrderPersistance = repository.getPassOrderPersistance();
        passOrder = new PassOrder(passes1);
        otherPassOrder = new PassOrder(passes2);
    }

    @Test
    public void whenGetWithNonExistantId_itReturnsAnEmptyOptional() {
        assertThat(passOrderPersistance.getById(INVALID_ID)).isNotPresent();
    }

    @Test
    public void givenSavingAOrder_whenGetTheOrderById_itReturnsTheSameOrder() throws Exception {
        passOrder.setId(null);
        passOrderPersistance.save(passOrder);
        PassOrder savedPassOrder = passOrderPersistance.getById(passOrder.getOrderNumber().getId()).get();
        assertThat(savedPassOrder).isEqualTo(passOrder);
    }

    @Test
    public void whenSavingOrderWithIdNull_itSetsAnId() throws Exception {
        passOrder.setId(null);
        passOrderPersistance.save(passOrder);
        assertThat(passOrder.getOrderNumber()).isNotNull();
    }

    @Test
    public void whenSavingTwoOrders_itIncrementsTheIdByOne() throws Exception {
        passOrderPersistance.save(passOrder);
        Long firstPassOrderId = passOrder.getOrderNumber().getId();

        passOrderPersistance.save(otherPassOrder);
        Long secondPassOrderId = otherPassOrder.getOrderNumber().getId();

        assertThat(secondPassOrderId - firstPassOrderId).isEqualTo(1L);
    }
}
