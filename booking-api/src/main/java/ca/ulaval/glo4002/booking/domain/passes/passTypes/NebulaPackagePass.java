package ca.ulaval.glo4002.booking.domain.passes.passTypes;

import java.time.LocalDate;

import ca.ulaval.glo4002.booking.domain.Price;
import ca.ulaval.glo4002.booking.domain.passes.PassCategory;

public class NebulaPackagePass extends PackagePass {

    public NebulaPackagePass(LocalDate startDate, LocalDate endDate) {
        super(startDate, endDate);
        price = new Price(250000);
    }

    @Override
    public PassCategory getPassCategory() {
        return PassCategory.NEBULA;
    }
}
