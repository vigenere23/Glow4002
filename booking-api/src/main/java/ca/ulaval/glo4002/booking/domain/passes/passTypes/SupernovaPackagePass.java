package ca.ulaval.glo4002.booking.domain.passes.passTypes;

import java.time.LocalDate;

import ca.ulaval.glo4002.booking.domain.Price;
import ca.ulaval.glo4002.booking.domain.passes.PassCategory;

public class SupernovaPackagePass extends PackagePass {

    public SupernovaPackagePass(LocalDate startDate, LocalDate endDate) {
        super(startDate, endDate);
        price = new Price(700000);
    }

    @Override
    public PassCategory getPassCategory() {
        return PassCategory.SUPERNOVA;
    }
}
