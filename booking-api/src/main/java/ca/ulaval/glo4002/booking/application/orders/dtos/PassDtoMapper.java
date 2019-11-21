package ca.ulaval.glo4002.booking.application.orders.dtos;

import java.util.List;
import java.util.stream.Collectors;

import ca.ulaval.glo4002.booking.domain.passes.Pass;
import ca.ulaval.glo4002.booking.domain.passes.PassOption;

public class PassDtoMapper {

    public List<PassDto> toDtos(List<Pass> passes) {
        return passes
            .stream()
            .map(pass -> toDto(pass))
            .collect(Collectors.toList());
    }

    public PassDto toDto(Pass pass) {
        return pass.getPassOption() == PassOption.SINGLE_PASS
            ? createSinglePassDto(pass)
            : createPassDto(pass);
    }

    private SinglePassDto createSinglePassDto(Pass pass) {
        return new SinglePassDto(pass.getPassNumber(), pass.getPassCategory(), pass.getPassOption(), pass.getStartDate());
    }

    private PassDto createPassDto(Pass pass) {
        return new PassDto(pass.getPassNumber(), pass.getPassCategory(), pass.getPassOption());
    }
}
