package ca.ulaval.glo4002.booking.domain.artists;

import ca.ulaval.glo4002.booking.domain.passes.PassNumber;

public class ArtistProgramInformation {
    private String artistName;
    private PassNumber passNumber;
    private int groupSize;

    public ArtistProgramInformation(String artistName, PassNumber passNumber, int groupSize) {
        this.artistName = artistName;
        this.passNumber = passNumber;
        this.groupSize = groupSize;
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