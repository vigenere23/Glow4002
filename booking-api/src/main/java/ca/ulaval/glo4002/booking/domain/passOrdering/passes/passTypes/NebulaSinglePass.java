package ca.ulaval.glo4002.booking.domain.passOrdering.passes.passTypes;

import java.time.OffsetDateTime;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import ca.ulaval.glo4002.booking.domain.passOrdering.passes.PassCategory;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.PassOption;
import ca.ulaval.glo4002.booking.interfaces.rest.orders.dtos.SinglePassResponse;

public class NebulaSinglePass extends SinglePass {

    public NebulaSinglePass(OffsetDateTime eventDate) {
        super(eventDate);
        this.price = Money.of(CurrencyUnit.CAD, 50000);
    }

    public SinglePassResponse serialize() {
        return new SinglePassResponse(this.passNumber, PassOption.SINGLE_PASS, PassCategory.NEBULA, this.startDate);
    }
}
