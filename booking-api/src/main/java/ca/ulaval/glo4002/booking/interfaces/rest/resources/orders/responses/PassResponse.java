package ca.ulaval.glo4002.booking.interfaces.rest.resources.orders.responses;

import ca.ulaval.glo4002.booking.application.orders.dtos.PassDto;

public class PassResponse {

    public long passNumber;
    public String passCategory;
    public String passOption;

    public PassResponse(PassDto passDto) {
        passNumber = passDto.passNumber.getValue();
        passCategory = passDto.passCategory.toString();
        passOption = passDto.passOption.toString();
    }
}
