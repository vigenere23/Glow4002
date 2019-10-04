package ca.ulaval.glo4002.booking.domain.passOrdering.passes.passTypes;

import java.time.OffsetDateTime;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import ca.ulaval.glo4002.booking.domain.passOrdering.passes.PassCategory;

public class SupernovaSinglePass extends SinglePass {

    public SupernovaSinglePass(OffsetDateTime eventDate) {
        super(eventDate);
        this.price = Money.of(CurrencyUnit.CAD, 150000);
    }

    @Override
    public PassCategory getPassCategory() {
        return PassCategory.SUPERNOVA;
    }
}
