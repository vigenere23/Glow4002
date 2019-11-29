package ca.ulaval.glo4002.booking.application.transport;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.ulaval.glo4002.booking.application.transport.dtos.ShuttleDto;
import ca.ulaval.glo4002.booking.application.transport.dtos.ShuttleDtoMapper;
import ca.ulaval.glo4002.booking.domain.exceptions.OutOfFestivalDatesException;
import ca.ulaval.glo4002.booking.domain.dates.FestivalDates;
import ca.ulaval.glo4002.booking.domain.transport.Shuttle;
import ca.ulaval.glo4002.booking.domain.transport.ShuttleRepository;
import ca.ulaval.glo4002.booking.domain.transport.SpaceX;

@ExtendWith(MockitoExtension.class)
class TransportUseCaseTest {

    // private final static LocalDate SOME_DATE = LocalDate.of(2050, 7, 18);
    // private final static LocalDate OUT_OF_FESTIVAL_DATE = LocalDate.of(2050, 7, 10);

    // private List<Shuttle> shuttlesEarth;
    // private List<Shuttle> shuttlesUlavalogy;
    // private List<ShuttleDto> shuttleDtos;
    
    // @Mock FestivalDates festivalDates;
    // @Mock ShuttleRepository shuttleRepository;
    // @Mock ShuttleDtoMapper shuttleDtoMapper;
    // @InjectMocks TransportUseCase transportUseCase;

    // @BeforeEach
    // public void setUpTransportUseCase() {
    //     prepareShuttles();
    // }

    // @Test
    // public void whenGetAllDeparture_thenCallMethodGetShuttlesFromEarthFromRepository() {
    //     transportUseCase.getAllDepartures();
    //     verify(shuttleRepository).findShuttlesByLocation(Location.EARTH);
    // }

    // @Test
    // public void whenGetAllDeparture_thenReturnListOfShuttlesForLocation() {
    //     when(shuttleRepository.findShuttlesByLocation(Location.EARTH)).thenReturn(shuttlesEarth);
    //     when(shuttleDtoMapper.toDtos(shuttlesEarth)).thenReturn(shuttleDtos);

    //     List<ShuttleDto> departureShuttles = transportUseCase.getAllDepartures();
    //     assertThat(departureShuttles).isEqualTo(shuttleDtos);
    // }

    // @Test
    // public void whenGetAllArrivals_thenCallMethodGetShuttlesFromEarthFromRepository() {
    //     transportUseCase.getAllArrivals();
    //     verify(shuttleRepository).findShuttlesByLocation(Location.ULAVALOGY);
    // }

    // @Test
    // public void whenGetAllArrivals_thenReturnListOfShuttlesForLocation() {
    //     when(shuttleRepository.findShuttlesByLocation(Location.ULAVALOGY)).thenReturn(shuttlesUlavalogy);
    //     when(shuttleDtoMapper.toDtos(shuttlesUlavalogy)).thenReturn(shuttleDtos);
        
    //     List<ShuttleDto> arrivalShuttles = transportUseCase.getAllArrivals();
    //     assertThat(arrivalShuttles).isEqualTo(shuttleDtos);
    // }

    // @Test
    // public void givenDate_whenGetShuttlesDepartureByDate_thenCallMethodGetShuttlesByDateFromRepository() {
    //     transportUseCase.getShuttlesDepartureByDate(SOME_DATE);
    //     verify(shuttleRepository).findShuttlesByDate(Location.EARTH, SOME_DATE);
    // }

    // @Test
    // public void givenDate_whenGetShuttlesDepartureByDate_thenReturnListOfShuttlesForLocationAndDate() {
    //     when(shuttleRepository.findShuttlesByDate(Location.EARTH, SOME_DATE)).thenReturn(shuttlesEarth);
    //     when(shuttleDtoMapper.toDtos(shuttlesEarth)).thenReturn(shuttleDtos);
        
    //     List<ShuttleDto> departueShuttles = transportUseCase.getShuttlesDepartureByDate(SOME_DATE);
    //     assertThat(departueShuttles).isEqualTo(shuttleDtos);
    // }

    // @Test
    // public void givenDateOutsideOfFestival_whenGetShuttlesDepartureByDate_throwsException() {
    //     doThrow(new OutOfFestivalDatesException(SOME_DATE, SOME_DATE))
    //         .when(festivalDates).validateEventDate(any(LocalDate.class));
    //     assertThatExceptionOfType(OutOfFestivalDatesException.class).isThrownBy(() -> {
    //         transportUseCase.getShuttlesDepartureByDate(OUT_OF_FESTIVAL_DATE);
    //     });
    // }

    // @Test
    // public void givenDate_whenGetShuttlesArrivalByDate_thenCallMethodGetShuttlesByDateFromRepository() {
    //     transportUseCase.getShuttlesArrivalByDate(SOME_DATE);
    //     verify(shuttleRepository).findShuttlesByDate(Location.ULAVALOGY, SOME_DATE);
    // }

    // @Test
    // public void givenDate_whenGetShuttlesArrivalByDate_thenReturnListOfShuttlesForLocationAndDate() {
    //     when(shuttleRepository.findShuttlesByDate(Location.ULAVALOGY, SOME_DATE)).thenReturn(shuttlesUlavalogy);
    //     when(shuttleDtoMapper.toDtos(shuttlesUlavalogy)).thenReturn(shuttleDtos);
        
    //     List<ShuttleDto> arrivalShuttles = transportUseCase.getShuttlesArrivalByDate(SOME_DATE);
    //     assertThat(arrivalShuttles).isEqualTo(shuttleDtos);
    // }

    // @Test
    // public void givenDateOutsideOfFestival_whenGetShuttlesArrivalByDate_throwsException() {
    //     doThrow(new OutOfFestivalDatesException(SOME_DATE, SOME_DATE))
    //         .when(festivalDates).validateEventDate(any(LocalDate.class));

    //     assertThatExceptionOfType(OutOfFestivalDatesException.class).isThrownBy(() -> {
    //         transportUseCase.getShuttlesArrivalByDate(OUT_OF_FESTIVAL_DATE);
    //     });
    // }

    // private void prepareShuttles() {
    //     shuttlesEarth = new ArrayList<>();
    //     shuttlesUlavalogy = new ArrayList<>();
    //     shuttleDtos = new ArrayList<>();

    //     Shuttle shuttle = new SpaceX(SOME_DATE);
    //     shuttlesEarth.add(shuttle);
    //     shuttlesUlavalogy.add(shuttle);

    //     ShuttleDto mockedShuttleDto = mock(ShuttleDto.class);
    //     shuttleDtos.add(mockedShuttleDto);
    // }
}
