package ca.ulaval.glo4002.booking.application.transport;

import ca.ulaval.glo4002.booking.application.transport.dtos.ShuttleDto;
import ca.ulaval.glo4002.booking.application.transport.dtos.ShuttleDtoMapper;
import ca.ulaval.glo4002.booking.domain.dates.FestivalDates;
import ca.ulaval.glo4002.booking.domain.transport.Location;
import ca.ulaval.glo4002.booking.domain.transport.ShuttleRepository;

import java.time.LocalDate;
import java.util.List;

import javax.inject.Inject;

public class TransportUseCase {

    @Inject private FestivalDates festivalDates;
    @Inject private ShuttleRepository transportRepository;
    @Inject private ShuttleDtoMapper shuttleDtoMapper;

    public List<ShuttleDto> getAllDepartures() {
        return shuttleDtoMapper.toDtos(transportRepository.findShuttlesByLocation(Location.EARTH));
    }

    public List<ShuttleDto> getAllArrivals() {
        return shuttleDtoMapper.toDtos(transportRepository.findShuttlesByLocation(Location.ULAVALOGY));
    }

    public List<ShuttleDto> getShuttlesDepartureByDate(LocalDate date) {
        festivalDates.validateEventDate(date);
        return shuttleDtoMapper.toDtos(transportRepository.findShuttlesByDate(Location.EARTH, date));
    }

    public List<ShuttleDto> getShuttlesArrivalByDate(LocalDate date) {
        festivalDates.validateEventDate(date);
        return shuttleDtoMapper.toDtos(transportRepository.findShuttlesByDate(Location.ULAVALOGY, date));
    }
}
