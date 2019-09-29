package ca.ulaval.glo4002.booking.domain.passOrdering.orders.orderTypes;

import ca.ulaval.glo4002.booking.domain.passOrdering.orders.PassOrder;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.PassCategory;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.factories.PackagePassFactory;

public class PackagePassOrder extends PassOrder {

	public PackagePassOrder() {
		super();
	}

	public PackagePassOrder(PassCategory passCategory) {
        super();
        PackagePassFactory passFactory = new PackagePassFactory();
        passes.add(passFactory.create(passCategory));

        this.updateTotalPrice();
    }
}
