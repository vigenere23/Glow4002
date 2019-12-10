package ca.ulaval.glo4002.booking.domain.transport;

import java.time.LocalDate;

import javax.inject.Inject;

import ca.ulaval.glo4002.booking.domain.transport.shuttles.ShuttleCategory;
import ca.ulaval.glo4002.booking.domain.transport.shuttles.ShuttleFiller;

public class TransportReserver {

    @Inject private ShuttleFiller shuttleFiller;
    
    public void reserveDeparture(ShuttleCategory shuttleCategory, LocalDate date, PassengerNumber passengerNumber) {
        reserveDepartures(shuttleCategory, date, passengerNumber, 1);
    }

    public void reserveDepartures(ShuttleCategory shuttleCategory, LocalDate date, PassengerNumber passengerNumber, int numberOfPassengers) {
        shuttleFiller.fillShuttles(Direction.DEPARTURE, date, shuttleCategory, passengerNumber, numberOfPassengers);
    }

    public void reserveArrival(ShuttleCategory shuttleCategory, LocalDate date, PassengerNumber passengerNumber) {
        reserveArrivals(shuttleCategory, date, passengerNumber, 1);
    }

    public void reserveArrivals(ShuttleCategory shuttleCategory, LocalDate date, PassengerNumber passengerNumber, int numberOfPassengers) {
        shuttleFiller.fillShuttles(Direction.ARRIVAL, date, shuttleCategory, passengerNumber, numberOfPassengers);
    }   
}
