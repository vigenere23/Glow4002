package ca.ulaval.glo4002.booking.domain.pass_ordering.passes.pass_types;

import org.joda.money.Money;

public class NebulaPackagePass extends PackagePass {

    public NebulaPackagePass() {
        this.price = Money.parse("USD 250000");
    }
}
