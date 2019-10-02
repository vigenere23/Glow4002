package ca.ulaval.glo4002.booking.interfaces.dtos;

import java.time.OffsetDateTime;
import java.util.List;

import ca.ulaval.glo4002.booking.domain.passOrdering.passes.PassCategory;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.PassOption;

public class PassDto {

    public PassOption passOption;
    public PassCategory passCategory;
    public List<OffsetDateTime> eventDates;

    public PassDto(PassOption passOption, PassCategory passCategory, List<OffsetDateTime> eventDates) {
        this.passOption = passOption;
        this.passCategory = passCategory;
        this.eventDates = eventDates;
    }
}
