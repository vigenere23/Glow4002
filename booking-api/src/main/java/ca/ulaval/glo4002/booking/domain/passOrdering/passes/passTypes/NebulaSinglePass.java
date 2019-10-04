package ca.ulaval.glo4002.booking.domain.passOrdering.passes.passTypes;

import java.time.OffsetDateTime;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import ca.ulaval.glo4002.booking.domain.passOrdering.passes.PassCategory;

public class NebulaSinglePass extends SinglePass {

    public NebulaSinglePass(OffsetDateTime eventDate) {
        super(eventDate);
        price = Money.of(CurrencyUnit.CAD, 50000);
    }

    @Override
    public PassCategory getPassCategory() {
        return PassCategory.NEBULA;
    }
}
