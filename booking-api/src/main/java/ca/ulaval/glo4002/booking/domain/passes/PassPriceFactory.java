package ca.ulaval.glo4002.booking.domain.passes;

import ca.ulaval.glo4002.booking.domain.Price;

public class PassPriceFactory {

    public Price create(PassOption passOption, PassCategory passCategory) {
        switch (passOption) {
        case SINGLE_PASS:
            return createSinglePassPrice(passCategory);
        case PACKAGE:
            return createPackagePassPrice(passCategory);
        default:
            throw new IllegalArgumentException(String.format("No pass exists for option %s", passOption.toString()));
        }
    }

    private Price createSinglePassPrice(PassCategory passCategory) {
        switch (passCategory) {
            case NEBULA:
                return new Price(50000);
            case SUPERGIANT:
                return new Price(100000);
            case SUPERNOVA:
                return new Price(150000);
            default:
                throw new IllegalArgumentException(
                    String.format("No pass exists for category %s", passCategory.toString()));
        }
    }

    private Price createPackagePassPrice(PassCategory passCategory) {
        switch (passCategory) {
        case NEBULA:
            return new Price(250000);
        case SUPERGIANT:
            return new Price(500000);
        case SUPERNOVA:
            return new Price(700000);
        default:
            throw new IllegalArgumentException(
                String.format("No pass exists for category %s", passCategory.toString()));
        }
    }
}
