package ca.ulaval.glo4002.booking.domain.transport;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
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

import ca.ulaval.glo4002.booking.domain.Price;
import ca.ulaval.glo4002.booking.domain.profit.OutcomeSaver;

@ExtendWith(MockitoExtension.class)
class ShuttleFillerTest {
    
    private final static Direction DIRECTION = Direction.ARRIVAL;
    private final static LocalDate DATE = LocalDate.now();
    private final static ShuttleCategory SHUTTLE_CATEGORY = ShuttleCategory.MILLENNIUM_FALCON;
    private final static PassengerNumber PASSENGER_NUMBER = new PassengerNumber(0);
    private final static int SOME_PLACES = 5;

    private List<Shuttle> shuttles;
    
    @Mock Shuttle shuttle1;
    @Mock Shuttle shuttle2;
    @Mock ShuttleRepository shuttleRepository;
    @Mock ShuttleFactory shuttleFactory;
    @Mock OutcomeSaver outcomeSaver;
    @InjectMocks ShuttleFiller shuttleFiller;

    @BeforeEach
    public void setup() {
        shuttles = new ArrayList<>();
    }

    @Test
    public void givenNoAvailableShuttle_whenFillingShuttles_itCreatesANewShuttle() {
        when(shuttleRepository.findAllAvailable(DIRECTION, DATE, SHUTTLE_CATEGORY)).thenReturn(new ArrayList<>());
        when(shuttleFactory.create(DIRECTION, DATE, SHUTTLE_CATEGORY)).thenReturn(shuttle2);
        
        shuttleFiller.fillShuttles(DIRECTION, DATE, SHUTTLE_CATEGORY, PASSENGER_NUMBER, SOME_PLACES);
        
        verify(shuttleFactory, times(1)).create(DIRECTION, DATE, SHUTTLE_CATEGORY);
    }

    @Test
    public void givenNoAvailableShuttle_whenFillingShuttles_itFillsPlacesOfTheCreatedShuttle() {
        when(shuttleRepository.findAllAvailable(DIRECTION, DATE, SHUTTLE_CATEGORY)).thenReturn(new ArrayList<>());
        when(shuttleFactory.create(DIRECTION, DATE, SHUTTLE_CATEGORY)).thenReturn(shuttle2);
        
        shuttleFiller.fillShuttles(DIRECTION, DATE, SHUTTLE_CATEGORY, PASSENGER_NUMBER, SOME_PLACES);
        
        verify(shuttle2).addPassengers(PASSENGER_NUMBER, SOME_PLACES);
    }

    @Test
    public void givenNoAvailableShuttle_whenFillingShuttles_itAddTheShuttlePriceAsOutcome() {
        when(shuttleRepository.findAllAvailable(DIRECTION, DATE, SHUTTLE_CATEGORY)).thenReturn(new ArrayList<>());
        when(shuttleFactory.create(DIRECTION, DATE, SHUTTLE_CATEGORY)).thenReturn(shuttle2);
        
        shuttleFiller.fillShuttles(DIRECTION, DATE, SHUTTLE_CATEGORY, PASSENGER_NUMBER, SOME_PLACES);
        
        verify(shuttle2).saveOutcome(outcomeSaver);
    }

    @Test
    public void givenAvailableShuttle_whenFillingShuttles_itTriesToFillThatShuttle() {
        shuttles.add(shuttle1);
        when(shuttleRepository.findAllAvailable(DIRECTION, DATE, SHUTTLE_CATEGORY)).thenReturn(shuttles);

        shuttleFiller.fillShuttles(DIRECTION, DATE, SHUTTLE_CATEGORY, PASSENGER_NUMBER, SOME_PLACES);

        verify(shuttle1).addPassengers(PASSENGER_NUMBER, SOME_PLACES);
    }

    @Test
    public void givenAvailableShuttleWithEnoughSpace_whenFillingShuttles_itDoesNotCreateANewShuttle() {
        shuttles.add(shuttle1);
        when(shuttleRepository.findAllAvailable(DIRECTION, DATE, SHUTTLE_CATEGORY)).thenReturn(shuttles);

        shuttleFiller.fillShuttles(DIRECTION, DATE, SHUTTLE_CATEGORY, PASSENGER_NUMBER, SOME_PLACES);

        verify(shuttleFactory, times(0)).create(DIRECTION, DATE, SHUTTLE_CATEGORY);
    }

    @Test
    public void givenAvailableShuttleWithEnoughSpace_whenFillingShuttles_itDoesNotAddAnyOutcome() {
        shuttles.add(shuttle1);
        when(shuttleRepository.findAllAvailable(DIRECTION, DATE, SHUTTLE_CATEGORY)).thenReturn(shuttles);

        shuttleFiller.fillShuttles(DIRECTION, DATE, SHUTTLE_CATEGORY, PASSENGER_NUMBER, SOME_PLACES);

        verify(shuttle1, times(0)).saveOutcome(outcomeSaver);
        verify(outcomeSaver, times(0)).addOutcome(any(Price.class));
    }

