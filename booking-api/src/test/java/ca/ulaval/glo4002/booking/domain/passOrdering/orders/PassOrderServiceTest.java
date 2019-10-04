package ca.ulaval.glo4002.booking.domain.passOrdering.orders;

import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.festivals.Glow4002;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.Pass;
import ca.ulaval.glo4002.booking.domain.persistanceInterface.PassOrderPersistance;
import ca.ulaval.glo4002.booking.domain.persistanceInterface.PassPersistance;
import ca.ulaval.glo4002.booking.domain.persistanceInterface.Repository;
import ca.ulaval.glo4002.booking.interfaces.rest.dtos.orders.PassRequest;
import ca.ulaval.glo4002.booking.persistance.heap.HeapPassOrderPersistance;
import ca.ulaval.glo4002.booking.persistance.heap.HeapPassPersistance;

public class PassOrderServiceTest {

    private static final int NUMBER_OF_PASSES = 5;

    private PassOrderService passOrderService;
    private PassOrderPersistance passOrderPersistance;
    private PassPersistance passPersistance;
    private List<PassRequest> passRequests;

    @BeforeEach
    public void setUp() {
        passOrderPersistance = mock(HeapPassOrderPersistance.class);
        passPersistance = mock(HeapPassPersistance.class);

        Repository repository = mock(Repository.class);
        when(repository.getPassOrderPersistance()).thenReturn(passOrderPersistance);
        when(repository.getPassPersistance()).thenReturn(passPersistance);

        Glow4002 festival = mock(Glow4002.class);
        when(festival.isDuringSaleTime(any(OffsetDateTime.class))).thenReturn(true);
        when(festival.isDuringEventTime(any(LocalDate.class))).thenReturn(true);
        when(festival.getStartDate()).thenReturn(LocalDate.now());
        when(festival.getEndDate()).thenReturn(LocalDate.now());
        
        passOrderService = new PassOrderService(repository, festival);

        initPasses();
    }

    private void initPasses() {
        passRequests = new ArrayList<>();

        for (int i = 0; i < NUMBER_OF_PASSES; i++) {
            passRequests.add(new PassRequest("package", "nebula", null));
        }
    }

    @Test
    public void whenCreatingAnOrder_itSavesTheOrderInTheRepository() throws Exception {
        PassOrder passOrder = passOrderService.orderPasses(OffsetDateTime.now(), "CODE", passRequests);
        verify(passOrderPersistance).save(passOrder);
    }

    @Test
    public void whenCreatingAnOrder_itSavesEveryPassesInTheRepository() throws Exception {
        passOrderService.orderPasses(OffsetDateTime.now(), "CODE", passRequests);
        verify(passPersistance, times(NUMBER_OF_PASSES)).save(any(Pass.class));
    }
}
