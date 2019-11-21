package ca.ulaval.glo4002.booking.application.transport;

import ca.ulaval.glo4002.booking.application.transport.dtos.ShuttleDto;
import ca.ulaval.glo4002.booking.application.transport.dtos.ShuttleDtoMapper;
import ca.ulaval.glo4002.booking.domain.festivals.FestivalDates;
import ca.ulaval.glo4002.booking.domain.transport.Location;
import ca.ulaval.glo4002.booking.domain.transport.ShuttleRepository;

import java.time.LocalDate;
import java.util.List;

public class TransportUseCase {

    private FestivalDates festivalDates;
    private ShuttleRepository transportRepository;
    private ShuttleDtoMapper shuttleDtoMapper;

    public TransportUseCase(FestivalDates festival, ShuttleRepository transportRepository, ShuttleDtoMapper shuttleDtoMapper) {
        this.festivalDates = festival;
        this.transportRepository = transportRepository;
        this.shuttleDtoMapper = shuttleDtoMapper;
    }

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
