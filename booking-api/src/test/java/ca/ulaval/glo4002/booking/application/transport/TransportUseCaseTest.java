package ca.ulaval.glo4002.booking.application.transport;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.ulaval.glo4002.booking.application.transport.dtos.ShuttleDto;
import ca.ulaval.glo4002.booking.application.transport.dtos.ShuttleDtoMapper;
import ca.ulaval.glo4002.booking.domain.exceptions.OutOfFestivalDatesException;
import ca.ulaval.glo4002.booking.domain.dates.FestivalDates;
import ca.ulaval.glo4002.booking.domain.transport.Direction;
import ca.ulaval.glo4002.booking.domain.transport.ETSpaceship;
import ca.ulaval.glo4002.booking.domain.transport.Shuttle;
import ca.ulaval.glo4002.booking.domain.transport.ShuttleRepository;

@ExtendWith(MockitoExtension.class)
class TransportUseCaseTest {

    private static final LocalDate A_DATE = LocalDate.now();
    private static final int NUMBER_OF_SHUTTLES = 9;

    @Mock List<ShuttleDto> shuttleDtos;
    @Mock FestivalDates festivalDates;
    @Mock ShuttleRepository shuttleRepository;
    @Mock ShuttleDtoMapper shuttleDtoMapper;
    @InjectMocks TransportUseCase transportUseCase;

    @Test
    public void whenGettingAllArrivals_itAsksTheRepositoryForAllArrivals() {
        transportUseCase.getAllArrivals();
        verify(shuttleRepository).findAllByDirection(Direction.ARRIVAL);
    }

    @Test
    public void whenGettingAllArrivals_itReturnsAllTheDepartures() {
        List<Shuttle> shuttles = addShuttles(NUMBER_OF_SHUTTLES, Direction.ARRIVAL);
        when(shuttleRepository.findAllByDirection(Direction.ARRIVAL)).thenReturn(shuttles);
        when(shuttleDtoMapper.toDtos(shuttles)).thenReturn(shuttleDtos);

        assertThat(transportUseCase.getAllArrivals()).isEqualTo(shuttleDtos);
    }

    @Test
    public void whenGettingAllDepartures_itAsksTheRepositoryForAllDepartures() {
        transportUseCase.getAllDepartures();
        verify(shuttleRepository).findAllByDirection(Direction.DEPARTURE);
    }

    @Test
    public void whenGettingAllDepartures_itReturnsAllTheDepartures() {
        List<Shuttle> shuttles = addShuttles(NUMBER_OF_SHUTTLES, Direction.DEPARTURE);
        when(shuttleRepository.findAllByDirection(Direction.DEPARTURE)).thenReturn(shuttles);
        when(shuttleDtoMapper.toDtos(shuttles)).thenReturn(shuttleDtos);

        assertThat(transportUseCase.getAllDepartures()).isEqualTo(shuttleDtos);
    }

    @Test
    public void whenGettingAllArrivalsByDate_itAsksTheRepositoryForAllArrivalsForTheDate() {
        transportUseCase.getAllArrivalsByDate(A_DATE);
        verify(shuttleRepository).findAllByDirectionAndDate(Direction.ARRIVAL, A_DATE);
    }

    @Test
    public void whenGettingAllArrivalsByDate_itReturnsAllTheArrivalsForTheDate() {
        List<Shuttle> shuttles = addShuttles(NUMBER_OF_SHUTTLES, Direction.ARRIVAL, A_DATE);
        when(shuttleRepository.findAllByDirectionAndDate(Direction.ARRIVAL, A_DATE)).thenReturn(shuttles);
        when(shuttleDtoMapper.toDtos(shuttles)).thenReturn(shuttleDtos);

        assertThat(transportUseCase.getAllArrivalsByDate(A_DATE)).isEqualTo(shuttleDtos);
    }

    @Test
    public void givenOutOfFestivalDate_whenGettingAllArrivalsByDate_itThrowsAnOutOfFestivalDatesException() {
        doThrow(OutOfFestivalDatesException.class).when(festivalDates).validateEventDate(A_DATE);
        assertThatExceptionOfType(OutOfFestivalDatesException.class).isThrownBy(() -> {
            transportUseCase.getAllArrivalsByDate(A_DATE);
        });
    }

    @Test
    public void whenGettingAllDeparturesByDate_itAsksTheRepositoryForAllDeparturesForTheDate() {
        transportUseCase.getAllDeparturesByDate(A_DATE);
        verify(shuttleRepository).findAllByDirectionAndDate(Direction.DEPARTURE, A_DATE);
    }

    @Test
    public void whenGettingAllDeparturesByDate_itReturnsAllTheDeparturesForTheDate() {
        List<Shuttle> shuttles = addShuttles(NUMBER_OF_SHUTTLES, Direction.DEPARTURE, A_DATE);
        when(shuttleRepository.findAllByDirectionAndDate(Direction.DEPARTURE, A_DATE)).thenReturn(shuttles);
        when(shuttleDtoMapper.toDtos(shuttles)).thenReturn(shuttleDtos);

        assertThat(transportUseCase.getAllDeparturesByDate(A_DATE)).isEqualTo(shuttleDtos);
    }

    @Test
    public void givenOutOfFestivalDate_whenGettingAllDeparturesByDate_itThrowsAnOutOfFestivalDatesException() {
        doThrow(OutOfFestivalDatesException.class).when(festivalDates).validateEventDate(A_DATE);
        assertThatExceptionOfType(OutOfFestivalDatesException.class).isThrownBy(() -> {
            transportUseCase.getAllDeparturesByDate(A_DATE);
        });
    }

    private Shuttle createShuttle(Direction direction, LocalDate date) {
        return new ETSpaceship(direction, date);
    }

    private List<Shuttle> addShuttles(int quantity, Direction direction) {
        return addShuttles(quantity, direction, A_DATE);
    }

    private List<Shuttle> addShuttles(int quantity, Direction direction, LocalDate date) {
        List<Shuttle> shuttlesAdded = new ArrayList<>();
        for (int i = 0; i < quantity; i++) {
            Shuttle shuttle = createShuttle(direction, date);
            shuttleRepository.add(shuttle);
            shuttlesAdded.add(shuttle);
        }

        return shuttlesAdded;
    }
}
