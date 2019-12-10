package ca.ulaval.glo4002.booking.interfaces.rest.resources.artists.responses;

import java.util.List;
import java.util.stream.Collectors;

import ca.ulaval.glo4002.booking.application.artists.dtos.ArtistDto;

public class ArtistSortingResponse {  
    public final List<String> artists;

    public ArtistSortingResponse(List<ArtistDto> artists) {
        this.artists = artists
            .stream()
            .map(artistDto -> artistDto.name)
            .collect(Collectors.toList());
    }
}
