package ca.ulaval.glo4002.booking.api.resources.transport.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import ca.ulaval.glo4002.booking.domain.transport.PassengerNumber;
import ca.ulaval.glo4002.booking.domain.transport.Shuttle;

public class ShuttleMapper {

    public List<ShuttleDto> toDto(List<Shuttle> shuttles) {
        List<ShuttleDto> shuttlesDto = new ArrayList<>();
        for (Shuttle shuttle: shuttles) {        
            ShuttleDto shuttleDto = new ShuttleDto();
            shuttleDto.date = shuttle.getDate().toString();
            shuttleDto.passengers = shuttle.getPassengers().stream().map(PassengerNumber::getValue).collect(Collectors.toList());
            shuttleDto.shuttleName = shuttle.getCategory().toString();
            shuttlesDto.add(shuttleDto);
        }
        return shuttlesDto; 
    }
}