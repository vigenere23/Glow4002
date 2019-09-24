package ca.ulaval.glo4002.booking.domain.passOrdering.passes.passTypes;

import java.time.LocalDateTime;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

public class NebulaSinglePass extends SinglePass {

    public NebulaSinglePass(LocalDateTime eventDate) {
        super(eventDate);
        this.price = Money.of(CurrencyUnit.CAD, 50000);
    }
}
