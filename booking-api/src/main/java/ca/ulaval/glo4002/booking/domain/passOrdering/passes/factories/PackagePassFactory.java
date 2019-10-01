package ca.ulaval.glo4002.booking.domain.passOrdering.passes.factories;

import java.time.OffsetDateTime;

import ca.ulaval.glo4002.booking.domain.passOrdering.passes.PassCategory;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.passTypes.NebulaPackagePass;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.passTypes.PackagePass;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.passTypes.SupergiantPackagePass;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.passTypes.SupernovaPackagePass;

public class PackagePassFactory {

    private OffsetDateTime festivalStart;
    private OffsetDateTime festivalEnd;

    public PackagePassFactory(OffsetDateTime festivalStart, OffsetDateTime festivalEnd) {
        this.festivalStart = festivalStart;
        this.festivalEnd = festivalEnd;
    }

    public PackagePass create(PassCategory passCategory) {
        switch(passCategory) {
            case NEBULA:
                return new NebulaPackagePass(festivalStart, festivalEnd);
            case SUPERGIANT:
                return new SupergiantPackagePass(festivalStart, festivalEnd);
            case SUPERNOVA:
                return new SupernovaPackagePass(festivalStart, festivalEnd);
            default:
                throw new IllegalArgumentException(
                    String.format("No package pass impemented for category %s.", passCategory)
                );
        }
    }
}
