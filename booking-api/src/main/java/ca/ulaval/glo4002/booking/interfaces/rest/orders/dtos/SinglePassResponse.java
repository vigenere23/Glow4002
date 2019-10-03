package ca.ulaval.glo4002.booking.interfaces.rest.orders.dtos;

import java.time.OffsetDateTime;

import ca.ulaval.glo4002.booking.domain.passOrdering.passes.PassCategory;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.PassOption;

public class SinglePassResponse extends PassResponse {

    public final OffsetDateTime eventDate;

    public SinglePassResponse(long passNumber, PassOption passOption, PassCategory passCategory, OffsetDateTime eventDate) {
        super(passNumber, passOption, passCategory);
        this.eventDate = eventDate;
    }
}
