package ca.ulaval.glo4002.booking.domain.passOrdering.orders.orderTypes;

import java.math.RoundingMode;
import java.time.OffsetDateTime;
import java.util.List;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import ca.ulaval.glo4002.booking.domain.passOrdering.passes.Pass;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.PassCategory;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.factories.SinglePassFactory;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.passTypes.NebulaSinglePass;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.passTypes.SupergiantSinglePass;
import ca.ulaval.glo4002.booking.domain.passOrdering.orders.PassOrder;

public class SinglePassOrder extends PassOrder {
	
	public SinglePassOrder() {
		super();
	}

	public SinglePassOrder(PassCategory passCategory, List<OffsetDateTime> eventDates) {
		this();
		SinglePassFactory passFactory = new SinglePassFactory();
        for (OffsetDateTime eventDate : eventDates) {
            passes.add(passFactory.create(passCategory, eventDate));
        }
        this.updateTotalPrice();
    }
	
	protected Money calculateRebates() {
		Money rabais = Money.zero(CurrencyUnit.CAD);
		int numberOfSupergiant = 0;
		int numberOfNebula = 0;
		
		for (Pass pass : this.passes) {
			if (pass.getClass().equals(NebulaSinglePass.class)) {
				numberOfNebula += 1;
			}
			if (pass.getClass().equals(SupergiantSinglePass.class)) {
				numberOfSupergiant += 1;
			}
		}
		
		if (numberOfNebula > 3) {
			rabais = this.totalPrice.multipliedBy(0.1, RoundingMode.UNNECESSARY);
    	};
    	if (numberOfSupergiant >= 5) {
    		rabais = Money.parse("CAD " + Integer.toString(numberOfSupergiant*10000));
    	};
		return rabais;
	}

}
