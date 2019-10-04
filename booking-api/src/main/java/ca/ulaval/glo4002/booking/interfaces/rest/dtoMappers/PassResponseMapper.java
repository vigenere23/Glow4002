package ca.ulaval.glo4002.booking.interfaces.rest.dtoMappers;

import ca.ulaval.glo4002.booking.domain.passOrdering.passes.Pass;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.passTypes.SinglePass;
import ca.ulaval.glo4002.booking.interfaces.rest.dtos.orders.PassResponse;
import ca.ulaval.glo4002.booking.interfaces.rest.dtos.orders.SinglePassResponse;

public class PassResponseMapper {

    public PassResponse getPassResponse(Pass pass) {
        if (pass instanceof SinglePass) {
            SinglePass singlePass = (SinglePass) pass;
            return new SinglePassResponse(
                singlePass.getPassNumber().getId(),
                singlePass.getPassOption(),
                singlePass.getPassCategory(),
                singlePass.getEventDate()
            );
        }
        return new PassResponse(pass.getPassNumber().getId(), pass.getPassOption(), pass.getPassCategory());
    }
}
