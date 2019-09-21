package ca.ulaval.glo4002.booking.domain.pass_ordering.passes.factories;

import ca.ulaval.glo4002.booking.domain.pass_ordering.passes.PassCategory;
import ca.ulaval.glo4002.booking.domain.pass_ordering.passes.pass_types.NebulaPackagePass;
import ca.ulaval.glo4002.booking.domain.pass_ordering.passes.pass_types.PackagePass;
import ca.ulaval.glo4002.booking.domain.pass_ordering.passes.pass_types.SuperGiantPackagePass;
import ca.ulaval.glo4002.booking.domain.pass_ordering.passes.pass_types.SuperNovaPackagePass;

public class PackagePassFactory {

    public PackagePass newPass(PassCategory passCategory) {
        switch(passCategory) {
            case NEBULA:
                return new NebulaPackagePass();
            case SUPERGIANT:
                return new SuperGiantPackagePass();
            case SUPERNOVA:
                return new SuperNovaPackagePass();
            default:
                throw new IllegalArgumentException(
                    String.format("No package pass impemented for category %s.", passCategory)
                );
        }
    }
}
