package ca.ulaval.glo4002.booking.api.resources.passOrder.dto;

import java.time.LocalDate;

public class SinglePassDto extends PassDto {

    public LocalDate eventDate;

    public SinglePassDto(PassDto passDto) {
        this.passCategory = passDto.passCategory;
        this.passNumber = passDto.passNumber;
        this.passOption = passDto.passOption;
    }
}
