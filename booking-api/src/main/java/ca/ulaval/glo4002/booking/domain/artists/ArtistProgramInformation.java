package ca.ulaval.glo4002.booking.domain.artists;

import ca.ulaval.glo4002.booking.domain.passes.PassNumber;

public class ArtistProgramInformation {
    private PassNumber id;
    private int groupSize;

    public ArtistProgramInformation(int id, int groupSize) {
        this.id = PassNumber.of(id);
        this.groupSize = groupSize;
    }

    public PassNumber getId() {
        return id;
    }

    public int getGroupSize() {
        return groupSize;
    }
}