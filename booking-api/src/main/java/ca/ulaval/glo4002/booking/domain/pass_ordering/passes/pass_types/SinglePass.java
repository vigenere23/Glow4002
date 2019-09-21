package ca.ulaval.glo4002.booking.domain.pass_ordering.passes.pass_types;

import java.time.LocalDateTime;

import ca.ulaval.glo4002.booking.domain.pass_ordering.passes.Pass;

public abstract class SinglePass extends Pass {

    protected LocalDateTime eventDate;

    protected SinglePass(LocalDateTime eventDate) {
        this.eventDate = eventDate;
    }
}
