package ca.ulaval.glo4002.booking.persistance.inMemory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.passOrdering.orders.Order;
import ca.ulaval.glo4002.booking.domain.persistanceInterface.OrderPersistance;
import ca.ulaval.glo4002.booking.domain.persistanceInterface.Repository;
import ca.ulaval.glo4002.booking.persistance.inMemory.exceptions.RecordAlreadyExistsException;
import ca.ulaval.glo4002.booking.persistance.inMemory.exceptions.RecordNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class InMemoryOrderPersistanceTest {

    private OrderPersistance orderPersistance;
    private Order order;
    private Order otherOrder;

    @BeforeEach
    public void setUp() {
        Repository repository = new InMemoryRepository();
        this.orderPersistance = repository.getOrderPersistance();
        this.order = new Order();
        this.otherOrder = new Order();
    }

    @Test
    public void whenGetWithNonExistantId_itThrowsARecordNotFoundException() {
        assertThatExceptionOfType(RecordNotFoundException.class).isThrownBy(() -> {
            this.orderPersistance.getById(-1L);
        });
    }

    @Test
    public void givenSavingAOrder_whenGetTheOrderById_itReturnsTheSameOrder() throws Exception {
        this.order.setId(null);
        this.orderPersistance.save(this.order);
        Order savedOrder = this.orderPersistance.getById(this.order.getId());
        assertThat(savedOrder).isEqualTo(this.order);
    }

    @Test
    public void whenSavingOrderWithIdNull_itSetsAnId() throws Exception {
        this.order.setId(null);
        this.orderPersistance.save(this.order);
        assertThat(this.order.getId()).isNotNull();
    }

    @Test
    public void whenSavingTwoOrders_itIncrementsTheIdBy1() throws Exception {
        this.orderPersistance.save(this.order);
        Long firstOrderId = this.order.getId();

        this.orderPersistance.save(this.otherOrder);
        Long secondOrderId = this.otherOrder.getId();

        assertThat(secondOrderId - firstOrderId).isEqualTo((long) 1);
    }

    @Test
    public void whenSavingAnAlreadyExistingOrder_itThrowsRecordAlreadyExistsException() throws Exception {
        this.orderPersistance.save(this.order);
        assertThatExceptionOfType(RecordAlreadyExistsException.class).isThrownBy(() -> {
            this.orderPersistance.save(this.order);
        });
    }

    @Test
    public void givenOrderIsSaved_whenRemovingOrderById_itDeletedTheOrder() throws Exception {
        this.orderPersistance.save(this.order);
        this.orderPersistance.remove(this.order.getId());
        assertThatExceptionOfType(RecordNotFoundException.class).isThrownBy(() -> {
            this.orderPersistance.getById(this.order.getId());
        });
    }

    @Test
    public void whenRemovingOrderByNotSavedId_itThrowsARecordNotFoundException() throws Exception {
        assertThatExceptionOfType(RecordNotFoundException.class).isThrownBy(() -> {
            this.orderPersistance.remove(this.order.getId());            
        });
    }
}
