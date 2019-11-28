package ca.ulaval.glo4002.booking.application.transport;

import ca.ulaval.glo4002.booking.application.transport.dtos.ShuttleDto2;
import ca.ulaval.glo4002.booking.application.transport.dtos.ShuttleDtoMapper2;
import ca.ulaval.glo4002.booking.domain.dates.FestivalDates;
import ca.ulaval.glo4002.booking.domain.transport2.Direction;
import ca.ulaval.glo4002.booking.domain.transport2.ShuttleRepository;

import java.time.LocalDate;
import java.util.List;

import javax.inject.Inject;

public class TransportUseCase2 {

    @Inject private FestivalDates festivalDates;
    @Inject private ShuttleRepository transportRepository;
    @Inject private ShuttleDtoMapper2 shuttleDtoMapper;

    public List<ShuttleDto2> getAllDepartures() {
        return shuttleDtoMapper.toDtos(transportRepository.findAllByDirection(Direction.DEPARTURE));
    }

    public List<ShuttleDto2> getAllArrivals() {
        return shuttleDtoMapper.toDtos(transportRepository.findAllByDirection(Direction.ARRIVAL));
    }

    public List<ShuttleDto2> getShuttlesDepartureByDate(LocalDate date) {
        festivalDates.validateEventDate(date);
        return shuttleDtoMapper.toDtos(transportRepository.findAllByDirectionAndDate(Direction.DEPARTURE, date));
    }

    public List<ShuttleDto2> getShuttlesArrivalByDate(LocalDate date) {
        festivalDates.validateEventDate(date);
        return shuttleDtoMapper.toDtos(transportRepository.findAllByDirectionAndDate(Direction.ARRIVAL, date));
    }
}
