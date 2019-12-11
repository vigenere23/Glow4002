package ca.ulaval.glo4002.booking.application.orders.dtos;

import java.util.List;
import java.util.stream.Collectors;

import ca.ulaval.glo4002.booking.domain.passes.Pass;

public class PassDtoMapper {

    public List<PassDto> toDtos(List<Pass> passes) {
        return passes
            .stream()
            .map(pass -> toDto(pass))
            .collect(Collectors.toList());
    }

    public PassDto toDto(Pass pass) {
        return new PassDto(
            pass.getPassNumber(),
            pass.getPassCategory(),
            pass.getPassOption(),
            pass.getStartDate(),
            pass.getEndDate()
        );
    }
}
