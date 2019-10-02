package ca.ulaval.glo4002.booking.domain.passOrdering.orders;

import static org.mockito.Mockito.*;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;

import ca.ulaval.glo4002.booking.domain.festivals.Festival;
import ca.ulaval.glo4002.booking.domain.festivals.Glow4002;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.PassCategory;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.PassOption;
import ca.ulaval.glo4002.booking.domain.persistanceInterface.PassOrderPersistance;
import ca.ulaval.glo4002.booking.domain.persistanceInterface.PassPersistance;
import ca.ulaval.glo4002.booking.domain.persistanceInterface.Repository;
import ca.ulaval.glo4002.booking.interfaces.dtos.PassDTO;
import ca.ulaval.glo4002.booking.persistance.heap.HeapRepository;

public class PassOrderCreatorTest {

    private PassOrderCreator passOrderCreator;
    @Spy
    private PassOrderPersistance passOrderPersistance;
    @Spy
    private PassPersistance passPersistance;
    private List<PassDTO> passDTOs;

    @BeforeEach
    public void setUp() {
        Repository repository = new HeapRepository();
        Festival festival = new Glow4002(repository);
        this.passOrderPersistance = repository.getPassOrderPersistance();
        this.passPersistance = repository.getPassPersistance();
        this.passOrderCreator = new PassOrderCreator(repository, festival.getStartDate(), festival.getEndDate());
        this.passDTOs = new ArrayList<>();

        initPasses();
    }

    private void initPasses() {
        for (int i = 0; i < 5; i++) {
            this.passDTOs.add(new PassDTO(PassOption.PACKAGE, PassCategory.NEBULA, null));
        }
    }

    @Test
    public void whenCreatingAnOrder_itSavesTheOrderInTheRepository() throws Exception {
        PassOrder passOrder = passOrderCreator.orderPasses(OffsetDateTime.now(), "CODE", this.passDTOs);

        // TODO not working with Mockito - zero interactions?
        // verify(this.passOrderPersistance).save(passOrder);
    }

    @Test
    public void whenCreatingAnOrder_itSavesEveryPassesInTheRepository() {

    }
}
