package ca.ulaval.glo4002.booking.domain.passOrdering.passes.passTypes;

import java.time.OffsetDateTime;

import ca.ulaval.glo4002.booking.domain.passOrdering.passes.Pass;

public abstract class SinglePass extends Pass {

    protected OffsetDateTime eventDate;

    // TODO receive services here
    protected SinglePass(OffsetDateTime eventDate) {
        this.eventDate = eventDate;
        // TODO call OxygenService.reserve(eventDate)
        // TODO call TransportService.reserve(Festival.eventDates)
    }

	public OffsetDateTime getDate() {
		return this.eventDate;
	}
}
