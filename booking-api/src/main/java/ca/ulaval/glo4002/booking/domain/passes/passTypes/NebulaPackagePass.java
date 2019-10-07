package ca.ulaval.glo4002.booking.domain.passes.passTypes;

import java.time.LocalDate;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import ca.ulaval.glo4002.booking.domain.passes.PassCategory;

public class NebulaPackagePass extends PackagePass {

    public NebulaPackagePass(LocalDate startDate, LocalDate endDate) {
        super(startDate, endDate);
        price = Money.of(CurrencyUnit.CAD, 250000);
    }

    @Override
    public PassCategory getPassCategory() {
        return PassCategory.NEBULA;
    }
}
