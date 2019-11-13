package ca.ulaval.glo4002.booking.domain.orders;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import ca.ulaval.glo4002.booking.domain.festivals.FestivalDates;
import ca.ulaval.glo4002.booking.domain.orders.discounts.NebulaSinglePassDiscount;
import ca.ulaval.glo4002.booking.domain.orders.discounts.OrderDiscount;
import ca.ulaval.glo4002.booking.domain.orders.discounts.OrderDiscountFactory;
import ca.ulaval.glo4002.booking.domain.orders.discounts.SupergiantSinglePassDiscount;
import ca.ulaval.glo4002.booking.domain.passes.Pass;
import ca.ulaval.glo4002.booking.domain.passes.PassCategory;
import ca.ulaval.glo4002.booking.domain.passes.PassFactory;
import ca.ulaval.glo4002.booking.domain.passes.PassOption;
import ca.ulaval.glo4002.booking.domain.profit.IncomeSaver;

public class PassOrderFactory {

    private PassFactory passFactory;
    private FestivalDates festivalDates;
    private OrderDiscountFactory orderDiscountFactory;
    private IncomeSaver incomeSaver;
    private OrderNumberFactory orderNumberFactory;
    public PassOrderFactory(FestivalDates festivalDates, PassFactory passFactory, OrderDiscountFactory orderDiscountFactory, IncomeSaver incomeSaver,  OrderNumberFactory orderNumberFactory,) {
        this.festivalDates = festivalDates;
        this.orderNumberFactory = orderNumberFactory;
        this.passFactory = passFactory;
        this.orderDiscountFactory = orderDiscountFactory;
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
        OrderDiscount orderDiscount = orderDiscountFactory.fromMultipleDiscounts(
            new SupergiantSinglePassDiscount(), new NebulaSinglePassDiscount()
        );
        return new PassOrder(orderNumber, passes, orderDiscount, incomeSaver);
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
