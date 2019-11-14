package ca.ulaval.glo4002.booking.domain.artists;

import ca.ulaval.glo4002.booking.domain.Price;
import ca.ulaval.glo4002.booking.domain.transport.PassengerNumber;

public class ArtistProgramInformation {
    private String artistName;
    private PassengerNumber passengerNumber;
    private int groupSize;
    private Price price;

    public ArtistProgramInformation(String artistName, PassengerNumber passengerNumber, int groupSize, Price price) {
        this.artistName = artistName;
        this.passengerNumber = passengerNumber;
        this.groupSize = groupSize;
        this.price = price;
    }

    public Price getPrice() {
        return price;
    }

    public String getArtistName() {
        return artistName;
    }

    public PassengerNumber getPassengerNumber() {
        return passengerNumber;
    }

    public int getGroupSize() {
        return groupSize;
    }
}
