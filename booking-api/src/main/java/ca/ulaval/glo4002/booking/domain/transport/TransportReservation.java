package ca.ulaval.glo4002.booking.domain.transport;

import java.time.LocalDate;
import java.util.List;

import ca.ulaval.glo4002.booking.domain.passes.PassNumber;

public class TransportReservation {

    private ShuttleFiller shuttleFiller;
    
    public TransportReservation() {
        shuttleFiller = new ShuttleFiller();
    }
    
    public void reserveDeparture(ShuttleCategory shuttleCategory, LocalDate date, PassNumber passNumber, List<Shuttle> departureShuttles) {
        assignNewPlace(departureShuttles, Location.EARTH, shuttleCategory, date, passNumber);
    }
    
    public void reserveArrival(ShuttleCategory shuttleCategory, LocalDate date, PassNumber passNumber, List<Shuttle> arrivalShuttles) {
        assignNewPlace(arrivalShuttles, Location.ULAVALOGY, shuttleCategory, date, passNumber);
    }   

    private void assignNewPlace(List<Shuttle> shuttlesToFill, Location location, ShuttleCategory shuttleCategory, LocalDate date, PassNumber passNumber) {
        shuttleFiller.fillShuttle(shuttlesToFill, shuttleCategory, passNumber, date);
    }
}
