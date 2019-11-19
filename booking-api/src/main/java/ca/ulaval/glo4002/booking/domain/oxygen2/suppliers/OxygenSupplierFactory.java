package ca.ulaval.glo4002.booking.domain.oxygen2.suppliers;

import ca.ulaval.glo4002.booking.domain.oxygen2.history.OxygenHistory;
import ca.ulaval.glo4002.booking.domain.finance.ProfitCalculator;
import ca.ulaval.glo4002.booking.domain.oxygen2.OxygenGrade;

public class OxygenSupplierFactory {

	private OxygenHistory oxygenHistory;
	private ProfitCalculator profitCalculator;

	public OxygenSupplierFactory(OxygenHistory oxygenHistory, ProfitCalculator profitCalculator) {
		this.oxygenHistory = oxygenHistory;
		this.profitCalculator = profitCalculator;
	}

	public OxygenSupplier create(OxygenGrade oxygenGrade) {
		switch (oxygenGrade) {
		case A:
			return new OxygenGradeAProducer(oxygenHistory, profitCalculator);
		case B:
			return new OxygenGradeBProducer(oxygenHistory, profitCalculator);
		case E:
			return new OxygenGradeEBuyer(oxygenHistory, profitCalculator);
		default:
			throw new IllegalArgumentException(
				String.format("No orderer exists for oxygen grade %s", oxygenGrade.toString())
			);
		}
	}
}
