package ca.ulaval.glo4002.booking.domain.passes;

import java.time.LocalDate;

import ca.ulaval.glo4002.booking.domain.Price;
import ca.ulaval.glo4002.booking.domain.festivals.FestivalDates;

public class PassFactory {

    private FestivalDates festivalDates;

    public PassFactory(FestivalDates festivalDates) {
        this.festivalDates = festivalDates;
    }

    public Pass create(PassOption passOption, PassCategory passCategory) {
        return create(passOption, passCategory, null);
    }

    public Pass create(PassOption passOption, PassCategory passCategory, LocalDate eventDate) {
        switch(passOption) {
            case SINGLE_PASS:
                return createSinglePass(passCategory, eventDate);
            case PACKAGE:
                return createPackagePass(passCategory);
            default:
                throw new IllegalArgumentException(
                    String.format("No pass exists for option %s", passOption.toString())
                );
        }
    }

    private Pass createSinglePass(PassCategory passCategory, LocalDate eventDate) {
        if (eventDate == null) {
            throw new IllegalArgumentException("A single pass must have an event date");
        }

        Price price = Price.zero();

        switch(passCategory) {
            case NEBULA:
                price = new Price(50000);
                break;
            case SUPERGIANT:
                price = new Price(100000);
                break;
            case SUPERNOVA:
                price = new Price(150000);
                break;
            default:
                throw new IllegalArgumentException(
                    String.format("No pass exists for category %s", passCategory.toString())
                );
        }

        return new Pass(festivalDates, PassOption.SINGLE_PASS, passCategory, price, eventDate, eventDate);
    }

    private Pass createPackagePass(PassCategory passCategory) {
        Price price = Price.zero();

        switch(passCategory) {
            case NEBULA:
                price = new Price(250000);
                break;
            case SUPERGIANT:
                price = new Price(500000);
                break;
            case SUPERNOVA:
                price = new Price(700000);
                break;
            default:
                throw new IllegalArgumentException(
                    String.format("No pass exists for category %s", passCategory.toString())
                );
        }

        return new Pass(festivalDates, PassOption.SINGLE_PASS, passCategory, price, festivalDates.getStartDate(), festivalDates.getEndDate());
    }
}
