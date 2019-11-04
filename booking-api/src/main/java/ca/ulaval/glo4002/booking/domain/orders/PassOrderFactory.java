package ca.ulaval.glo4002.booking.domain.orders;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import ca.ulaval.glo4002.booking.api.dtos.orders.PassRequest;
import ca.ulaval.glo4002.booking.domain.festivals.Glow4002;
import ca.ulaval.glo4002.booking.domain.passes.Pass;
import ca.ulaval.glo4002.booking.domain.passes.PassFactory;

public class PassOrderFactory {

    private PassFactory passFactory;
    private Glow4002 festival;

    // TODO #129 inject the PassFactory
    public PassOrderFactory(Glow4002 festival) {
        this.festival = festival;
        passFactory = new PassFactory(festival);
    }

    // TODO #129 unfold passRequest into 3 separated arguments (passOption, passCategory, eventDates)
    public PassOrder create(OffsetDateTime orderDate, VendorCode vendorCode, PassRequest passRequest) {
        festival.validateOrderDate(orderDate);
        List<Pass> passes = createPasses(passRequest);
        PassOrder passOrder = new PassOrder(vendorCode, passes);
        return passOrder;
    }

    private List<Pass> createPasses(PassRequest passRequest) {
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
}
