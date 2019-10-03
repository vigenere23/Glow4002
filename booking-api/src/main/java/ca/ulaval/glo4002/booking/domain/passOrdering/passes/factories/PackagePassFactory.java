package ca.ulaval.glo4002.booking.domain.passOrdering.passes.factories;

import ca.ulaval.glo4002.booking.domain.festivals.Glow4002;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.PassCategory;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.passTypes.NebulaPackagePass;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.passTypes.PackagePass;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.passTypes.SupergiantPackagePass;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.passTypes.SupernovaPackagePass;

public class PackagePassFactory {

    private Glow4002 festival;

    public PackagePassFactory(Glow4002 festival) {
        this.festival = festival;
    }

    public PackagePass create(PassCategory passCategory) {
        switch(passCategory) {
            case NEBULA:
                return new NebulaPackagePass(this.festival.getStartDate(), this.festival.getEndDate());
            case SUPERGIANT:
                return new SupergiantPackagePass(this.festival.getStartDate(), this.festival.getEndDate());
            case SUPERNOVA:
                return new SupernovaPackagePass(this.festival.getStartDate(), this.festival.getEndDate());
            default:
                throw new IllegalArgumentException(
                    String.format("No package pass impemented for category %s.", passCategory)
                );
        }
    }
}
