package ca.ulaval.glo4002.booking.interfaces.rest.resources.dates.requests;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import ca.ulaval.glo4002.booking.interfaces.rest.exceptions.InvalidFormatClientException;

public class UpdateDatesRequest {

    public final LocalDate startDate;
    public final LocalDate endDate;

    @JsonCreator
    public UpdateDatesRequest(
        @JsonProperty(value = "beginDate", required = true) String stringStartDate,
        @JsonProperty(value = "endDate", required = true) String stringEndDate
    ) {
        try {
            startDate = LocalDate.parse(stringStartDate);
            endDate = LocalDate.parse(stringEndDate);
        }
        catch (Exception exception) {
            throw new InvalidFormatClientException();
        }
    }
}
