package ca.ulaval.glo4002.booking.domain.passOrdering.orders;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import ca.ulaval.glo4002.booking.domain.festivals.Festival;
import ca.ulaval.glo4002.booking.domain.festivals.Glow4002;
import ca.ulaval.glo4002.booking.domain.persistanceInterface.PassOrderPersistance;
import ca.ulaval.glo4002.booking.domain.persistanceInterface.Repository;
import ca.ulaval.glo4002.booking.persistance.heap.HeapRepository;

public class PassOrderCreatorTest {

    private PassOrderCreator orderCreator;
    private PassOrderPersistance passOrderPersistance;

    @BeforeEach
    public void setUp() {
        Repository repository = new HeapRepository();
        Festival festival = new Glow4002(repository);
        this.passOrderPersistance = repository.getPassOrderPersistance();
        this.orderCreator = new PassOrderCreator(repository, festival.getStartDate(), festival.getEndDate());
    }

    @Test
    public void whenCreatingOrder_itCreatesAnOrderWithTheFactory() {

    }

    @Test
    public void whenCreatingAnOrder_itSavesTheOrderInTheRepository() {

    }

    @Test
    public void whenCreatingAnOrder_itSavesEveryPassesInTheRepository() {

    }
}
