package ca.ulaval.glo4002.booking.interfaces.rest.resources.orders.requests;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import ca.ulaval.glo4002.booking.domain.passes.PassCategory;
import ca.ulaval.glo4002.booking.domain.passes.PassOption;
import ca.ulaval.glo4002.booking.interfaces.rest.exceptions.InvalidFormatClientException;

public class PassRequest {
    public final PassOption passOption;
    public final PassCategory passCategory;
    public final Optional<List<LocalDate>> eventDates;

    @JsonCreator
    public PassRequest(
        @JsonProperty(value = "passOption", required = true) String passOption,
        @JsonProperty(value = "passCategory", required = true) String passCategory,
        @JsonProperty(value = "eventDates") List<String> eventDates
    ) {
        try {
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
        catch (Exception exception) {
            throw new InvalidFormatClientException();
        }
    }
}
