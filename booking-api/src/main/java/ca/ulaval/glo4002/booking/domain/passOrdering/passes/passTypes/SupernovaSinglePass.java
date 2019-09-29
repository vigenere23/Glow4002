package ca.ulaval.glo4002.booking.domain.passOrdering.passes.passTypes;

import java.time.OffsetDateTime;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

public class SupernovaSinglePass extends SinglePass {

    public SupernovaSinglePass(OffsetDateTime eventDate) {
        super(eventDate);
        this.price = Money.of(CurrencyUnit.CAD, 150000);
    }
}