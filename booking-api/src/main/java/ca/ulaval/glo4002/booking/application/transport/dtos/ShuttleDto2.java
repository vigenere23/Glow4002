package ca.ulaval.glo4002.booking.application.transport.dtos;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import ca.ulaval.glo4002.booking.domain.transport2.PassengerNumber;
import ca.ulaval.glo4002.booking.domain.transport2.ShuttleCategory;

public class ShuttleDto2 {
    
    public String date;
    public String shuttleName;
    public List<Long> passengers;

    public ShuttleDto2() {}

    public ShuttleDto2(LocalDate date, ShuttleCategory shuttleCategory, List<PassengerNumber> passengers) {
        this.date = date.toString();
        this.shuttleName = shuttleCategory.toString();
        this.passengers = passengers.stream().map(PassengerNumber::getValue).collect(Collectors.toList());
    }
}
