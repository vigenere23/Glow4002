package ca.ulaval.glo4002.booking.api.resources.passOrder.dto;

import ca.ulaval.glo4002.booking.domain.passes.Pass;
import ca.ulaval.glo4002.booking.domain.passes.PassOption;

public class PassMapper {

    public PassDto toDto(Pass pass) {
        PassDto passDto = createPassDto(pass);
        return (pass.getPassOption() == PassOption.SINGLE_PASS) ? createSinglePassDto(passDto, pass) : passDto;
    }

    private PassDto createPassDto(Pass pass) {
        PassDto passDto = new PassDto();
        passDto.passCategory =  pass.getPassCategory().toString();
        passDto.passNumber = pass.getPassNumber().getValue();
        passDto.passOption = pass.getPassOption().toString();
        return passDto;
    }

    private SinglePassDto createSinglePassDto(PassDto passDto, Pass pass) {
        SinglePassDto singlePassDto = new SinglePassDto(passDto);
        singlePassDto.eventDate = pass.getStartDate();
       return singlePassDto;
    }
}
