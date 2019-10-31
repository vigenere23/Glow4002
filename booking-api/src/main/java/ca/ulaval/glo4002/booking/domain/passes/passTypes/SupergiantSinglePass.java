package ca.ulaval.glo4002.booking.domain.passes.passTypes;

import java.time.LocalDate;

import ca.ulaval.glo4002.booking.domain.Price;
import ca.ulaval.glo4002.booking.domain.passes.PassCategory;

public class SupergiantSinglePass extends SinglePass {

    public SupergiantSinglePass(LocalDate eventDate) {
        super(eventDate);
        price = new Price(100000);
    }

    @Override
    public PassCategory getPassCategory() {
        return PassCategory.SUPERGIANT;
    }
}
