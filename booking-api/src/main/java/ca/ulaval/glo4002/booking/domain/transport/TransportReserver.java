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
    
    public void reserveDeparture(ShuttleCategory shuttleCategory, LocalDate date, PassNumber passNumber) {
        List<Shuttle> departureShuttles = shuttleRepository.findShuttlesByLocation(Location.EARTH);
        List<Shuttle> shuttlesToSave = assignNewPlace(departureShuttles, shuttleCategory, date, passNumber);
        shuttleRepository.saveDeparture(shuttlesToSave);
    }
    
    public List<Shuttle> reserveArrival(ShuttleCategory shuttleCategory, LocalDate date, PassNumber passNumber, List<Shuttle> arrivalShuttles) {
        return assignNewPlace(arrivalShuttles, shuttleCategory, date, passNumber);
    }   

    private List<Shuttle> assignNewPlace(List<Shuttle> shuttlesToFill, ShuttleCategory shuttleCategory, LocalDate date, PassNumber passNumber) {
        return shuttleFiller.fillShuttle(shuttlesToFill, shuttleCategory, passNumber, date);
    }
}
