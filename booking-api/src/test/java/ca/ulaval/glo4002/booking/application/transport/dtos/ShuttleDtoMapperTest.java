package ca.ulaval.glo4002.booking.application.transport.dtos;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.transport.Direction;
import ca.ulaval.glo4002.booking.domain.transport.shuttles.ETSpaceship;
import ca.ulaval.glo4002.booking.domain.transport.shuttles.Shuttle;

public class ShuttleDtoMapperTest {

    private ShuttleDtoMapper shuttleDtoMapper;
    private Shuttle shuttle;

    @BeforeEach
    public void setup() {
        shuttleDtoMapper = new ShuttleDtoMapper();
        shuttle = createShuttle();
    }

    @Test
    public void whenMappingToDto_isSetsTheSameDate() {
        ShuttleDto shuttleDto = shuttleDtoMapper.toDto(shuttle);
        assertThat(shuttleDto.date).isEqualTo(shuttle.getDate());
    }

    @Test
    public void whenMappingToDto_isSetsTheSameShuttleName() {
        ShuttleDto shuttleDto = shuttleDtoMapper.toDto(shuttle);
        assertThat(shuttleDto.shuttleCategory).isEqualTo(shuttle.getCategory());
    }

    @Test
    public void whenMappingToDto_isSetsTheSame() {
        ShuttleDto shuttleDto = shuttleDtoMapper.toDto(shuttle);
        assertThat(shuttleDto.passengers).isEqualTo(shuttle.getPassengerNumbers());
    }

    private Shuttle createShuttle() {
        return new ETSpaceship(Direction.ARRIVAL, LocalDate.now());
    }
}
