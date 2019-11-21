package ca.ulaval.glo4002.booking;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

import ca.ulaval.glo4002.booking.application.artists.ArtistRankingUseCase;
import ca.ulaval.glo4002.booking.application.orders.PassOrderUseCase;
import ca.ulaval.glo4002.booking.application.oxygen.OxygenUseCase;
import ca.ulaval.glo4002.booking.application.profit.ProfitUseCase;
import ca.ulaval.glo4002.booking.application.program.ProgramUseCase;
import ca.ulaval.glo4002.booking.application.transport.TransportUseCase;

public class ResourceConfiguration extends ResourceConfig {

    public ResourceConfiguration(
            ProfitUseCase profitUseCase,
            PassOrderUseCase passOrderUseCase,
            TransportUseCase transportUseCase,
            OxygenUseCase oxygenUseCase,
            ArtistRankingUseCase artistRankingUseCase,
            ProgramUseCase programUseCase
    ) {
        register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(passOrderUseCase).to(PassOrderUseCase.class);
                bind(transportUseCase).to(TransportUseCase.class);
                bind(oxygenUseCase).to(OxygenUseCase.class);
                bind(artistRankingUseCase).to(ArtistRankingUseCase.class);
                bind(profitUseCase).to(ProfitUseCase.class);
                bind(programUseCase).to(ProgramUseCase.class);
            }
        });
    }
}
