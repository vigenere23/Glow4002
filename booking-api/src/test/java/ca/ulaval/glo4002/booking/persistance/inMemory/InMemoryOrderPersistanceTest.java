package ca.ulaval.glo4002.booking.persistance.inMemory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.passOrdering.orders.Order;
import ca.ulaval.glo4002.booking.domain.persistanceInterface.OrderPersistance;
import ca.ulaval.glo4002.booking.domain.persistanceInterface.Repository;

import static org.assertj.core.api.Assertions.assertThat;

public class InMemoryOrderPersistanceTest {

    private OrderPersistance orderPersistance;
    private Order order;

    @BeforeEach
    public void setUp() {
        Repository repository = new InMemoryRepository();
        this.orderPersistance = repository.getOrderPersistance();
        this.order = new Order();
    }

    @Test
    public void whenGetWithNonExistantId_itReturnsNull() {
        assertThat(this.orderPersistance.getById((long) -1)).isNull();
    }

    @Test
    public void whenSavingPassWithIdNull_itSetsAnId() {
        this.order.setId(null);
        this.orderPersistance.save(this.order);
        assertThat(this.order.getId()).isNotNull();
    }

    @Test
    public void whenSavingPassWithNotNullId_itSetsANewId() {
        Long passId = (long) 1;
        this.order.setId((long) passId);
        this.orderPersistance.save(this.order);
        assertThat(this.order.getId()).isNotEqualTo(passId);
    }

    @Test
    public void givenSavingAPass_whenGetThePassById_itReturnsTheSamePass() {
        this.order.setId(null);
        this.orderPersistance.save(this.order);
        Order savedOrder = this.orderPersistance.getById(this.order.getId());
        assertThat(savedOrder).isEqualTo(this.order);
    }

    @Test
    public void whenSavingTwoPasses_itIncrementsTheIdBy1() {
        this.orderPersistance.save(this.order);
        Long firstPassId = this.order.getId();

        this.orderPersistance.save(this.order);
        Long secondPassId = this.order.getId();

        assertThat(secondPassId - firstPassId).isEqualTo((long) 1);
    }
}
