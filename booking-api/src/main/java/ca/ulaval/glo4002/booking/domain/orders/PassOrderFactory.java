package ca.ulaval.glo4002.booking.domain.orders;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import ca.ulaval.glo4002.booking.domain.festivals.FestivalDates;
import ca.ulaval.glo4002.booking.domain.orders.orderNumber.OrderNumber;
import ca.ulaval.glo4002.booking.domain.orders.orderNumber.OrderNumberFactory;
import ca.ulaval.glo4002.booking.domain.passes.Pass;
import ca.ulaval.glo4002.booking.domain.passes.PassCategory;
import ca.ulaval.glo4002.booking.domain.passes.PassFactory;
import ca.ulaval.glo4002.booking.domain.passes.PassOption;
import ca.ulaval.glo4002.booking.domain.profit.IncomeSaver;

public class PassOrderFactory {

    private FestivalDates festivalDates;
    private OrderNumberFactory orderNumberFactory;
    private PassFactory passFactory;
    private IncomeSaver incomeSaver;

    public PassOrderFactory(FestivalDates festivalDates, OrderNumberFactory orderNumberFactory, PassFactory passFactory, IncomeSaver incomeSaver) {
        this.festivalDates = festivalDates;
        this.orderNumberFactory = orderNumberFactory;
        this.passFactory = passFactory;
        this.incomeSaver = incomeSaver;
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
        OrderNumber orderNumber = orderNumberFactory.create(vendorCode);
        return new PassOrder(orderNumber, passes, incomeSaver);
    }

    private List<Pass> createPasses(PassOption passOption, PassCategory passCategory, Optional<List<LocalDate>> eventDates) {
        List<Pass> passes = new ArrayList<>();

        if (eventDates.isPresent() && !eventDates.get().isEmpty()) {
            for (LocalDate eventDate : eventDates.get()) {
                passes.add(passFactory.create(passOption, passCategory, Optional.of(eventDate)));
            }
        } else {
            passes.add(passFactory.create(passOption, passCategory, Optional.empty()));
        }
        return passes;
    }
}
