package ca.ulaval.glo4002.booking.infrastructure.persistence.memory;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.transport.Location;
import ca.ulaval.glo4002.booking.domain.transport.Shuttle;
import ca.ulaval.glo4002.booking.domain.transport.SpaceX;

class InMemoryShuttleRepositoryTest {
    
    private InMemoryShuttleRepository shuttleRepository;
    private List<Shuttle> shuttles;
    
    @BeforeEach
    public void setUp() {
        shuttleRepository = new InMemoryShuttleRepository();
        shuttles = new ArrayList<>();
    }
    
    @Test
    public void givenShuttleList_whenSaveArrival_thenReplaceShuttleList() {
        shuttleRepository.saveArrival(shuttles);
        assertEquals(shuttles, shuttleRepository.findShuttlesByLocation(Location.ULAVALOGY));
    }
    
    @Test
    public void givenShuttleList_whenSaveDeparture_thenReplaceShuttleList() {
        shuttleRepository.saveDeparture(shuttles);
        assertEquals(shuttles, shuttleRepository.findShuttlesByLocation(Location.EARTH));
    }

    @Test
    public void givenDate_whenGetShuttlesByDate_thenReturnShuttlesForDate() {
        Shuttle firstShuttle = new SpaceX(LocalDate.of(2050, 7, 19));
        Shuttle secondShuttle = new SpaceX(LocalDate.of(2050, 7, 20));
        shuttles.add(firstShuttle);
        shuttles.add(secondShuttle);
        List<Shuttle> shuttlesByDate = new ArrayList<>();
        shuttlesByDate.add(firstShuttle);

        shuttleRepository.saveDeparture(shuttles);

        List<Shuttle> shuttlesForDate = shuttleRepository.findShuttlesByDate(Location.EARTH , LocalDate.of(2050, 7, 19));
        assertEquals(shuttlesByDate, shuttlesForDate);
    }
}