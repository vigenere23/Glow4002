package ca.ulaval.glo4002.booking.domain.passes.passTypes;

import java.time.LocalDate;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import ca.ulaval.glo4002.booking.domain.passes.PassCategory;

public class SupernovaPackagePass extends PackagePass {

    public SupernovaPackagePass(LocalDate startDate, LocalDate endDate) {
        super(startDate, endDate);
        price = Money.of(CurrencyUnit.CAD, 700000);
    }

    @Override
    public PassCategory getPassCategory() {
        return PassCategory.SUPERNOVA;
    }
}
