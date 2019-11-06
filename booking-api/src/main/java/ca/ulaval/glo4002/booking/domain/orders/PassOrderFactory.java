package ca.ulaval.glo4002.booking.domain.orders;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import ca.ulaval.glo4002.booking.domain.festivals.FestivalDates;
import ca.ulaval.glo4002.booking.domain.passes.Pass;
import ca.ulaval.glo4002.booking.domain.passes.PassCategory;
import ca.ulaval.glo4002.booking.domain.passes.PassFactory;
import ca.ulaval.glo4002.booking.domain.passes.PassOption;

public class PassOrderFactory {

    private PassFactory passFactory;
    private FestivalDates festivalDates;

    public PassOrderFactory(FestivalDates festivalDates) {
        this.festivalDates = festivalDates;
        passFactory = new PassFactory(festivalDates);
    }

    // TODO #129 unfold passRequest into 3 separated arguments (passOption, passCategory, eventDates)
    public PassOrder create(OffsetDateTime orderDate, VendorCode vendorCode, PassOption passOption, PassCategory passCategory, List<LocalDate> eventDates) {
        festivalDates.validateOrderDate(orderDate);
        List<Pass> passes = createPasses(passOption, passCategory, eventDates);
        PassOrder passOrder = new PassOrder(vendorCode, passes);
        return passOrder;
    }

    private List<Pass> createPasses(PassOption passOption, PassCategory passCategory, List<LocalDate> eventDates) {
        List<Pass> passes = new ArrayList<>();

        if (eventDates == null || eventDates.isEmpty()) {
            passes.add(passFactory.create(passOption, passCategory));
        } else {
            for (LocalDate eventDate : eventDates) {
                passes.add(passFactory.create(passOption, passCategory, eventDate));
            }
        }
        return passes;
    }
}
