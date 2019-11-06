package ca.ulaval.glo4002.booking;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

import ca.ulaval.glo4002.booking.application.ArtistRankingUseCase;
import ca.ulaval.glo4002.booking.application.OxygenUseCase;
import ca.ulaval.glo4002.booking.application.PassOrderUseCase;
import ca.ulaval.glo4002.booking.application.ProgramUseCase;
import ca.ulaval.glo4002.booking.application.TransportUseCase;
import ca.ulaval.glo4002.booking.domain.festivals.FestivalDates;

public class ResourceConfiguration extends ResourceConfig {

    public ResourceConfiguration(
        PassOrderUseCase passOrderUseCase,
        TransportUseCase transportUseCase,
        OxygenUseCase oxygenUseCase,
        ArtistRankingUseCase artistRankingUseCase,
        ProgramUseCase programUseCase,
        FestivalDates festival
    ) {
        register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(passOrderUseCase).to(PassOrderUseCase.class);
                bind(transportUseCase).to(TransportUseCase.class);
                bind(oxygenUseCase).to(OxygenUseCase.class);
                bind(artistRankingUseCase).to(ArtistRankingUseCase.class);
                bind(programUseCase).to(ProgramUseCase.class);
                bind(festival).to(FestivalDates.class);
            }
        });
    }
}
