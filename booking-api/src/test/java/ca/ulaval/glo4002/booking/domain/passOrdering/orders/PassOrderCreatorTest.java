package ca.ulaval.glo4002.booking.domain.passOrdering.orders;

import static org.mockito.Mockito.*;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.passOrdering.passes.Pass;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.PassCategory;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.PassOption;
import ca.ulaval.glo4002.booking.domain.persistanceInterface.PassOrderPersistance;
import ca.ulaval.glo4002.booking.domain.persistanceInterface.PassPersistance;
import ca.ulaval.glo4002.booking.domain.persistanceInterface.Repository;
import ca.ulaval.glo4002.booking.interfaces.dtos.PassDTO;
import ca.ulaval.glo4002.booking.persistance.heap.HeapPassOrderPersistance;
import ca.ulaval.glo4002.booking.persistance.heap.HeapPassPersistance;
import ca.ulaval.glo4002.booking.persistance.heap.exceptions.RecordAlreadyExistsException;

public class PassOrderCreatorTest {

    private static final int NUMBER_OF_PASSES = 5;

    private PassOrderCreator passOrderCreator;
    private PassOrderPersistance passOrderPersistance;
    private PassPersistance passPersistance;
    private List<PassDTO> passDTOs;

    @BeforeEach
    public void setUp() throws RecordAlreadyExistsException {
        this.passOrderPersistance = mock(HeapPassOrderPersistance.class);
        doNothing().when(this.passOrderPersistance).save(any(PassOrder.class));
        
        this.passPersistance = mock(HeapPassPersistance.class);
        doNothing().when(this.passPersistance).save(any(Pass.class));

        Repository repository = mock(Repository.class);
        when(repository.getPassOrderPersistance()).thenReturn(this.passOrderPersistance);
        when(repository.getPassPersistance()).thenReturn(this.passPersistance);
        
        this.passOrderCreator = new PassOrderCreator(repository, OffsetDateTime.now(), OffsetDateTime.now());

        initPasses();
    }

    private void initPasses() {
        this.passDTOs = new ArrayList<>();

        for (int i = 0; i < NUMBER_OF_PASSES; i++) {
            this.passDTOs.add(new PassDTO(PassOption.PACKAGE, PassCategory.NEBULA, null));
        }
    }

    @Test
    public void whenCreatingAnOrder_itSavesTheOrderInTheRepository() throws Exception {
        PassOrder passOrder = passOrderCreator.orderPasses(OffsetDateTime.now(), "CODE", this.passDTOs);
        verify(this.passOrderPersistance).save(passOrder);
    }

    @Test
    public void whenCreatingAnOrder_itSavesEveryPassesInTheRepository() throws RecordAlreadyExistsException {
        passOrderCreator.orderPasses(OffsetDateTime.now(), "CODE", this.passDTOs);
        verify(this.passPersistance, times(NUMBER_OF_PASSES)).save(any(Pass.class));
    }
}
