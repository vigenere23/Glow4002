package ca.ulaval.glo4002.booking.context.application.artists;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import ca.ulaval.glo4002.booking.application.artists.ArtistSortingUseCase;
import ca.ulaval.glo4002.booking.application.artists.dtos.ArtistDtoMapper;
import ca.ulaval.glo4002.booking.domain.artists.ArtistSorterFactory;

public class ArtistsContext extends AbstractBinder {

    @Override
    protected void configure() {
        bindAsContract(ArtistDtoMapper.class);
        bindAsContract(ArtistSorterFactory.class);
        bindAsContract(ArtistSortingUseCase.class);
    }
}
