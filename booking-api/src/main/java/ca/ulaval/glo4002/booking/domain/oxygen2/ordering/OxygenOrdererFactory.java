package ca.ulaval.glo4002.booking.domain.oxygen2.ordering;

import ca.ulaval.glo4002.booking.domain.oxygen2.OxygenHistory;
import ca.ulaval.glo4002.booking.domain.oxygen2.OxygenInventory;
import ca.ulaval.glo4002.booking.domain.oxygen2.settings.OxygenOrderingSettings;

public class OxygenOrdererFactory {

	private OxygenInventory oxygenInventory;
	private OxygenHistory oxygenHistory;

	public OxygenOrdererFactory(OxygenInventory oxygenInventory, OxygenHistory oxygenHistory) {
		this.oxygenInventory = oxygenInventory;
		this.oxygenHistory = oxygenHistory;
	}

	public OxygenOrderer create(OxygenOrderingSettings oxygenOrderingSettings) {
		switch (oxygenOrderingSettings.getGrade()) {
		case A:
			return new OxygenGradeAOrdering(oxygenInventory, oxygenHistory, oxygenOrderingSettings);
		case B:
			return new OxygenGradeBOrdering(oxygenInventory, oxygenHistory, oxygenOrderingSettings);
		case E:
			return new OxygenGradeEOrdering(oxygenInventory, oxygenHistory, oxygenOrderingSettings);
		default:
			throw new IllegalArgumentException("no orderer for grade X");
		}
	}
}
