package ca.ulaval.glo4002.booking.api.dtoMappers;

import ca.ulaval.glo4002.booking.api.dtos.orders.PassResponse;
import ca.ulaval.glo4002.booking.api.dtos.orders.SinglePassResponse;
import ca.ulaval.glo4002.booking.domain.passes.Pass;
import ca.ulaval.glo4002.booking.domain.passes.passTypes.SinglePass;

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