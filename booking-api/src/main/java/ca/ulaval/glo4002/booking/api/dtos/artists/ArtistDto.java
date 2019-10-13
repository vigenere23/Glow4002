package ca.ulaval.glo4002.booking.api.dtos.artists;

import java.time.LocalDate;
import java.util.List;

public class ArtistDto {
    public int id;
    public String name;
    public int nbPeople;
    public String musicStyle;
    public float price;
    public int popularityRank;
    public List<LocalDate> availabilities;
}
