package ca.ulaval.glo4002.booking.interfaces.rest.dtos.orders;

import java.time.LocalDate;

import ca.ulaval.glo4002.booking.domain.passOrdering.passes.PassCategory;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.PassOption;

public class SinglePassResponse extends PassResponse {

    public final LocalDate eventDate;

    public SinglePassResponse(long passNumber, PassOption passOption, PassCategory passCategory, LocalDate eventDate) {
        super(passNumber, passOption, passCategory);
        this.eventDate = eventDate;
    }
}
