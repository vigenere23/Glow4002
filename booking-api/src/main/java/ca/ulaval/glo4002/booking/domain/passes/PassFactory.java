package ca.ulaval.glo4002.booking.domain.passes;

import java.time.LocalDate;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import ca.ulaval.glo4002.booking.domain.festivals.FestivalDates;
import ca.ulaval.glo4002.booking.domain.passes.Pass;
import ca.ulaval.glo4002.booking.domain.passes.PassCategory;
import ca.ulaval.glo4002.booking.domain.passes.PassOption;

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

        Money price = Money.of(CurrencyUnit.CAD, 0);

        switch(passCategory) {
            case NEBULA:
                price = Money.of(CurrencyUnit.CAD, 50000);
                break;
            case SUPERGIANT:
                price = Money.of(CurrencyUnit.CAD, 100000);
                break;
            case SUPERNOVA:
                price = Money.of(CurrencyUnit.CAD, 150000);
                break;
            default:
                throw new IllegalArgumentException(
                    String.format("No pass exists for category %s", passCategory.toString())
                );
        }

        return new Pass(festivalDates, PassOption.SINGLE_PASS, passCategory, price, eventDate, eventDate);
    }

    private Pass createPackagePass(PassCategory passCategory) {
        Money price = Money.of(CurrencyUnit.CAD, 0);

        switch(passCategory) {
            case NEBULA:
                price = Money.of(CurrencyUnit.CAD, 250000);
                break;
            case SUPERGIANT:
                price = Money.of(CurrencyUnit.CAD, 500000);
                break;
            case SUPERNOVA:
                price = Money.of(CurrencyUnit.CAD, 700000);
                break;
            default:
                throw new IllegalArgumentException(
                    String.format("No pass exists for category %s", passCategory.toString())
                );
        }

        return new Pass(festivalDates, PassOption.SINGLE_PASS, passCategory, price, festivalDates.getStartDate(), festivalDates.getEndDate());
    }
}
