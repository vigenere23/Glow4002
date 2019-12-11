package ca.ulaval.glo4002.booking.application.transport.dtos;

import java.time.LocalDate;
import java.util.List;

import ca.ulaval.glo4002.booking.domain.transport.PassengerNumber;
import ca.ulaval.glo4002.booking.domain.transport.shuttles.ShuttleCategory;

public class ShuttleDto {
    
    public LocalDate date;
    public ShuttleCategory shuttleCategory;
    public List<PassengerNumber> passengers;

    public ShuttleDto(LocalDate date, ShuttleCategory shuttleCategory, List<PassengerNumber> passengers) {
        this.date = date;
        this.shuttleCategory = shuttleCategory;
        this.passengers = passengers;
    }
}
