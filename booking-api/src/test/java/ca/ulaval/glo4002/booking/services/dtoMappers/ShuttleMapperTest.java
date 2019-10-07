package ca.ulaval.glo4002.booking.services.dtoMappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.transport.Shuttle;
import ca.ulaval.glo4002.booking.domain.transport.SpaceX;

public class ShuttleMapperTest {

    private List<Shuttle> shuttles;
    private ShuttleMapper shuttleMapper; 

    @BeforeEach
    public void setUp() {
        shuttleMapper = new ShuttleMapper();
        shuttles = new LinkedList<Shuttle>();
    }

    @Test
    public void givenShuttleList_whenGetShuttlesDto_thenReturnEquivalentShuttlesDto() {
        Shuttle firstShuttle = new SpaceX(LocalDate.of(2050, 07, 17));
        shuttles.add(firstShuttle);
        
        assertEquals(LocalDate.of(2050, 07, 17).toString(), shuttleMapper.getShuttlesDto(shuttles).get(0).date);
        assertEquals("SpaceX", shuttleMapper.getShuttlesDto(shuttles).get(0).shuttleName);
        assertEquals(0, shuttleMapper.getShuttlesDto(shuttles).get(0).passengers.size());
    }
}