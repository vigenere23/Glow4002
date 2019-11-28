package ca.ulaval.glo4002.booking.domain.transport2;

import java.time.LocalDate;

import javax.inject.Inject;

public class TransportReserver {

    @Inject private ShuttleFiller shuttleFiller;
    
    public void reserveDeparture(ShuttleCategory shuttleCategory, LocalDate date, PassengerNumber passengerNumber) {
        reserveDeparture(shuttleCategory, date, passengerNumber, 1);
    }

    public void reserveDeparture(ShuttleCategory shuttleCategory, LocalDate date, PassengerNumber passengerNumber, int numberOfPassengers) {
        shuttleFiller.fillShuttles(Direction.DEPARTURE, date, shuttleCategory, passengerNumber, numberOfPassengers);
    }

    public void reserveArrival(ShuttleCategory shuttleCategory, LocalDate date, PassengerNumber passengerNumber) {
        reserveArrival(shuttleCategory, date, passengerNumber, 1);
    }

    public void reserveArrival(ShuttleCategory shuttleCategory, LocalDate date, PassengerNumber passengerNumber, int numberOfPassengers) {
        shuttleFiller.fillShuttles(Direction.ARRIVAL, date, shuttleCategory, passengerNumber, numberOfPassengers);
    }   
}
