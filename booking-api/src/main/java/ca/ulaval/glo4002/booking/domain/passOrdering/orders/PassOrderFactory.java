package ca.ulaval.glo4002.booking.domain.passOrdering.orders;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import ca.ulaval.glo4002.booking.domain.passOrdering.passes.Pass;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.factories.PassFactory;
import ca.ulaval.glo4002.booking.interfaces.dtos.PassDto;

public class PassOrderFactory {

    private PassFactory passFactory;

    public PassOrderFactory(OffsetDateTime festivalStart, OffsetDateTime festivalEnd) {
        this.passFactory = new PassFactory(festivalStart, festivalEnd);
    }

    public PassOrder create(OffsetDateTime orderDate, String vendorCode, List<PassDto> passDtos) {
        List<Pass> passes = createPasses(passDtos);
        PassOrder passOrder = new PassOrder(orderDate, vendorCode, passes);

        return passOrder;
    }

    private List<Pass> createPasses(List<PassDto> passDtos) {
        if (passDtos == null) {
            throw new IllegalArgumentException("List of pass Dtos cannot be null");
        }

        List<Pass> passes = new ArrayList<>();

        for (PassDto passDto : passDtos) {
            if (passDto.eventDates == null) {
                passes.add(passFactory.create(passDto.passOption, passDto.passCategory, null));
            } else {
                for (OffsetDateTime eventDate : passDto.eventDates) {
                    passes.add(passFactory.create(passDto.passOption, passDto.passCategory, eventDate));
                }
            }
        }

        return passes;
    }
}
