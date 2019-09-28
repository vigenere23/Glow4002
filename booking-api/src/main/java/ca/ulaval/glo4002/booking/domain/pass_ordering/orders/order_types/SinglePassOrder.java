package ca.ulaval.glo4002.booking.domain.pass_ordering.orders.order_types;

import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.joda.money.Money;

import ca.ulaval.glo4002.booking.domain.pass_ordering.orders.PassOrder;
import ca.ulaval.glo4002.booking.domain.pass_ordering.passes.PassCategory;
import ca.ulaval.glo4002.booking.domain.pass_ordering.passes.factories.SinglePassFactory;
import ca.ulaval.glo4002.booking.domain.pass_ordering.passes.pass_types.NebulaSinglePass;
import ca.ulaval.glo4002.booking.domain.pass_ordering.passes.pass_types.SuperGiantSinglePass;

public class SinglePassOrder extends PassOrder {
	
	public SinglePassOrder() {
		super();
	}

	public SinglePassOrder(SinglePassFactory passFactory, PassCategory passCategory, List<LocalDateTime> eventDates) {
        this();
        for (LocalDateTime eventDate : eventDates) {
            passes.add(passFactory.newPass(passCategory, eventDate));
        }
        this.updateTotalPrice();
    }
	
	protected Money calculateRebates() {
		Money rabais = Money.parse("USD 0");
		int numberOfSupergiant = 0;
		int numberOfNebula = 0;
		
		for (int i = 0; i < this.passes.size(); i++) {
			if (this.passes.get(i).getClass().equals(NebulaSinglePass.class)) {
				numberOfNebula += 1;
			}
			if (this.passes.get(i).getClass().equals(SuperGiantSinglePass.class)) {
				numberOfSupergiant += 1;
			}
		}
		
		if (numberOfNebula > 3) {
			rabais = this.totalPrice.multipliedBy(0.1, RoundingMode.UNNECESSARY);
    	};
    	if (numberOfSupergiant >= 5) {
    		rabais = Money.parse("USD " + Integer.toString(numberOfSupergiant*10000));
    	};
		return rabais;
	}

}
