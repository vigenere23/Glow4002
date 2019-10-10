package ca.ulaval.glo4002.booking.infrastructure.persistance.heap;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.transport.Location;
import ca.ulaval.glo4002.booking.domain.transport.Shuttle;
import ca.ulaval.glo4002.booking.domain.transport.SpaceX;

class HeapShuttleRepositoryTest {
    
    private HeapShuttleRepository repository;
    private List<Shuttle> shuttles;
    
    @BeforeEach
    public void setUp() {
        repository = new HeapShuttleRepository();
        shuttles = new LinkedList<Shuttle>();
    }
    
    @Test
    public void givenShuttleList_whenSaveArrival_thenReplacesShuttleList() {
        repository.saveArrival(shuttles);
        assertEquals(shuttles, repository.getShuttles(Location.ULAVALOGY));
    }

    @Test
    public void givenShuttleListWithNull_whenSaveArrival_thenDontReplacesShuttleList() {
        shuttles.add(null);
        repository.saveArrival(shuttles);
        assertEquals(0, repository.getShuttles(Location.ULAVALOGY).size());
    }
    
    @Test
    public void givenShuttleList_whenSaveDeparture_thenReplacesShuttleList() {
        repository.saveDeparture(shuttles);
        assertEquals(shuttles, repository.getShuttles(Location.EARTH));
    }

    @Test
    public void givenShuttleListWithNull_whenSaveDeparture_thenDontReplacesShuttleList() {
        shuttles.add(null);
        repository.saveDeparture(shuttles);
        assertEquals(0, repository.getShuttles(Location.EARTH).size());
    }

    @Test
    public void givenDate_whenGetShuttlesByDate_thenReturnShuttlesForDate() {
        Shuttle firstShuttle = new SpaceX(LocalDate.of(2050, 7, 19));
        Shuttle secondShuttle = new SpaceX(LocalDate.of(2050, 7, 20));
        shuttles.add(firstShuttle);
        shuttles.add(secondShuttle);
        List<Shuttle> shuttlesByDate = new LinkedList<Shuttle>();
        shuttlesByDate.add(firstShuttle);
        repository.saveDeparture(shuttles);

        assertEquals(shuttlesByDate, repository.getShuttlesByDate(Location.EARTH , LocalDate.of(2050, 7, 19)));
    }
}
