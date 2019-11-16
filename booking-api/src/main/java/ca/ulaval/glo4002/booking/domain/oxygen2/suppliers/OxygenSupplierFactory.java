package ca.ulaval.glo4002.booking.domain.oxygen2.suppliers;

import ca.ulaval.glo4002.booking.domain.oxygen2.history.OxygenHistory;
import ca.ulaval.glo4002.booking.domain.oxygen2.OxygenInventory;
import ca.ulaval.glo4002.booking.domain.oxygen2.settings.OxygenOrderSettings;

public class OxygenSupplierFactory {

	private OxygenInventory oxygenInventory;
	private OxygenHistory oxygenHistory;

	public OxygenSupplierFactory(OxygenInventory oxygenInventory, OxygenHistory oxygenHistory) {
		this.oxygenInventory = oxygenInventory;
		this.oxygenHistory = oxygenHistory;
	}

	public OxygenSupplier create(OxygenOrderSettings oxygenOrderingSettings) {
		switch (oxygenOrderingSettings.getGrade()) {
		case A:
			return new OxygenGradeAProducer(oxygenInventory, oxygenHistory, oxygenOrderingSettings);
		case B:
			return new OxygenGradeBProducer(oxygenInventory, oxygenHistory, oxygenOrderingSettings);
		case E:
			return new OxygenGradeEBuyer(oxygenInventory, oxygenHistory, oxygenOrderingSettings);
		default:
			throw new IllegalArgumentException("no orderer for grade X");
		}
	}
}
