package ca.ulaval.glo4002.booking.domain.passOrdering.orders.orderTypes;

import java.time.OffsetDateTime;
import java.util.List;

import ca.ulaval.glo4002.booking.domain.passOrdering.passes.PassCategory;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.factories.SinglePassFactory;
import ca.ulaval.glo4002.booking.domain.passOrdering.orders.PassOrder;
import ca.ulaval.glo4002.booking.domain.passOrdering.orders.discounts.NebulaSinglePassDiscount;
import ca.ulaval.glo4002.booking.domain.passOrdering.orders.discounts.SupergiantSinglePassDiscount;

public class SinglePassOrder extends PassOrder {
	
	public SinglePassOrder(OffsetDateTime orderDate) {
		super(orderDate);
		this.orderDiscount = new SupergiantSinglePassDiscount();
		this.orderDiscount.setNextDiscount(new NebulaSinglePassDiscount());
	}

	public SinglePassOrder(OffsetDateTime orderDate, PassCategory passCategory, List<OffsetDateTime> eventDates) {
		this(orderDate);
		SinglePassFactory passFactory = new SinglePassFactory();
        for (OffsetDateTime eventDate : eventDates) {
            passes.add(passFactory.create(passCategory, eventDate));
        }
    }
}
