package ca.ulaval.glo4002.booking.domain.passOrdering.orders;

import java.time.OffsetDateTime;
import java.util.List;

import ca.ulaval.glo4002.booking.domain.passOrdering.passes.PassCategory;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.factories.SinglePassFactory;
import ca.ulaval.glo4002.booking.domain.passOrdering.orders.order_types.PackagePassOrder;
import ca.ulaval.glo4002.booking.domain.passOrdering.orders.order_types.SinglePassOrder;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.PassOption;

public class OrderFactory {

	public PassOrder createOrder(SinglePassFactory passFactory, PassOption passOption, PassCategory passCategory, List<OffsetDateTime> eventDates) {
		switch(passOption) {
			case PACKAGE:
				return new PackagePassOrder();
			case SINGLE_PASS:
				return new SinglePassOrder(passFactory, passCategory, eventDates);
			default:
                throw new IllegalArgumentException(
                    String.format("No pass implemented for option %s.", passOption)
                );
		}
		
	}
}
