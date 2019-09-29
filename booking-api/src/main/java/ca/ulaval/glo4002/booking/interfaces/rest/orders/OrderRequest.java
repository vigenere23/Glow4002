package ca.ulaval.glo4002.booking.interfaces.rest.orders;

import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import ca.ulaval.glo4002.booking.domain.passOrdering.passes.PassCategory;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.PassOption;

public class OrderRequest {

    public final PassOption passOption;
    public final PassCategory passCategory;
    public final List<OffsetDateTime> eventDates;

    @JsonCreator
    public OrderRequest(
        @JsonProperty(value = "passOption", required = true) PassOption passOption,
        @JsonProperty(value = "passCategory", required = true) PassCategory passCategory,
        @JsonProperty(value = "eventDates") List<OffsetDateTime> eventDates
    ) {
        this.passOption = passOption;
        this.passCategory = passCategory;
        this.eventDates = eventDates;
    }
}
