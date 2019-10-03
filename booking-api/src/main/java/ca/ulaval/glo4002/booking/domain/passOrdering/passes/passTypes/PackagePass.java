package ca.ulaval.glo4002.booking.domain.passOrdering.passes.passTypes;

import java.time.OffsetDateTime;

import ca.ulaval.glo4002.booking.domain.passOrdering.passes.Pass;

public abstract class PackagePass extends Pass {

    protected PackagePass(OffsetDateTime startDate, OffsetDateTime endDate) {
        super(startDate, endDate);
    }
}
