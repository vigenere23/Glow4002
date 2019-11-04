package ca.ulaval.glo4002.booking.domain.orders;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import ca.ulaval.glo4002.booking.api.dtos.orders.PassRequest;
import ca.ulaval.glo4002.booking.domain.festivals.FestivalDates;
import ca.ulaval.glo4002.booking.domain.passes.Pass;
import ca.ulaval.glo4002.booking.domain.passes.PassFactory;

public class PassOrderFactory {

    private PassFactory passFactory;
    private FestivalDates festivalDates;

    public PassOrderFactory(FestivalDates festivalDates) {
        this.festivalDates = festivalDates;
        passFactory = new PassFactory(festivalDates);
    }

    // TODO #129 unfold passRequest into 3 separated arguments (passOption, passCategory, eventDates)
    public PassOrder create(OffsetDateTime orderDate, VendorCode vendorCode, PassRequest passRequest) {
        festivalDates.validateOrderDate(orderDate);
        List<Pass> passes = createPasses(passRequest);
        PassOrder passOrder = new PassOrder(vendorCode, passes);
        return passOrder;
    }

    private List<Pass> createPasses(PassRequest passRequest) {
        validatePassRequest(passRequest);

        List<Pass> passes = new ArrayList<>();

        if (passRequest.getEventDates() == null || passRequest.getEventDates().isEmpty()) {
            passes.add(passFactory.create(passRequest.getPassOption(), passRequest.getPassCategory()));
        } else {
            for (LocalDate eventDate : passRequest.getEventDates()) {
                passes.add(passFactory.create(passRequest.getPassOption(), passRequest.getPassCategory(), eventDate));
            }
        }
        return passes;
    }

    private void validatePassRequest(PassRequest passRequest) {
        if (passRequest == null) {
            throw new IllegalArgumentException("Passes cannot be null");
        }
    }
}
