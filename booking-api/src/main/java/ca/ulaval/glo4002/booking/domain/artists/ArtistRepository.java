package ca.ulaval.glo4002.booking.domain.artists;

import java.util.List;

public interface ArtistRepository {
    List<Artist> findAll();
    Artist findByName(String name);
}
