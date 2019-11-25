package ca.ulaval.glo4002.booking.context.external_apis;

import javax.inject.Singleton;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import ca.ulaval.glo4002.booking.domain.artists.ArtistRepository;
import ca.ulaval.glo4002.booking.infrastructure.external_apis.artists_api.ApiArtist;
import ca.ulaval.glo4002.booking.infrastructure.external_apis.artists_api.ExternalApiArtist;
import ca.ulaval.glo4002.booking.infrastructure.external_apis.artists_api.ExternalApiArtistRepository;
import ca.ulaval.glo4002.booking.infrastructure.external_apis.artists_api.dtos.ArtistInformationMapper;

public class ExternalApisContext extends AbstractBinder {

    @Override
    protected void configure() {
        bindAsContract(ArtistInformationMapper.class);
        bind(ExternalApiArtist.class).to(ApiArtist.class).in(Singleton.class);
        bind(ExternalApiArtistRepository.class).to(ArtistRepository.class).in(Singleton.class);
    }
}
