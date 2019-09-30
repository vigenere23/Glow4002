package ca.ulaval.glo4002.booking.persistance.inMemory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.passOrdering.orders.OrderFactory;
import ca.ulaval.glo4002.booking.domain.passOrdering.orders.PassOrder;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.PassCategory;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.PassOption;
import ca.ulaval.glo4002.booking.domain.persistanceInterface.PassOrderPersistance;
import ca.ulaval.glo4002.booking.domain.persistanceInterface.Repository;
import ca.ulaval.glo4002.booking.persistance.heap.HeapRepository;
import ca.ulaval.glo4002.booking.persistance.heap.exceptions.RecordAlreadyExistsException;
import ca.ulaval.glo4002.booking.persistance.heap.exceptions.RecordNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.time.OffsetDateTime;

public class HeapOrderPersistanceTest {

    private PassOrderPersistance passOrderPersistance;
    private PassOrder passOrder;
    private PassOrder otherPassOrder;

    @BeforeEach
    public void setUp() {
        Repository repository = new HeapRepository();
        this.passOrderPersistance = repository.getPassOrderPersistance();
        this.passOrder = new OrderFactory().createOrder(OffsetDateTime.now(), PassOption.PACKAGE, PassCategory.NEBULA, null);
        this.otherPassOrder = new OrderFactory().createOrder(OffsetDateTime.now(), PassOption.PACKAGE, PassCategory.SUPERGIANT, null);
    }

    @Test
    public void whenGetWithNonExistantId_itThrowsARecordNotFoundException() {
        assertThatExceptionOfType(RecordNotFoundException.class).isThrownBy(() -> {
            this.passOrderPersistance.getById(-1L);
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
    public void whenSavingTwoOrders_itIncrementsTheIdBy1() throws Exception {
        this.passOrderPersistance.save(this.passOrder);
        Long firstPassOrderId = this.passOrder.getId();

        this.passOrderPersistance.save(this.otherPassOrder);
        Long secondPassOrderId = this.otherPassOrder.getId();

        assertThat(secondPassOrderId - firstPassOrderId).isEqualTo((long) 1);
    }

    @Test
    public void whenSavingAnAlreadyExistingOrder_itThrowsRecordAlreadyExistsException() throws Exception {
        this.passOrderPersistance.save(this.passOrder);
        assertThatExceptionOfType(RecordAlreadyExistsException.class).isThrownBy(() -> {
            this.passOrderPersistance.save(this.passOrder);
        });
    }
}
