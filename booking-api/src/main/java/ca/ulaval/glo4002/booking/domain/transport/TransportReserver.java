package ca.ulaval.glo4002.booking.domain.transport;

import java.time.LocalDate;
import java.util.List;

import javax.inject.Inject;

public class TransportReserver {

    private ShuttleFiller shuttleFiller;
    private ShuttleRepository shuttleRepository;
    
    @Inject
    public TransportReserver(ShuttleRepository shuttleRepository, ShuttleFiller shuttleFiller) {
        this.shuttleRepository = shuttleRepository;
        this.shuttleFiller = shuttleFiller;
    }
    
    public void reserveDeparture(ShuttleCategory shuttleCategory, LocalDate date, PassengerNumber passengerNumber, int numberOfPassengers) {
        List<Shuttle> departureShuttles = shuttleRepository.findShuttlesByLocation(Location.EARTH);
        List<Shuttle> shuttlesToSave = assignNewPlace(departureShuttles, shuttleCategory, date, passengerNumber, numberOfPassengers);
        shuttleRepository.saveDeparture(shuttlesToSave);
    }

    public void reserveArrival(ShuttleCategory shuttleCategory, LocalDate date, PassengerNumber passengerNumber, int numberOfPassengers) {
        List<Shuttle> arrivalShuttles = shuttleRepository.findShuttlesByLocation(Location.ULAVALOGY);
        List<Shuttle> shuttlesToSave = assignNewPlace(arrivalShuttles, shuttleCategory, date, passengerNumber, numberOfPassengers);
        shuttleRepository.saveArrival(shuttlesToSave);
    }   

    private List<Shuttle> assignNewPlace(List<Shuttle> shuttlesToFill, ShuttleCategory shuttleCategory, LocalDate date, PassengerNumber passengerNumber, int numberOfPassengers) {
        return shuttleFiller.fillShuttle(shuttlesToFill, shuttleCategory, passengerNumber, date, numberOfPassengers);
    }
}
