package ca.ulaval.glo4002.booking.api.dtos.orders;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import ca.ulaval.glo4002.booking.domain.passes.PassCategory;
import ca.ulaval.glo4002.booking.domain.passes.PassOption;

public class PassRequest {
    public final PassOption passOption;
    public final PassCategory passCategory;
    public final List<LocalDate> eventDates;

    @JsonCreator
    public PassRequest(
        @JsonProperty(value = "passOption", required = true) String passOption,
        @JsonProperty(value = "passCategory", required = true) String passCategory,
        @JsonProperty(value = "eventDates") List<String> eventDates
    ) {
        this.passOption = PassOption.fromString(passOption);
        this.passCategory = PassCategory.fromString(passCategory);
        if (eventDates != null) {
            this.eventDates = eventDates
                .stream()
                .map(LocalDate::parse)
                .collect(Collectors.toList());
        } else {
            this.eventDates = null;
        }
    }
}
