package ca.ulaval.glo4002.booking.domain.artists;

public class ArtistRankingFactory {

    public ArtistRanking createArtistRanking(Ranking rankingType) {
        switch (rankingType) {
            case LOW_COSTS:
                return new DecreasingPriceOrderedArtists();
            case MOST_POPULARITY:
                return new AscendingPopularityOrderedArtists();
            default:
                throw new IllegalArgumentException(
                        String.format("No ranking implemented for this %s ranking type.", rankingType)
                );
        }
    }
}
