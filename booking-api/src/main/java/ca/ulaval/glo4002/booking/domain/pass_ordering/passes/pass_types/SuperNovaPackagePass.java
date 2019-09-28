package ca.ulaval.glo4002.booking.domain.pass_ordering.passes.pass_types;

import org.joda.money.Money;

public class SuperNovaPackagePass extends PackagePass {

    public SuperNovaPackagePass() {
        this.price = Money.parse("USD 700000");
    }
}
