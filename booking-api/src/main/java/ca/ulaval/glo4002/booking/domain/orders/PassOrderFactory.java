package ca.ulaval.glo4002.booking.domain.orders;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public PassOrder create(
        OffsetDateTime orderDate,
        VendorCode vendorCode,
        PassOption passOption,
        PassCategory passCategory,
        Optional<List<LocalDate>> eventDates
    ) {
        festivalDates.validateOrderDate(orderDate);
        List<Pass> passes = createPasses(passOption, passCategory, eventDates);
        return new PassOrder(vendorCode, passes);
    }

    private List<Pass> createPasses(PassOption passOption, PassCategory passCategory, Optional<List<LocalDate>> eventDates) {
        List<Pass> passes = new ArrayList<>();

        if (eventDates.isPresent() && !eventDates.get().isEmpty()) {
            for (LocalDate eventDate : eventDates.get()) {
                passes.add(passFactory.create(passOption, passCategory, Optional.of(eventDate)));
            }
        } else {
            passes.add(passFactory.create(passOption, passCategory));
        }
        return passes;
    }
}
