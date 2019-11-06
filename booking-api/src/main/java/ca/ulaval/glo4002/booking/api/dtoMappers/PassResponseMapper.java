package ca.ulaval.glo4002.booking.api.dtoMappers;

import ca.ulaval.glo4002.booking.api.dtos.orders.PassResponse;
import ca.ulaval.glo4002.booking.api.dtos.orders.SinglePassResponse;
import ca.ulaval.glo4002.booking.domain.passes.Pass;
import ca.ulaval.glo4002.booking.domain.passes.PassOption;

public class PassResponseMapper {

    public PassResponse getPassResponse(Pass pass) {
        if (pass.getPassOption() == PassOption.SINGLE_PASS) {
            return new SinglePassResponse(
                pass.getPassNumber().getValue(),
                pass.getPassOption(),
                pass.getPassCategory(),
                pass.getStartDate()
            );
        }
        return new PassResponse(pass.getPassNumber().getValue(), pass.getPassOption(), pass.getPassCategory());
    }
}
