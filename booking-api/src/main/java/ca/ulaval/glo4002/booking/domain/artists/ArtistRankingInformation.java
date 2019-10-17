package ca.ulaval.glo4002.booking.domain.artists;

public class ArtistRankingInformation {
    private String artistName;
    private int popularity;
    private float price;

    public ArtistRankingInformation(String artistName, int popularity, float price) {
        this.artistName = artistName;
        this.popularity = popularity;
        this.price = price;
    }

    public String getArtistName() {
        return artistName;
    }

    public int getPopularity() {
        return popularity;
    }

    public float getPrice() {
        return price;
    }
}
