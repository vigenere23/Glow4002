package ca.ulaval.glo4002.booking.domain.passOrdering.passes.passTypes;

import java.time.LocalDate;

import ca.ulaval.glo4002.booking.domain.passOrdering.passes.Pass;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.PassOption;

public abstract class PackagePass extends Pass {

    protected PackagePass(LocalDate startDate, LocalDate endDate) {
        super(startDate, endDate);
    }

    @Override
    public PassOption getPassOption() {
        return PassOption.PACKAGE;
    }
}
