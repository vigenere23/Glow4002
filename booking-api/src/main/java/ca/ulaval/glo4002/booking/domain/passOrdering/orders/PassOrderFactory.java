package ca.ulaval.glo4002.booking.domain.passOrdering.orders;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import ca.ulaval.glo4002.booking.domain.exceptions.OutOfFestivalDatesException;
import ca.ulaval.glo4002.booking.domain.festivals.Glow4002;
import ca.ulaval.glo4002.booking.domain.passOrdering.OutOfSaleDatesException;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.Pass;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.factories.PassFactory;
import ca.ulaval.glo4002.booking.interfaces.rest.dtos.orders.PassRequest;

public class PassOrderFactory {

    private PassFactory passFactory;
    private Glow4002 festival;

    public PassOrderFactory(Glow4002 festival) {
        this.festival = festival;
        this.passFactory = new PassFactory(festival);
    }

    public PassOrder create(OffsetDateTime orderDate, String vendorCode, List<PassRequest> passRequests) throws OutOfFestivalDatesException, OutOfSaleDatesException {
        validateOrderDate(orderDate);

        List<Pass> passes = createPasses(passRequests);
        PassOrder passOrder = new PassOrder(orderDate, vendorCode, passes);

        return passOrder;
    }

    private List<Pass> createPasses(List<PassRequest> passRequests) throws OutOfFestivalDatesException {
        validatePassRequests(passRequests);

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

    private void validateOrderDate(OffsetDateTime orderDate) throws OutOfSaleDatesException {
        if (!this.festival.isDuringSaleTime(orderDate)) {
            throw new OutOfSaleDatesException(this.festival.getSaleStartDate(), this.festival.getSaleEndDate());
        }
    }

    private void validatePassRequests(List<PassRequest> passRequests) {
        if (passRequests == null) {
            throw new IllegalArgumentException("List of passes cannot be null");
        }
    }
}
