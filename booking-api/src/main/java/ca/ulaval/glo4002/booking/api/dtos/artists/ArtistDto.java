package ca.ulaval.glo4002.booking.api.dtos.artists;

import java.time.LocalDate;
import java.util.List;

public class ArtistDto {
    private String name;
    private int popularityRank;
    private float price;
    private int nbPeople;
    private List<LocalDate> availabilities;
}
