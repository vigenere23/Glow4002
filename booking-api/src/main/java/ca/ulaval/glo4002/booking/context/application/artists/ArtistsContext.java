package ca.ulaval.glo4002.booking.context.application.artists;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import ca.ulaval.glo4002.booking.application.artists.ArtistRankingUseCase;
import ca.ulaval.glo4002.booking.domain.artists.ArtistRankingFactory;

public class ArtistsContext extends AbstractBinder {

    @Override
    protected void configure() {
        bindAsContract(ArtistRankingFactory.class);
        bindAsContract(ArtistRankingUseCase.class);
    }
}
