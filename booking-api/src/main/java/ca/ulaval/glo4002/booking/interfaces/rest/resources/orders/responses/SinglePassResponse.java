package ca.ulaval.glo4002.booking.interfaces.rest.resources.orders.responses;

import java.time.LocalDate;

import ca.ulaval.glo4002.booking.application.orders.dtos.PassDto;

public class SinglePassResponse extends PassResponse {

    public LocalDate eventDate;

    public SinglePassResponse(PassDto passDto) {
        super(passDto);
        eventDate = passDto.startDate;
    }
}
