package ca.ulaval.glo4002.booking.api.dtoMappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.api.resources.transport.dto.ShuttleMapper;
import ca.ulaval.glo4002.booking.domain.transport.Shuttle;
import ca.ulaval.glo4002.booking.domain.transport.SpaceX;

public class ShuttleMapperTest {

    private List<Shuttle> shuttles;
    private ShuttleMapper shuttleMapper;

    @BeforeEach
    public void setUpMapper() {
        shuttleMapper = new ShuttleMapper();
        shuttles = new ArrayList<>();
    }

    @Test
    public void givenShuttleList_whenGetShuttlesDto_thenReturnEquivalentShuttlesDto() {
        Shuttle firstShuttle = new SpaceX(LocalDate.of(2050, 07, 17));
        shuttles.add(firstShuttle);
        
        assertEquals(LocalDate.of(2050, 07, 17).toString(), shuttleMapper.toDto(shuttles).get(0).date);
        assertEquals("SpaceX", shuttleMapper.toDto(shuttles).get(0).shuttleName);
        assertEquals(0, shuttleMapper.toDto(shuttles).get(0).passengers.size());
    }
}