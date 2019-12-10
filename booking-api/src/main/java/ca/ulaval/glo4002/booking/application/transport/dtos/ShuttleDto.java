package ca.ulaval.glo4002.booking.application.transport.dtos;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import ca.ulaval.glo4002.booking.domain.transport.PassengerNumber;
import ca.ulaval.glo4002.booking.domain.transport.shuttles.ShuttleCategory;

public class ShuttleDto {
    
    public String date;
    public String shuttleName;
    public List<Long> passengers;

    public ShuttleDto() {}

    public ShuttleDto(LocalDate date, ShuttleCategory shuttleCategory, List<PassengerNumber> passengers) {
        this.date = date.toString();
        this.shuttleName = shuttleCategory.toString();
        this.passengers = passengers.stream().map(PassengerNumber::getValue).collect(Collectors.toList());
    }
}
