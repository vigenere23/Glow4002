package ca.ulaval.glo4002.booking.interfaces.rest.resources.orders.responses;

import java.util.List;
import java.util.stream.Collectors;

import ca.ulaval.glo4002.booking.application.orders.dtos.PassOrderDto;
import ca.ulaval.glo4002.booking.domain.passes.PassOption;

public class PassOrderResponse {

    public final float orderPrice;
    public final List<PassResponse> passes;

    public PassOrderResponse(PassOrderDto passOrderDto) {
        this.orderPrice = passOrderDto.price.getRoundedAmountFromCurrencyScale();
        this.passes = passOrderDto.passes
            .stream()
            .map(passDto -> {
                return passDto.passOption == PassOption.SINGLE_PASS
                    ? new SinglePassResponse(passDto)
                    : new PassResponse(passDto);
            })
            .collect(Collectors.toList());
    }
}
