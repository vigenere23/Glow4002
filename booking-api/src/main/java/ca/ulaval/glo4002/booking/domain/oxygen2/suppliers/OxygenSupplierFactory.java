package ca.ulaval.glo4002.booking.domain.oxygen2.suppliers;

import ca.ulaval.glo4002.booking.domain.oxygen2.history.OxygenHistory;
import ca.ulaval.glo4002.booking.domain.oxygen2.OxygenGrade;
import ca.ulaval.glo4002.booking.domain.oxygen2.OxygenInventory;

public class OxygenSupplierFactory {

	private OxygenInventory oxygenInventory;
	private OxygenHistory oxygenHistory;

	public OxygenSupplierFactory(OxygenInventory oxygenInventory, OxygenHistory oxygenHistory) {
		this.oxygenInventory = oxygenInventory;
		this.oxygenHistory = oxygenHistory;
	}

	public OxygenSupplier create(OxygenGrade oxygenGrade) {
		switch (oxygenGrade) {
		case A:
			return new OxygenGradeAProducer(oxygenInventory, oxygenHistory);
		case B:
			return new OxygenGradeBProducer(oxygenInventory, oxygenHistory);
		case E:
			return new OxygenGradeEBuyer(oxygenInventory, oxygenHistory);
		default:
			throw new IllegalArgumentException("no orderer for grade X");
		}
	}
}
