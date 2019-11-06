package ca.ulaval.glo4002.booking.domain.transport;

import java.time.LocalDate;
import java.util.List;

import ca.ulaval.glo4002.booking.domain.passes.PassNumber;

public class TransportReservation {

    private ShuttleFiller shuttleFiller;
    
    public TransportReservation() {
        shuttleFiller = new ShuttleFiller();
    }
    
    public List<Shuttle> reserveDeparture(ShuttleCategory shuttleCategory, LocalDate date, PassNumber passNumber, List<Shuttle> departureShuttles, int passengers) {
        return assignNewPlace(departureShuttles, shuttleCategory, date, passNumber, passengers);
    }
    
    public List<Shuttle> reserveArrival(ShuttleCategory shuttleCategory, LocalDate date, PassNumber passNumber, List<Shuttle> arrivalShuttles, int passengers) {
        return assignNewPlace(arrivalShuttles, shuttleCategory, date, passNumber, passengers);
    }   

    private List<Shuttle> assignNewPlace(List<Shuttle> shuttlesToFill, ShuttleCategory shuttleCategory, LocalDate date, PassNumber passNumber, int passengers) {
        return shuttleFiller.fillShuttle(shuttlesToFill, shuttleCategory, passNumber, date, passengers);
    }
}
