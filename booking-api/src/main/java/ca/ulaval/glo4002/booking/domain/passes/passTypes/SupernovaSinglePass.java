package ca.ulaval.glo4002.booking.domain.passes.passTypes;

import java.time.LocalDate;

import ca.ulaval.glo4002.booking.domain.Price;
import ca.ulaval.glo4002.booking.domain.passes.PassCategory;

public class SupernovaSinglePass extends SinglePass {

    public SupernovaSinglePass(LocalDate eventDate) {
        super(eventDate);
        price = new Price(150000);
    }

    @Override
    public PassCategory getPassCategory() {
        return PassCategory.SUPERNOVA;
    }
}
