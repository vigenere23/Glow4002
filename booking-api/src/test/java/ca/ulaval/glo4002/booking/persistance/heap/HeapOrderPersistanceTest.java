package ca.ulaval.glo4002.booking.persistance.heap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.passOrdering.orders.PassOrder;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.Pass;
import ca.ulaval.glo4002.booking.domain.persistanceInterface.PassOrderPersistance;
import ca.ulaval.glo4002.booking.domain.persistanceInterface.Repository;
import ca.ulaval.glo4002.booking.persistance.heap.exceptions.RecordNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.mock;

import java.time.OffsetDateTime;
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

        this.passOrderPersistance = repository.getPassOrderPersistance();
        this.passOrder = new PassOrder(OffsetDateTime.now(), "CODE", passes1);
        this.otherPassOrder = new PassOrder(OffsetDateTime.now(), "CODE2", passes2);
    }

    @Test
    public void whenGetWithNonExistantId_itThrowsARecordNotFoundException() {
        assertThatExceptionOfType(RecordNotFoundException.class).isThrownBy(() -> {
            this.passOrderPersistance.getById(INVALID_ID);
        });
    }

    @Test
    public void givenSavingAOrder_whenGetTheOrderById_itReturnsTheSameOrder() throws Exception {
        this.passOrder.setId(null);
        this.passOrderPersistance.save(this.passOrder);
        PassOrder savedPassOrder = this.passOrderPersistance.getById(this.passOrder.getId());
        assertThat(savedPassOrder).isEqualTo(this.passOrder);
    }

    @Test
    public void whenSavingOrderWithIdNull_itSetsAnId() throws Exception {
        this.passOrder.setId(null);
        this.passOrderPersistance.save(this.passOrder);
        assertThat(this.passOrder.getId()).isNotNull();
    }

    @Test
    public void whenSavingTwoOrders_itIncrementsTheIdByOne() throws Exception {
        this.passOrderPersistance.save(this.passOrder);
        Long firstPassOrderId = this.passOrder.getId();

        this.passOrderPersistance.save(this.otherPassOrder);
        Long secondPassOrderId = this.otherPassOrder.getId();

        assertThat(secondPassOrderId - firstPassOrderId).isEqualTo(1L);
    }
}
