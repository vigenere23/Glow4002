package ca.ulaval.glo4002.booking.domain.transport;

import java.time.LocalDate;
import java.util.List;

import ca.ulaval.glo4002.booking.domain.passes.PassNumber;

public class TransportReserver {

    private ShuttleFiller shuttleFiller;
    private ShuttleRepository shuttleRepository;
    
    public TransportReserver(ShuttleRepository shuttleRepository) {
        this.shuttleRepository = shuttleRepository;
        shuttleFiller = new ShuttleFiller();
    }
    
    public void reserveDeparture(ShuttleCategory shuttleCategory, LocalDate date, PassNumber passNumber, int passengers) {
        List<Shuttle> departureShuttles = shuttleRepository.findShuttlesByLocation(Location.EARTH);
        List<Shuttle> shuttlesToSave = assignNewPlace(departureShuttles, shuttleCategory, date, passNumber, passengers);
        shuttleRepository.saveDeparture(shuttlesToSave);
    }
    
    public void reserveArrival(ShuttleCategory shuttleCategory, LocalDate date, PassNumber passNumber, int passengers) {
        List<Shuttle> arrivalShuttles = shuttleRepository.findShuttlesByLocation(Location.ULAVALOGY);
        List<Shuttle> shuttlesToSave = assignNewPlace(arrivalShuttles, shuttleCategory, date, passNumber, passengers);
        shuttleRepository.saveArrival(shuttlesToSave);
    }   

    private List<Shuttle> assignNewPlace(List<Shuttle> shuttlesToFill, ShuttleCategory shuttleCategory, LocalDate date, PassNumber passNumber, int passengers) {
        return shuttleFiller.fillShuttle(shuttlesToFill, shuttleCategory, passNumber, date, passengers);
    }
}
