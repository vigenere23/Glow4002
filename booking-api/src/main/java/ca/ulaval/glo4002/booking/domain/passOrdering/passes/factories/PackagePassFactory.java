package ca.ulaval.glo4002.booking.domain.passOrdering.passes.factories;

import ca.ulaval.glo4002.booking.domain.passOrdering.passes.PassCategory;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.passTypes.NebulaPackagePass;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.passTypes.PackagePass;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.passTypes.SupergiantPackagePass;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.passTypes.SupernovaPackagePass;

public class PackagePassFactory {

    public PackagePass newPass(PassCategory passCategory) {
        switch(passCategory) {
            case NEBULA:
                return new NebulaPackagePass();
            case SUPERGIANT:
                return new SupergiantPackagePass();
            case SUPERNOVA:
                return new SupernovaPackagePass();
            default:
                throw new IllegalArgumentException(
                    String.format("No package pass impemented for category %s.", passCategory)
                );
        }
    }
}
