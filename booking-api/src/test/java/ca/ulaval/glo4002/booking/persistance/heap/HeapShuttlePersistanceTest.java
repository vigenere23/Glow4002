package ca.ulaval.glo4002.booking.persistance.heap;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.transport.Location;
import ca.ulaval.glo4002.booking.domain.transport.Shuttle;
import ca.ulaval.glo4002.booking.domain.transport.SpaceX;

class HeapShuttlePersistanceTest {
    
    private HeapShuttlePersistance repository;
    private List<Shuttle> shuttles;
    private Shuttle firstMockedShuttle;
    private Shuttle secondMockedShuttle;
    
    @BeforeEach
    public void createNewInMemoryTransportRepository() {
        repository = new HeapShuttlePersistance();
        shuttles = new LinkedList<Shuttle>();
        shuttles.add(firstMockedShuttle);
        shuttles.add(secondMockedShuttle);
    }
    
    @Test
    public void givenShuttleList_whenSaveArrival_thenReplacesShuttleList() {
        repository.saveArrival(shuttles);
        assertEquals(shuttles, repository.getShuttles(Location.ULAVALOGY));
    }
    
    @Test
    public void givenShuttleList_whenSaveDeparture_thenReplacesShuttleList() {
        repository.saveDeparture(shuttles);
        assertEquals(shuttles, repository.getShuttles(Location.EARTH));
    }
}
