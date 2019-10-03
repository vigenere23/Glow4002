package ca.ulaval.glo4002.booking.domain.passOrdering.orders;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import ca.ulaval.glo4002.booking.domain.passOrdering.passes.Pass;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.factories.PassFactory;
import ca.ulaval.glo4002.booking.interfaces.rest.orders.dtos.PassRequest;

public class PassOrderFactory {

    private PassFactory passFactory;

    public PassOrderFactory(OffsetDateTime festivalStart, OffsetDateTime festivalEnd) {
        this.passFactory = new PassFactory(festivalStart, festivalEnd);
    }

    public PassOrder create(OffsetDateTime orderDate, String vendorCode, List<PassRequest> passRequests) {
        List<Pass> passes = createPasses(passRequests);
        PassOrder passOrder = new PassOrder(orderDate, vendorCode, passes);

        return passOrder;
    }

    private List<Pass> createPasses(List<PassRequest> passRequests) {
        if (passRequests == null) {
            throw new IllegalArgumentException("List of passes cannot be null");
        }

        List<Pass> passes = new ArrayList<>();

        for (PassRequest passRequest : passRequests) {
            if (passRequest.eventDates == null || passRequest.eventDates.isEmpty()) {
                passes.add(passFactory.create(passRequest.passOption, passRequest.passCategory, null));
            } else {
                for (OffsetDateTime eventDate : passRequest.eventDates) {
                    passes.add(passFactory.create(passRequest.passOption, passRequest.passCategory, eventDate));
                }
            }
        }

        return passes;
    }
}
