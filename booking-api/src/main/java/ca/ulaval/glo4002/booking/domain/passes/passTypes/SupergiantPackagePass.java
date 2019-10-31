package ca.ulaval.glo4002.booking.domain.passes.passTypes;

import java.time.LocalDate;

import ca.ulaval.glo4002.booking.domain.Price;
import ca.ulaval.glo4002.booking.domain.passes.PassCategory;

public class SupergiantPackagePass extends PackagePass {

    public SupergiantPackagePass(LocalDate startDate, LocalDate endDate) {
        super(startDate, endDate);
        price = new Price(500000);
    }

    @Override
    public PassCategory getPassCategory() {
        return PassCategory.SUPERGIANT;
    }
}
