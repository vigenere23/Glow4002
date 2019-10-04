package ca.ulaval.glo4002.booking.domain.passOrdering.passes.passTypes;

import java.time.OffsetDateTime;

import ca.ulaval.glo4002.booking.domain.passOrdering.passes.Pass;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.PassOption;

public abstract class SinglePass extends Pass {

    protected SinglePass(OffsetDateTime eventDate) {
        super(eventDate, eventDate);
    }

    @Override
    public PassOption getPassOption() {
        return PassOption.SINGLE_PASS;
    }

    public OffsetDateTime getEventDate() {
        return startDate;
    }
}
