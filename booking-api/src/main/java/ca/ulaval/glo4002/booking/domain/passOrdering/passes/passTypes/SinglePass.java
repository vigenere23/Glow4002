package ca.ulaval.glo4002.booking.domain.passOrdering.passes.passTypes;

import java.time.LocalDateTime;

import ca.ulaval.glo4002.booking.domain.passOrdering.passes.Pass;

public abstract class SinglePass extends Pass {

    protected LocalDateTime eventDate;

    // TODO receive services here
    protected SinglePass(LocalDateTime eventDate) {
        this.eventDate = eventDate;
        // TODO call OxygenService.reserve(eventDate)
        // TODO call TransportService.reserve(Festival.eventDates)
    }

	public LocalDateTime getDate() {
		return this.eventDate;
	}
}
