package ca.ulaval.glo4002.booking.domain.transport;

import java.time.LocalDate;
import java.util.List;

import ca.ulaval.glo4002.booking.domain.passes.PassNumber;

public class TransportReservation {

    private ShuttleFiller shuttleFiller;
    
    public TransportReservation() {
        shuttleFiller = new ShuttleFiller();
    }

    public void reserveShuttles(ShuttleCategory shuttleCategory, LocalDate date, PassNumber passNumber, List<Shuttle> departureShuttles, List<Shuttle> arrivalShuttles) {
        reserveDeparture(shuttleCategory, date, passNumber, departureShuttles);
        reserveArrival(shuttleCategory, date, passNumber, arrivalShuttles);
    }
    
    private void reserveDeparture(ShuttleCategory shuttleCategory, LocalDate date, PassNumber passNumber, List<Shuttle> departureShuttles) {
        assignNewPlace(departureShuttles, shuttleCategory, date, passNumber);
    }
    
    private void reserveArrival(ShuttleCategory shuttleCategory, LocalDate date, PassNumber passNumber, List<Shuttle> arrivalShuttles) {
        assignNewPlace(arrivalShuttles, shuttleCategory, date, passNumber);
    }   

    private void assignNewPlace(List<Shuttle> shuttlesToFill, ShuttleCategory shuttleCategory, LocalDate date, PassNumber passNumber) {
        shuttleFiller.fillShuttle(shuttlesToFill, shuttleCategory, passNumber, date);
    }
}
