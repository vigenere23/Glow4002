package ca.ulaval.glo4002.booking.domain.pass_ordering.orders;

import ca.ulaval.glo4002.booking.domain.pass_ordering.order_types.PackagePassOrder;
import ca.ulaval.glo4002.booking.domain.pass_ordering.order_types.SinglePassOrder;
import ca.ulaval.glo4002.booking.domain.pass_ordering.passes.PassOption;

public class OrderFactory {

	public PassOrder newOrder(PassOption passOption) {
		switch(passOption) {
			case PACKAGE:
				return new PackagePassOrder();
			case SINGLE_PASS:
				return new SinglePassOrder();
			default:
                throw new IllegalArgumentException(
                    String.format("No pass implemented for option %s.", passOption)
                );
		}
		
	}
}
