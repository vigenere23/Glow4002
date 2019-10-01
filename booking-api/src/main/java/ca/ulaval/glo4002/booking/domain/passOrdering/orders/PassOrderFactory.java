package ca.ulaval.glo4002.booking.domain.passOrdering.orders;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import ca.ulaval.glo4002.booking.domain.passOrdering.passes.Pass;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.factories.PassFactory;
import ca.ulaval.glo4002.booking.interfaces.dtos.PassDTO;

public class PassOrderFactory {

    private PassFactory passFactory;

    public PassOrderFactory(OffsetDateTime festivalStart, OffsetDateTime festivalEnd) {
        this.passFactory = new PassFactory(festivalStart, festivalEnd);
    }

    public PassOrder create(OffsetDateTime orderDate, String vendorCode, List<PassDTO> passDTOs) {
        List<Pass> passes = createPasses(passDTOs);
        PassOrder passOrder = new PassOrder(orderDate, vendorCode, passes);

        return passOrder;
    }

    private List<Pass> createPasses(List<PassDTO> passDTOs) {
        List<Pass> passes = new ArrayList<>();

        for (PassDTO passDTO : passDTOs) {
            if (passDTO.eventDates == null) {
                passes.add(passFactory.create(passDTO.passOption, passDTO.passCategory, null));
            } else {
                for (OffsetDateTime eventDate : passDTO.eventDates) {
                    passes.add(passFactory.create(passDTO.passOption, passDTO.passCategory, eventDate));
                }
            }
        }

        return passes;
    }
}
