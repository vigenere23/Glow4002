package ca.ulaval.glo4002.booking.interfaces.rest.orders;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import ca.ulaval.glo4002.booking.interfaces.exceptions.ApiException;

public class OrderRequest {

    public final String passOption;
    public final String passCategory;
    public final List<String> eventDates;

    @JsonCreator
    public OrderRequest(
        @JsonProperty(value = "passOption", required = true) String passOption,
        @JsonProperty(value = "passCategory", required = true) String passCategory,
        @JsonProperty(value = "eventDates") List<String> eventDates
    ) throws ApiException {
        this.passOption = passOption;
        this.passCategory = passCategory;
        this.eventDates = eventDates;
    }
}
