package ca.ulaval.glo4002.booking.domain.passes.passTypes;

import java.time.LocalDate;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import ca.ulaval.glo4002.booking.domain.passes.PassCategory;

public class NebulaSinglePass extends SinglePass {

    public NebulaSinglePass(LocalDate eventDate) {
        super(eventDate);
        price = Money.of(CurrencyUnit.CAD, 50000);
    }

	@Override
	public PassCategory getPassCategory() {
		return PassCategory.NEBULA;
	}
}
