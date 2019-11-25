package ca.ulaval.glo4002.booking.api.resources.dates.requests;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdateDatesRequest {

    public final LocalDate startDate;
    public final LocalDate endDate;

    @JsonCreator
    public UpdateDatesRequest(
        @JsonProperty(value = "beginDate", required = true) String stringStartDate,
        @JsonProperty(value = "endDate", required = true) String stringEndDate
    ) {
        startDate = LocalDate.parse(stringStartDate);
        endDate = LocalDate.parse(stringEndDate);
    }
}
