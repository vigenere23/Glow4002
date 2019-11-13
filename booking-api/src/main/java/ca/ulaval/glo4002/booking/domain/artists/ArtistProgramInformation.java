package ca.ulaval.glo4002.booking.domain.artists;

import ca.ulaval.glo4002.booking.domain.Price;
import ca.ulaval.glo4002.booking.domain.passes.PassNumber;

public class ArtistProgramInformation {
    private String artistName;
    private PassNumber passNumber;
    private int groupSize;
    private Price price;

    public ArtistProgramInformation(String artistName, PassNumber passNumber, int groupSize, Price price) {
        this.artistName = artistName;
        this.passNumber = passNumber;
        this.groupSize = groupSize;
        this.price = price;
    }

    public Price getPrice() {
        return price;
    }

    public String getArtistName() {
        return artistName;
    }

    public PassNumber getPassNumber() {
        return passNumber;
    }

    public int getGroupSize() {
        return groupSize;
    }
}