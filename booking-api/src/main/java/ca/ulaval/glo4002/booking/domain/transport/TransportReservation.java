package ca.ulaval.glo4002.booking.domain.transport;

import java.time.LocalDate;
import java.util.List;

import ca.ulaval.glo4002.booking.domain.passes.PassNumber;

public class TransportReservation {

    private ShuttleFiller shuttleFiller;
    
    public TransportReservation() {
        shuttleFiller = new ShuttleFiller();
    }
    
    public List<Shuttle> reserveShuttle(ShuttleCategory shuttleCategory, LocalDate date, PassNumber passNumber, List<Shuttle> currentShuttlesForLocation, int passengers) {
        return shuttleFiller.fillShuttle(currentShuttlesForLocation, shuttleCategory, passNumber, date, passengers);
    }  
}
