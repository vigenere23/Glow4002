package ca.ulaval.glo4002.booking.domain.pass_ordering.orders;

import java.time.LocalDateTime;
import java.util.List;

import ca.ulaval.glo4002.booking.domain.pass_ordering.orders.order_types.PackagePassOrder;
import ca.ulaval.glo4002.booking.domain.pass_ordering.orders.order_types.SinglePassOrder;
import ca.ulaval.glo4002.booking.domain.pass_ordering.passes.PassCategory;
import ca.ulaval.glo4002.booking.domain.pass_ordering.passes.PassOption;
import ca.ulaval.glo4002.booking.domain.pass_ordering.passes.factories.SinglePassFactory;

public class OrderFactory {

	public PassOrder createOrder(SinglePassFactory passFactory, PassOption passOption, PassCategory passCategory, List<LocalDateTime> eventDate) {
		switch(passOption) {
			case PACKAGE:
				return new PackagePassOrder();
			case SINGLE_PASS:
				return new SinglePassOrder(passFactory, passCategory, eventDate);
			default:
                throw new IllegalArgumentException(
                    String.format("No pass implemented for option %s.", passOption)
                );
		}
		
	}
}
