package ca.ulaval.glo4002.booking.domain.passOrdering.passes.passTypes;

import java.time.OffsetDateTime;

import ca.ulaval.glo4002.booking.domain.passOrdering.passes.Pass;

public abstract class SinglePass extends Pass {

    protected SinglePass(OffsetDateTime eventDate) {
        super(eventDate, eventDate);
    }
}
