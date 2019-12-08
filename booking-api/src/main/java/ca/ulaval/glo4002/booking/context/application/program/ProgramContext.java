package ca.ulaval.glo4002.booking.context.application.program;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import ca.ulaval.glo4002.booking.application.program.ProgramUseCase;
import ca.ulaval.glo4002.booking.application.program.dtos.ProgramDayDtoMapper;
import ca.ulaval.glo4002.booking.application.program.validators.ProgramDayRequestValidator;
import ca.ulaval.glo4002.booking.domain.program.ProgramDayFactory;

public class ProgramContext extends AbstractBinder {

    @Override
    protected void configure() {
        bindAsContract(ProgramDayFactory.class);
        bindAsContract(ProgramDayDtoMapper.class);
        bindAsContract(ProgramDayRequestValidator.class);
        bindAsContract(ProgramUseCase.class);
    }
}
