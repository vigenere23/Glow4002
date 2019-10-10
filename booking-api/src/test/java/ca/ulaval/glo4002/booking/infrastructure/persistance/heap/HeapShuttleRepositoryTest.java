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
    
    private HeapShuttleRepository shuttleRepository;
    private List<Shuttle> shuttles;
    
    @BeforeEach
    public void setUp() {
        shuttleRepository = new HeapShuttleRepository();
        shuttles = new LinkedList<Shuttle>();
    }
    
    @Test
    public void givenShuttleList_whenSaveArrival_thenReplacesShuttleList() {
        shuttleRepository.saveArrival(shuttles);
        assertEquals(shuttles, shuttleRepository.getShuttles(Location.ULAVALOGY));
    }

    @Test
    public void givenShuttleListWithNull_whenSaveArrival_thenDontReplacesShuttleList() {
        shuttles.add(null);
        shuttleRepository.saveArrival(shuttles);
        assertEquals(0, shuttleRepository.getShuttles(Location.ULAVALOGY).size());
    }
    
    @Test
    public void givenShuttleList_whenSaveDeparture_thenReplacesShuttleList() {
        shuttleRepository.saveDeparture(shuttles);
        assertEquals(shuttles, shuttleRepository.getShuttles(Location.EARTH));
    }

    @Test
    public void givenShuttleListWithNull_whenSaveDeparture_thenDontReplacesShuttleList() {
        shuttles.add(null);
        shuttleRepository.saveDeparture(shuttles);
        assertEquals(0, shuttleRepository.getShuttles(Location.EARTH).size());
    }

    @Test
    public void givenDate_whenGetShuttlesByDate_thenReturnShuttlesForDate() {
        Shuttle firstShuttle = new SpaceX(LocalDate.of(2050, 7, 19));
        Shuttle secondShuttle = new SpaceX(LocalDate.of(2050, 7, 20));
        shuttles.add(firstShuttle);
        shuttles.add(secondShuttle);
        List<Shuttle> shuttlesByDate = new LinkedList<Shuttle>();
        shuttlesByDate.add(firstShuttle);
        shuttleRepository.saveDeparture(shuttles);

        assertEquals(shuttlesByDate, shuttleRepository.getShuttlesByDate(Location.EARTH , LocalDate.of(2050, 7, 19)));
    }
}
