package ca.ulaval.glo4002.booking.domain.oxygen.suppliers;

import ca.ulaval.glo4002.booking.domain.profit.OutcomeSaver;

import javax.inject.Inject;

import ca.ulaval.glo4002.booking.domain.oxygen.OxygenGrade;
import ca.ulaval.glo4002.booking.domain.oxygen.history.OxygenHistoryRepository;

public class OxygenSupplierFactory {

	@Inject private OxygenHistoryRepository oxygenHistory;
	@Inject private OutcomeSaver outcomeSaver;

	public OxygenSupplier create(OxygenGrade oxygenGrade) {
		switch (oxygenGrade) {
		case A:
			return new OxygenGradeAProducer(oxygenHistory, outcomeSaver);
		case B:
			return new OxygenGradeBProducer(oxygenHistory, outcomeSaver);
		case E:
			return new OxygenGradeEBuyer(oxygenHistory, outcomeSaver);
		default:
			throw new IllegalArgumentException(
				String.format("No orderer exists for oxygen grade %s", oxygenGrade.toString())
			);
		}
	}
}