    @Test
    public void givenAvailableShuttleWithoutEnoughSpace_whenFillingShuttles_itCreatesANewShuttle() {
        doThrow(IllegalArgumentException.class).when(shuttle1).addPassengers(PASSENGER_NUMBER, SOME_PLACES);
        shuttles.add(shuttle1);
        when(shuttleRepository.findAllAvailable(DIRECTION, DATE, SHUTTLE_CATEGORY)).thenReturn(shuttles);
        when(shuttleFactory.create(DIRECTION, DATE, SHUTTLE_CATEGORY)).thenReturn(shuttle2);

        shuttleFiller.fillShuttles(DIRECTION, DATE, SHUTTLE_CATEGORY, PASSENGER_NUMBER, SOME_PLACES);

        verify(shuttleFactory, times(1)).create(DIRECTION, DATE, SHUTTLE_CATEGORY);
    }

    @Test
    public void givenAvailableShuttleWithoutEnoughSpace_whenFillingShuttles_itFillsPlacesOfTheCreatedShuttle() {
        doThrow(IllegalArgumentException.class).when(shuttle1).addPassengers(PASSENGER_NUMBER, SOME_PLACES);
        shuttles.add(shuttle1);
        when(shuttleRepository.findAllAvailable(DIRECTION, DATE, SHUTTLE_CATEGORY)).thenReturn(shuttles);
        when(shuttleFactory.create(DIRECTION, DATE, SHUTTLE_CATEGORY)).thenReturn(shuttle2);

        shuttleFiller.fillShuttles(DIRECTION, DATE, SHUTTLE_CATEGORY, PASSENGER_NUMBER, SOME_PLACES);

        verify(shuttle2).addPassengers(PASSENGER_NUMBER, SOME_PLACES);
    }

    @Test
    public void givenAvailableShuttleWithoutEnoughSpace_whenFillingShuttles_itAddTheShuttlePriceAsOutcome() {
        doThrow(IllegalArgumentException.class).when(shuttle1).addPassengers(PASSENGER_NUMBER, SOME_PLACES);
        shuttles.add(shuttle1);
        when(shuttleRepository.findAllAvailable(DIRECTION, DATE, SHUTTLE_CATEGORY)).thenReturn(shuttles);
        when(shuttleFactory.create(DIRECTION, DATE, SHUTTLE_CATEGORY)).thenReturn(shuttle2);

        shuttleFiller.fillShuttles(DIRECTION, DATE, SHUTTLE_CATEGORY, PASSENGER_NUMBER, SOME_PLACES);

        verify(shuttle2).saveOutcome(outcomeSaver);
    }

    @Test
    public void givenAvailableShuttleAndCreatedShuttleWithoutEnoughSpace_whenFillingShuttles_itThrowsAnIllegalArgumentException() {
        doThrow(IllegalArgumentException.class).when(shuttle1).addPassengers(PASSENGER_NUMBER, SOME_PLACES);
        doThrow(IllegalArgumentException.class).when(shuttle2).addPassengers(PASSENGER_NUMBER, SOME_PLACES);
        shuttles.add(shuttle1);
        shuttles.add(shuttle2);
        when(shuttleRepository.findAllAvailable(DIRECTION, DATE, SHUTTLE_CATEGORY)).thenReturn(shuttles);
        when(shuttleFactory.create(DIRECTION, DATE, SHUTTLE_CATEGORY)).thenReturn(shuttle2);

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            shuttleFiller.fillShuttles(DIRECTION, DATE, SHUTTLE_CATEGORY, PASSENGER_NUMBER, SOME_PLACES);
        });
    }

    @Test
    public void givenAvailableShuttleAndCreatedShuttleWithoutEnoughSpace_whenFillingShuttles_itDoesNotAddAnyIncome() {
        doThrow(IllegalArgumentException.class).when(shuttle1).addPassengers(PASSENGER_NUMBER, SOME_PLACES);
        doThrow(IllegalArgumentException.class).when(shuttle2).addPassengers(PASSENGER_NUMBER, SOME_PLACES);
        shuttles.add(shuttle1);
        shuttles.add(shuttle2);
        when(shuttleRepository.findAllAvailable(DIRECTION, DATE, SHUTTLE_CATEGORY)).thenReturn(shuttles);
        when(shuttleFactory.create(DIRECTION, DATE, SHUTTLE_CATEGORY)).thenReturn(shuttle2);

        try {
            shuttleFiller.fillShuttles(DIRECTION, DATE, SHUTTLE_CATEGORY, PASSENGER_NUMBER, SOME_PLACES);
        }
        catch (Exception exception) {
            verify(shuttle1, times(0)).saveOutcome(outcomeSaver);
            verify(shuttle2, times(0)).saveOutcome(outcomeSaver);   
            verify(outcomeSaver, times(0)).addOutcome(any(Price.class));
        }
    }

    @Test
    public void givenOneAvailableShuttleWithoutEnoughSpaceAndOneWithEnoughSpace_whenFillingShuttles_itTriesToFillTheSecondShuttle() {
        doThrow(IllegalArgumentException.class).when(shuttle1).addPassengers(PASSENGER_NUMBER, SOME_PLACES);
        shuttles.add(shuttle1);
        shuttles.add(shuttle2);
        when(shuttleRepository.findAllAvailable(DIRECTION, DATE, SHUTTLE_CATEGORY)).thenReturn(shuttles);

        shuttleFiller.fillShuttles(DIRECTION, DATE, SHUTTLE_CATEGORY, PASSENGER_NUMBER, SOME_PLACES);

        verify(shuttle2).addPassengers(PASSENGER_NUMBER, SOME_PLACES);
    }
}
