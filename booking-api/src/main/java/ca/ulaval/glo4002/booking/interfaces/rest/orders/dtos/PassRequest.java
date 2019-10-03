package ca.ulaval.glo4002.booking.interfaces.rest.orders.dtos;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import ca.ulaval.glo4002.booking.domain.passOrdering.passes.PassCategory;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.PassOption;

public class PassRequest {
    public final PassOption passOption;
    public final PassCategory passCategory;
    public final List<OffsetDateTime> eventDates;

    @JsonCreator
    public PassRequest(
        @JsonProperty(value = "passOption", required = true) String passOption,
        @JsonProperty(value = "passCategory", required = true) String passCategory,
        @JsonProperty(value = "eventDates") Collection<String> eventDates
    ) {
        this.passOption = PassOption.fromString(passOption);
        this.passCategory = PassCategory.fromString(passCategory);
        if (eventDates != null) {
            this.eventDates = eventDates.stream().map(OffsetDateTime::parse).collect(Collectors.toList());
        } else {
            this.eventDates = null;
        }
    }
}
