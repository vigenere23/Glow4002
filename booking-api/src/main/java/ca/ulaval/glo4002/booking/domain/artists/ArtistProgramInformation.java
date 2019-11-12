package ca.ulaval.glo4002.booking.domain.artists;

import ca.ulaval.glo4002.booking.domain.passes.PassNumber;

public class ArtistProgramInformation {
    private PassNumber passNumber;
    private int groupSize;

    public ArtistProgramInformation(PassNumber passNumber, int groupSize) {
        this.passNumber = passNumber;
        this.groupSize = groupSize;
    }

    public PassNumber getPassNumber() {
        return passNumber;
    }

    public int getGroupSize() {
        return groupSize;
    }
}