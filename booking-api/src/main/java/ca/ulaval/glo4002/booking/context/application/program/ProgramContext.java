package ca.ulaval.glo4002.booking.context.application.program;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import ca.ulaval.glo4002.booking.application.program.ProgramUseCase;
import ca.ulaval.glo4002.booking.application.program.dtos.ProgramDayDtoMapper;
import ca.ulaval.glo4002.booking.domain.program.ProgramValidator;

public class ProgramContext extends AbstractBinder {

    @Override
    protected void configure() {
        bindAsContract(ProgramValidator.class);
        bindAsContract(ProgramDayDtoMapper.class);
        bindAsContract(ProgramUseCase.class);
    }
}
