package ca.ulaval.glo4002.booking.interfaces.rest.resources.orders.requests;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import ca.ulaval.glo4002.booking.domain.passes.PassCategory;
import ca.ulaval.glo4002.booking.domain.passes.PassOption;

public class PassRequest {
    private final PassOption passOption;
    private final PassCategory passCategory;
    private final Optional<List<LocalDate>> eventDates;

    @JsonCreator
    public PassRequest(
        @JsonProperty(value = "passOption", required = true) String passOption,
        @JsonProperty(value = "passCategory", required = true) String passCategory,
        @JsonProperty(value = "eventDates") List<String> eventDates
    ) {
        this.passOption = PassOption.fromString(passOption);
        this.passCategory = PassCategory.fromString(passCategory);
        this.eventDates = eventDates == null || eventDates.isEmpty()
            ? Optional.empty()
            : Optional.of(eventDates
                .stream()
                .map(LocalDate::parse)
                .collect(Collectors.toList())
            );
    }

    public PassOption getPassOption() {
        return passOption;
    }

    public PassCategory getPassCategory() {
        return passCategory;
    }

    public Optional<List<LocalDate>> getEventDates() {
        return eventDates;
    }
}
