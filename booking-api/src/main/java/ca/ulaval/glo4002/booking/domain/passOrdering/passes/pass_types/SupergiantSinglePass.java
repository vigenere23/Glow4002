package ca.ulaval.glo4002.booking.domain.passOrdering.passes.pass_types;

import java.time.LocalDateTime;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

public class SupergiantSinglePass extends SinglePass {

    public SupergiantSinglePass(LocalDateTime eventDate) {
        super(eventDate);
        this.price = Money.of(CurrencyUnit.CAD, 100000);
    }
}
