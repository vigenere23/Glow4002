package ca.ulaval.glo4002.booking.domain.passes.passTypes;

import java.time.LocalDate;

import ca.ulaval.glo4002.booking.domain.Price;
import ca.ulaval.glo4002.booking.domain.passes.PassCategory;

public class NebulaSinglePass extends SinglePass {

    public NebulaSinglePass(LocalDate eventDate) {
        super(eventDate);
        price = new Price(50000);
    }

	@Override
	public PassCategory getPassCategory() {
		return PassCategory.NEBULA;
	}
}
