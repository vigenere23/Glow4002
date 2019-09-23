package ca.ulaval.glo4002.booking.domain.pass_ordering.order_types;

import ca.ulaval.glo4002.booking.domain.pass_ordering.orders.PassOrder;
import ca.ulaval.glo4002.booking.domain.pass_ordering.passes.pass_types.NebulaSinglePass;
import ca.ulaval.glo4002.booking.domain.pass_ordering.passes.pass_types.SuperGiantSinglePass;

public class SinglePassOrder extends PassOrder {

	public SinglePassOrder() {
		super();
	}
	
	protected double calculateRebates() {
		double rabais = 0;
		int numberOfSupergiant = 0;
		int numberOfNebula = 0;
		
		for (int i = 0; i < this.passes.size(); i++) {
			if (this.passes.get(i).getClass() == NebulaSinglePass.class) {
				numberOfNebula += 1;
			}
			if (this.passes.get(i).getClass() == SuperGiantSinglePass.class) {
				numberOfSupergiant += 1;
			}
		}
		
		if (numberOfNebula > 3) {
    		rabais += this.totalPrice*.1;
    	};
    	if (numberOfSupergiant >= 5) {
    		rabais += numberOfSupergiant*10000;
    	};
		return rabais;
	}

}
