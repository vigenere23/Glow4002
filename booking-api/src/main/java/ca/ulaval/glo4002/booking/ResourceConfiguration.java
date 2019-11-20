package ca.ulaval.glo4002.booking;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

import ca.ulaval.glo4002.booking.api.resources.program.dto.ProgramMapper;
import ca.ulaval.glo4002.booking.application.orders.PassOrderUseCase;
import ca.ulaval.glo4002.booking.application.use_cases.ArtistRankingUseCase;
import ca.ulaval.glo4002.booking.application.oxygen.OxygenUseCase;
import ca.ulaval.glo4002.booking.application.transport.TransportUseCase;
import ca.ulaval.glo4002.booking.application.use_cases.ProfitUseCase;
import ca.ulaval.glo4002.booking.application.use_cases.ProgramUseCase;
import ca.ulaval.glo4002.booking.domain.program.ProgramValidator;

public class ResourceConfiguration extends ResourceConfig {

    public ResourceConfiguration(
            ProfitUseCase profitUseCase,
            PassOrderUseCase passOrderUseCase,
            TransportUseCase transportUseCase,
            OxygenUseCase oxygenUseCase,
            ArtistRankingUseCase artistRankingUseCase,
            ProgramUseCase programUseCase,
            ProgramValidator programValidator,
            ProgramMapper programMapper
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
                bind(programValidator).to(ProgramValidator.class);
                bind(programMapper).to(ProgramMapper.class);
            }
        });
    }
}
