package ca.ulaval.glo4002.booking.domain.passOrdering.orders.orderTypes;

import java.time.OffsetDateTime;

import ca.ulaval.glo4002.booking.domain.passOrdering.orders.PassOrder;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.PassCategory;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.factories.PackagePassFactory;

public class PackagePassOrder extends PassOrder {

	public PackagePassOrder(OffsetDateTime orderDate) {
		super(orderDate);
	}

	public PackagePassOrder(OffsetDateTime orderDate, PassCategory passCategory) {
        super(orderDate);
        PackagePassFactory passFactory = new PackagePassFactory();
        passes.add(passFactory.create(passCategory));
    }
}
