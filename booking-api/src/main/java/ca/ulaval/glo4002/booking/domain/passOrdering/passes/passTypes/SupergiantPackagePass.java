package ca.ulaval.glo4002.booking.domain.passOrdering.passes.passTypes;

import java.time.LocalDate;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import ca.ulaval.glo4002.booking.domain.passOrdering.passes.PassCategory;

public class SupergiantPackagePass extends PackagePass {

    public SupergiantPackagePass(LocalDate startDate, LocalDate endDate) {
        super(startDate, endDate);
        price = Money.of(CurrencyUnit.CAD, 500000);
    }

    @Override
    public PassCategory getPassCategory() {
        return PassCategory.SUPERGIANT;
    }
}
