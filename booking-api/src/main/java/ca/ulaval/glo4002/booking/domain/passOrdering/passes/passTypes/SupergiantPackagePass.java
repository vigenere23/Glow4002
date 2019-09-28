package ca.ulaval.glo4002.booking.domain.passOrdering.passes.passTypes;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

public class SupergiantPackagePass extends PackagePass {

    public SupergiantPackagePass() {
        this.price = Money.of(CurrencyUnit.CAD, 500000);
    }
}
