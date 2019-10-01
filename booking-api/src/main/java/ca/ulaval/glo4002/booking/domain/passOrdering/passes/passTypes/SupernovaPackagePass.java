package ca.ulaval.glo4002.booking.domain.passOrdering.passes.passTypes;

import java.time.OffsetDateTime;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

public class SupernovaPackagePass extends PackagePass {

    public SupernovaPackagePass(OffsetDateTime startDate, OffsetDateTime endDate) {
        super(startDate, endDate);
        this.price = Money.of(CurrencyUnit.CAD, 700000);
    }
}
