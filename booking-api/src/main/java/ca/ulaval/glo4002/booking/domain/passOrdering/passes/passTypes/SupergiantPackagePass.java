package ca.ulaval.glo4002.booking.domain.passOrdering.passes.passTypes;

import java.time.OffsetDateTime;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

public class SupergiantPackagePass extends PackagePass {

    public SupergiantPackagePass(OffsetDateTime startDate, OffsetDateTime endDate) {
        super(startDate, endDate);
        this.price = Money.of(CurrencyUnit.CAD, 500000);
    }
}
