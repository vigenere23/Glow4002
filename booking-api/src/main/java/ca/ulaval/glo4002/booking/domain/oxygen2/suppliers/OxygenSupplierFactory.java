package ca.ulaval.glo4002.booking.domain.oxygen2.suppliers;

import ca.ulaval.glo4002.booking.domain.oxygen2.history.OxygenHistory;
import ca.ulaval.glo4002.booking.domain.finance.ProfitCalculator;
import ca.ulaval.glo4002.booking.domain.oxygen2.OxygenGrade;
import ca.ulaval.glo4002.booking.domain.oxygen2.inventory.OxygenInventory;

public class OxygenSupplierFactory {

	private OxygenInventory oxygenInventory;
	private OxygenHistory oxygenHistory;
	private ProfitCalculator profitCalculator;

	public OxygenSupplierFactory(OxygenInventory oxygenInventory, OxygenHistory oxygenHistory, ProfitCalculator profitCalculator) {
		this.oxygenInventory = oxygenInventory;
		this.oxygenHistory = oxygenHistory;
		this.profitCalculator = profitCalculator;
	}

	public OxygenSupplier create(OxygenGrade oxygenGrade) {
		switch (oxygenGrade) {
		case A:
			return new OxygenGradeAProducer(oxygenInventory, oxygenHistory, profitCalculator);
		case B:
			return new OxygenGradeBProducer(oxygenInventory, oxygenHistory, profitCalculator);
		case E:
			return new OxygenGradeEBuyer(oxygenInventory, oxygenHistory, profitCalculator);
		default:
			throw new IllegalArgumentException(
				String.format("No orderer exists for oxygen grade %s", oxygenGrade.toString())
			);
		}
	}
}
