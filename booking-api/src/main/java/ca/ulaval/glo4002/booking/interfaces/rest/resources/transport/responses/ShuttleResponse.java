package ca.ulaval.glo4002.booking.interfaces.rest.resources.transport.responses;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import ca.ulaval.glo4002.booking.application.transport.dtos.ShuttleDto;

public class ShuttleResponse {

    public LocalDate date;
    public String shuttleName;
    public List<Long> passengers;

    public ShuttleResponse(ShuttleDto shuttleDto) {
        date = shuttleDto.date;
        shuttleName = shuttleDto.shuttleCategory.toString();
        passengers = shuttleDto.passengers
            .stream()
            .map(passengerNumber -> passengerNumber.getValue())
            .collect(Collectors.toList());
    }
}
