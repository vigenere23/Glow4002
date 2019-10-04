package ca.ulaval.glo4002.booking.domain.passOrdering.passes.passTypes;

import java.time.OffsetDateTime;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import ca.ulaval.glo4002.booking.domain.passOrdering.passes.PassCategory;

public class NebulaPackagePass extends PackagePass {

    public NebulaPackagePass(OffsetDateTime startDate, OffsetDateTime endDate) {
        super(startDate, endDate);
        price = Money.of(CurrencyUnit.CAD, 250000);
    }

    @Override
    public PassCategory getPassCategory() {
        return PassCategory.NEBULA;
    }
}
