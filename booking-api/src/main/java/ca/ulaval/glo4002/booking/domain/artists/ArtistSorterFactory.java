package ca.ulaval.glo4002.booking.domain.artists;

public class ArtistSorterFactory {

    public ArtistSortingStrategy create(ArtistSortingType artistSortingType) {
        switch (artistSortingType) {
            case LOW_COSTS:
                return new DecreasingPriceSorter();
            case MOST_POPULARITY:
                return new AscendingPopularitySorter();
            default:
                throw new IllegalArgumentException(
                        String.format("No sorting implemented for this %s sorting type.", artistSortingType)
                );
        }
    }
}
