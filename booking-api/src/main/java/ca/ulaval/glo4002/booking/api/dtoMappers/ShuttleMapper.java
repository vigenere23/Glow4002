package ca.ulaval.glo4002.booking.api.dtoMappers;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import ca.ulaval.glo4002.booking.api.dtos.transport.ShuttleDto;
import ca.ulaval.glo4002.booking.domain.passes.PassNumber;
import ca.ulaval.glo4002.booking.domain.transport.Shuttle;

public class ShuttleMapper {

    public List<ShuttleDto> getShuttlesDto(List<Shuttle> shuttles) {
        List<ShuttleDto> shuttlesDto = new LinkedList<>();
        for (Shuttle shuttle: shuttles) {        
            ShuttleDto shuttleDto = new ShuttleDto();
            shuttleDto.date = shuttle.getDate().toString();
            shuttleDto.passengers = shuttle.getPassNumbers().stream().map(PassNumber::getValue).collect(Collectors.toList());
            shuttleDto.shuttleName = shuttle.getCategory().toString();
            shuttlesDto.add(shuttleDto);
        }
        return shuttlesDto; 
    }
}