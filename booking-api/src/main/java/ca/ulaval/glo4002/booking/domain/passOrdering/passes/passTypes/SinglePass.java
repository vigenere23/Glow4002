package ca.ulaval.glo4002.booking.domain.passOrdering.passes.passTypes;

import java.time.LocalDateTime;

import ca.ulaval.glo4002.booking.domain.passOrdering.passes.Pass;

public abstract class SinglePass extends Pass {

    protected LocalDateTime eventDate;

    protected SinglePass(LocalDateTime eventDate) {
        this.eventDate = eventDate;
    }
}
