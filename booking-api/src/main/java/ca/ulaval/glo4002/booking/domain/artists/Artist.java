package ca.ulaval.glo4002.booking.domain.artists;

import java.time.LocalDate;

import ca.ulaval.glo4002.booking.domain.Price;
import ca.ulaval.glo4002.booking.domain.transport.PassengerNumber;
import ca.ulaval.glo4002.booking.domain.transport.ShuttleCategory;
import ca.ulaval.glo4002.booking.domain.transport.TransportReserver;

public class Artist {
    private String name;
    private int popularity;
    private Price price;
    private PassengerNumber passengerNumber;
    private int groupSize;

    public Artist(String name, int popularity, Price price, int groupSize, PassengerNumber passengerNumber) {
        this.name = name;
        this.popularity = popularity;
        this.price = price;
        this.groupSize = groupSize;
        this.passengerNumber = passengerNumber;
    }

    public Artist(String name, Price price, PassengerNumber passengerNumber) {
        this(name, 0, price, 1, passengerNumber);
    }

    public void orderShuttle(TransportReserver transportReserver, LocalDate date) {
        ShuttleCategory shuttleCategory = groupSize == 1 ? ShuttleCategory.ET_SPACESHIP : ShuttleCategory.MILLENNIUM_FALCON;
        transportReserver.reserveDeparture(shuttleCategory, date, passengerNumber, groupSize);
        transportReserver.reserveArrival(shuttleCategory, date, passengerNumber, groupSize);
    }

    public String getName() {
        return name;
    }

    public int getPopularity() {
        return popularity;
    }

    public Price getPrice() {
        return price;
    }

    public int getGroupSize() {
        return groupSize;
    }

    public PassengerNumber getPassengerNumber() {
        return passengerNumber;
    }
}