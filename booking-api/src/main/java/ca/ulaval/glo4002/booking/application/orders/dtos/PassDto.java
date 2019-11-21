package ca.ulaval.glo4002.booking.application.orders.dtos;

import ca.ulaval.glo4002.booking.domain.passes.PassCategory;
import ca.ulaval.glo4002.booking.domain.passes.PassOption;
import ca.ulaval.glo4002.booking.domain.passes.pass_number.PassNumber;

public class PassDto {

    public long passNumber;
    public String passCategory;
    public String passOption;

    public PassDto() {}

    public PassDto(PassNumber passNumber, PassCategory passCategory, PassOption passOption) {
        this.passNumber = passNumber.getValue();
        this.passCategory = passCategory.toString();
        this.passOption = passOption.toString();
    }
}
