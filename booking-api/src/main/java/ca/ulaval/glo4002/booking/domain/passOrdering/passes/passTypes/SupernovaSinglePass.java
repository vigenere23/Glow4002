package ca.ulaval.glo4002.booking.domain.passOrdering.passes.passTypes;

import java.time.LocalDate;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import ca.ulaval.glo4002.booking.domain.passOrdering.passes.PassCategory;

public class SupernovaSinglePass extends SinglePass {

    public SupernovaSinglePass(LocalDate eventDate) {
        super(eventDate);
        price = Money.of(CurrencyUnit.CAD, 150000);
    }

    @Override
    public PassCategory getPassCategory() {
        return PassCategory.SUPERNOVA;
    }
}
