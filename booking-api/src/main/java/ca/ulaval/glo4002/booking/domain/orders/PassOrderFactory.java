package ca.ulaval.glo4002.booking.domain.orders;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import ca.ulaval.glo4002.booking.api.dtos.orders.PassRequest;
import ca.ulaval.glo4002.booking.domain.exceptions.OutOfSaleDatesException;
import ca.ulaval.glo4002.booking.domain.festivals.FestivalDates;
import ca.ulaval.glo4002.booking.domain.passes.Pass;
import ca.ulaval.glo4002.booking.domain.passes.factories.PassFactory;

public class PassOrderFactory {

    private PassFactory passFactory;
    private FestivalDates festival;

    public PassOrderFactory(FestivalDates festivalDates) {
        this.festival = festivalDates;
        
        passFactory = new PassFactory(festivalDates);
    }

    public PassOrder create(OffsetDateTime orderDate, VendorCode vendorCode, PassRequest passRequest) {
        validateOrderDate(orderDate);

        List<Pass> passes = createPasses(passRequest);
        PassOrder passOrder = new PassOrder(vendorCode, passes);
        return passOrder;
    }

    private List<Pass> createPasses(PassRequest passRequest) {
        validatePassRequest(passRequest);

        List<Pass> passes = new ArrayList<>();

        if (passRequest.eventDates == null || passRequest.eventDates.isEmpty()) {
            passes.add(passFactory.create(passRequest.passOption, passRequest.passCategory, null));
        } else {
            for (LocalDate eventDate : passRequest.eventDates) {
                passes.add(passFactory.create(passRequest.passOption, passRequest.passCategory, eventDate));
            }
        }
        return passes;
    }

    private void validateOrderDate(OffsetDateTime orderDate) {
        if (!festival.isDuringSaleTime(orderDate)) {
            throw new OutOfSaleDatesException(festival.getSaleStartDate(), festival.getSaleEndDate());
        }
    }

    private void validatePassRequest(PassRequest passRequest) {
        if (passRequest == null) {
            throw new IllegalArgumentException("Passes cannot be null");
        }
    }
}
