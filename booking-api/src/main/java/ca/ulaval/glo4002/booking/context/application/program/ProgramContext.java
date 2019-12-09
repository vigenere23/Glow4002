package ca.ulaval.glo4002.booking.context.application.program;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import ca.ulaval.glo4002.booking.application.program.ProgramUseCase;
import ca.ulaval.glo4002.booking.application.program.dtos.ProgramDayDtoMapper;
import ca.ulaval.glo4002.booking.application.program.validators.ArtistsNamesValidator;
import ca.ulaval.glo4002.booking.application.program.validators.ArtistsUniquenessValidator;
import ca.ulaval.glo4002.booking.application.program.validators.EventDatesValidator;
import ca.ulaval.glo4002.booking.application.program.validators.ProgramDayRequestValidator;
import ca.ulaval.glo4002.booking.application.program.validators.ProgramDayRequestValidatorContainer;
import ca.ulaval.glo4002.booking.domain.dates.Glow4002Dates;
import ca.ulaval.glo4002.booking.domain.program.ProgramDayFactory;

public class ProgramContext extends AbstractBinder {

    private Glow4002Dates glow4002Dates;

    public ProgramContext(Glow4002Dates glow4002Dates) {
        this.glow4002Dates = glow4002Dates;
    }

    @Override
    protected void configure() {
        ProgramDayRequestValidatorContainer validatorContainer = new ProgramDayRequestValidatorContainer();
        validatorContainer.addValidator(new EventDatesValidator(glow4002Dates));
        validatorContainer.addValidator(new ArtistsNamesValidator());
        validatorContainer.addValidator(new ArtistsUniquenessValidator());
        bind(validatorContainer).to(ProgramDayRequestValidator.class);
        
        bindAsContract(ProgramDayFactory.class);
        bindAsContract(ProgramDayDtoMapper.class);
        bindAsContract(ProgramUseCase.class);
    }
}
