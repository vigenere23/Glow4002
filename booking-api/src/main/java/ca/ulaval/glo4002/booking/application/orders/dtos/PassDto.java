package ca.ulaval.glo4002.booking.application.orders.dtos;

import java.time.LocalDate;

import ca.ulaval.glo4002.booking.domain.passes.PassCategory;
import ca.ulaval.glo4002.booking.domain.passes.PassOption;
import ca.ulaval.glo4002.booking.domain.passes.pass_number.PassNumber;

public class PassDto {

    public PassNumber passNumber;
    public PassCategory passCategory;
    public PassOption passOption;
    public LocalDate startDate;
    public LocalDate endDate;

    public PassDto(PassNumber passNumber, PassCategory passCategory, PassOption passOption, LocalDate startDate, LocalDate endDate) {
        this.passNumber = passNumber;
        this.passCategory = passCategory;
        this.passOption = passOption;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
