package ca.ulaval.glo4002.booking.domain.passes.factories;

import ca.ulaval.glo4002.booking.domain.festivals.FestivalDates;
import ca.ulaval.glo4002.booking.domain.passes.PassCategory;
import ca.ulaval.glo4002.booking.domain.passes.passTypes.NebulaPackagePass;
import ca.ulaval.glo4002.booking.domain.passes.passTypes.PackagePass;
import ca.ulaval.glo4002.booking.domain.passes.passTypes.SupergiantPackagePass;
import ca.ulaval.glo4002.booking.domain.passes.passTypes.SupernovaPackagePass;

public class PackagePassFactory {

    private FestivalDates festival;

    public PackagePassFactory(FestivalDates festivalDates) {
        this.festival = festivalDates;
    }

    public PackagePass create(PassCategory passCategory) {
        switch (passCategory) {
            case NEBULA:
                return new NebulaPackagePass(festival.getStartDate(), festival.getEndDate());
            case SUPERGIANT:
                return new SupergiantPackagePass(festival.getStartDate(), festival.getEndDate());
            case SUPERNOVA:
                return new SupernovaPackagePass(festival.getStartDate(), festival.getEndDate());
            default:
                throw new IllegalArgumentException(
                    String.format("No package pass impemented for category %s.", passCategory)
                );
        }
    }
}
