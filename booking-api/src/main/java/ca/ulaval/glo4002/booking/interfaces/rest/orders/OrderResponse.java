package ca.ulaval.glo4002.booking.interfaces.rest.orders;

import java.time.OffsetDateTime;
import java.util.List;

import ca.ulaval.glo4002.booking.domain.passOrdering.passes.PassCategory;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.PassOption;

public class OrderResponse {

    public final String passOption;
    public final String passCategory;
    public final List<OffsetDateTime> eventDates;

    public OrderResponse(PassOption passOption, PassCategory passCategory, List<OffsetDateTime> eventDates) {
        this.passOption = passOption.toString();
        this.passCategory = passCategory.toString();
        this.eventDates = eventDates;
    }
}
