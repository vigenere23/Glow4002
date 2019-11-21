package ca.ulaval.glo4002.booking.application.orders.dtos;

import java.time.LocalDate;

import ca.ulaval.glo4002.booking.domain.passes.PassCategory;
import ca.ulaval.glo4002.booking.domain.passes.PassOption;
import ca.ulaval.glo4002.booking.domain.passes.pass_number.PassNumber;

public class SinglePassDto extends PassDto {

    public LocalDate eventDate;

    public SinglePassDto() {}

    public SinglePassDto(PassNumber passNumber, PassCategory passCategory, PassOption passOption, LocalDate eventDate) {
        super(passNumber, passCategory, passOption);
        this.eventDate = eventDate;
    }
}
