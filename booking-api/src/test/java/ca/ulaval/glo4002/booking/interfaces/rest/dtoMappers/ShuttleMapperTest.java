package ca.ulaval.glo4002.booking.interfaces.rest.dtoMappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.application.transport.dtos.ShuttleDto;
import ca.ulaval.glo4002.booking.application.transport.dtos.ShuttleDtoMapper;
import ca.ulaval.glo4002.booking.domain.passes.pass_number.PassNumber;
import ca.ulaval.glo4002.booking.domain.transport.PassengerNumber;
import ca.ulaval.glo4002.booking.domain.transport.Shuttle;
import ca.ulaval.glo4002.booking.domain.transport.ShuttleCategory;
import ca.ulaval.glo4002.booking.domain.transport.SpaceX;

public class ShuttleMapperTest {

    private final static LocalDate SOME_DATE = LocalDate.of(2050, 07, 17);
    private final static PassengerNumber SOME_PASSENGER_NUMBER = new PassengerNumber(new PassNumber(1));
    private final static ShuttleCategory SOME_SHUTTLE_CATEGORY = ShuttleCategory.SPACE_X;
    private List<PassengerNumber> passengers = new ArrayList<>();
    private List<Shuttle> shuttles = new ArrayList<>();
    private SpaceX shuttle;
    private ShuttleDtoMapper shuttleMapper = new ShuttleDtoMapper();

    @BeforeEach
    public void setUpShuttleMapper() {
        mockSpaceXShuttle();
        shuttles.add(shuttle);
        passengers.add(SOME_PASSENGER_NUMBER);
    }

    @Test
    public void whenMappingShuttleToDto_thenReturnEquivalentShuttlesDto() {
        ShuttleDto shuttleDto = shuttleMapper.toDtos(shuttles).get(0);
        
        assertEquals(SOME_DATE.toString(), shuttleDto.date);
        assertEquals(SOME_SHUTTLE_CATEGORY.toString(), shuttleDto.shuttleName);
        assertEquals(shuttles.get(0).getPassengerNumbers().size(), shuttleDto.passengers.size());
    }

    private void mockSpaceXShuttle() {
        shuttle = mock(SpaceX.class);

        when(shuttle.getCategory()).thenReturn(SOME_SHUTTLE_CATEGORY);
        when(shuttle.getDate()).thenReturn(SOME_DATE);
        when(shuttle.getPassengerNumbers()).thenReturn(passengers);
    }
}
