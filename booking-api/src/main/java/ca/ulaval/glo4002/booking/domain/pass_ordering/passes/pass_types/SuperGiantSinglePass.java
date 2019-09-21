package ca.ulaval.glo4002.booking.domain.pass_ordering.passes.pass_types;

import java.time.LocalDateTime;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

public class SuperGiantSinglePass extends SinglePass {

    public SuperGiantSinglePass(LocalDateTime eventDate) {
        super(eventDate);
        this.price = Money.of(CurrencyUnit.CAD, 100000);
    }
}
