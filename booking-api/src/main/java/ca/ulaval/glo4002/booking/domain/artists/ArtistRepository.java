package ca.ulaval.glo4002.booking.domain.artists;

import java.util.List;

public interface ArtistRepository {
    public List<Artist> findAll();
    public Artist findByName(String name);
}
