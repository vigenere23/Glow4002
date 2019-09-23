package ca.ulaval.glo4002.booking.domain.passOrdering.passes.pass_types;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

public class SupernovaPackagePass extends PackagePass {

    public SupernovaPackagePass() {
        this.price = Money.of(CurrencyUnit.CAD, 700000);
    }
}
