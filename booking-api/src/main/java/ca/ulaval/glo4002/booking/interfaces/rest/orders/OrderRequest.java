package ca.ulaval.glo4002.booking.interfaces.rest.orders;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

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
        @JsonProperty(value = "passOption", required = true) String passOption,
        @JsonProperty(value = "passCategory", required = true) String passCategory,
        @JsonProperty(value = "eventDates") List<String> eventDates
    ) {
        this.passOption = PassOption.fromString(passOption);
        this.passCategory = PassCategory.fromString(passCategory);
        this.eventDates = eventDates
            .stream()
            .map(stringDate -> OffsetDateTime.of(LocalDate.parse(stringDate), LocalTime.NOON, ZoneOffset.UTC))
            .collect(Collectors.toList());
    }
}
