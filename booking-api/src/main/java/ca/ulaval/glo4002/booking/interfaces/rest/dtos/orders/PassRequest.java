package ca.ulaval.glo4002.booking.interfaces.rest.dtos.orders;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import ca.ulaval.glo4002.booking.domain.passOrdering.passes.PassCategory;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.PassOption;
import ca.ulaval.glo4002.booking.interfaces.rest.exceptions.InvalidFormatException;

public class PassRequest {
    public final PassOption passOption;
    public final PassCategory passCategory;
    public final List<OffsetDateTime> eventDates;

    @JsonCreator
    public PassRequest(
        @JsonProperty(value = "passOption", required = true) String passOption,
        @JsonProperty(value = "passCategory", required = true) String passCategory,
        @JsonProperty(value = "eventDates") Collection<String> eventDates
    ) throws InvalidFormatException {
        try {
            this.passOption = PassOption.fromString(passOption);
            this.passCategory = PassCategory.fromString(passCategory);
            if (eventDates != null) {
                this.eventDates = eventDates
                    .stream()
                    .map(LocalDate::parse)
                    .map(localDate -> OffsetDateTime.of(localDate, LocalTime.NOON, ZoneOffset.UTC))
                    .collect(Collectors.toList());
            } else {
                this.eventDates = null;
            }
        }
        catch (Exception exception) {
            throw new InvalidFormatException();
        }
    }
}
