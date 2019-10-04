package ca.ulaval.glo4002.booking.domain.passOrdering.passes.passTypes;

import java.time.LocalDate;

import ca.ulaval.glo4002.booking.domain.passOrdering.passes.Pass;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.PassOption;

public abstract class SinglePass extends Pass {

    protected SinglePass(LocalDate eventDate) {
        super(eventDate, eventDate);
    }

    @Override
    public PassOption getPassOption() {
        return PassOption.SINGLE_PASS;
    }

    public LocalDate getEventDate() {
        return startDate;
    }
}
