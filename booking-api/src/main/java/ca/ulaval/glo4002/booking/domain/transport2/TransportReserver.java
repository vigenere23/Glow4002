package ca.ulaval.glo4002.booking.domain.transport2;

import java.time.LocalDate;
import java.util.List;

import javax.inject.Inject;

public class TransportReserver {

    @Inject private ShuttleFiller shuttleFiller;
    @Inject private ShuttleRepository shuttleRepository;
    
    public void reserveDeparture(ShuttleCategory shuttleCategory, LocalDate date, PassengerNumber passengerNumber) {
        reserveDeparture(shuttleCategory, date, passengerNumber, 1);
    }

    public void reserveDeparture(ShuttleCategory shuttleCategory, LocalDate date, PassengerNumber passengerNumber, int numberOfPassengers) {
        List<Shuttle> departureShuttles = shuttleRepository.findByDirection(Direction.DEPARTURE);
        List<Shuttle> shuttlesToSave = assignNewPlace(departureShuttles, shuttleCategory, date, passengerNumber, numberOfPassengers);
        shuttleRepository.saveDeparture(shuttlesToSave);
    }

    public void reserveArrival(ShuttleCategory shuttleCategory, LocalDate date, PassengerNumber passengerNumber) {
        reserveArrival(shuttleCategory, date, passengerNumber, 1);
    }

    public void reserveArrival(ShuttleCategory shuttleCategory, LocalDate date, PassengerNumber passengerNumber, int numberOfPassengers) {
        List<Shuttle> arrivalShuttles = shuttleRepository.findByDirection(Direction.ARRIVAL);
        List<Shuttle> shuttlesToSave = assignNewPlace(arrivalShuttles, shuttleCategory, date, passengerNumber, numberOfPassengers);
        shuttleRepository.saveArrival(shuttlesToSave);
    }   

    private List<Shuttle> assignNewPlace(List<Shuttle> shuttlesToFill, ShuttleCategory shuttleCategory, LocalDate date, PassengerNumber passengerNumber, int numberOfPassengers) {
        return shuttleFiller.fillShuttle(shuttlesToFill, shuttleCategory, passengerNumber, date, numberOfPassengers);
    }
}
