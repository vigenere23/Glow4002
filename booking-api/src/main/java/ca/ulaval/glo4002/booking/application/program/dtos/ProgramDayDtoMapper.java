package ca.ulaval.glo4002.booking.application.program.dtos;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import javax.inject.Inject;

import ca.ulaval.glo4002.booking.interfaces.rest.resources.program.requests.ProgramDayRequest;
import ca.ulaval.glo4002.booking.domain.artists.Artist;
import ca.ulaval.glo4002.booking.domain.exceptions.InvalidProgramException;
import ca.ulaval.glo4002.booking.domain.passes.PassRepository;
import ca.ulaval.glo4002.booking.domain.program.ProgramDay;

public class ProgramDayDtoMapper {

    @Inject private PassRepository passRepository;

    public List<ProgramDay> fromDtoAndArtists(List<ProgramDayRequest> programDayRequests, List<Artist> artists) {
        return programDayRequests
            .stream()
            .map(programDayRequest -> fromRequestAndArtist(programDayRequest, getArtistByName(artists, programDayRequest.artist)))
            .collect(Collectors.toList());
    }

    public ProgramDay fromRequestAndArtist(ProgramDayRequest programDayRequest, Artist artist) {
        int numberOfAttendees = passRepository.findAttendingAtDate(programDayRequest.eventDate).size();
        return new ProgramDay(programDayRequest.activity, artist, programDayRequest.eventDate, numberOfAttendees);
    }

    private Artist getArtistByName(List<Artist> artists, String artistName) {
        try {
            return artists
                .stream()
                .filter(artist -> artist.getName().equals(artistName))
                .findFirst()
                .get();
        }
        catch (NoSuchElementException exception) {
            throw new InvalidProgramException();
        }
    }
}
