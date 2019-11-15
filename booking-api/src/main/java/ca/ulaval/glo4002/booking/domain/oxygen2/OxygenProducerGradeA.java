package ca.ulaval.glo4002.booking.domain.oxygen2;

import java.time.LocalDate;

public class OxygenProducerGradeA extends OxygenProducer {

	private OxygenHistory oxygenHistory;

	public OxygenProducerGradeA(LocalDate limitDate, OxygenInventory oxygenInventory, OxygenHistory oxygenHistory) {
		super(limitDate, oxygenInventory);
		productionSettings = new OxygenProductionSettingsGradeA();
        this.oxygenHistory = oxygenHistory;
	}

	@Override
	public void produceOxygen(int quantity, LocalDate orderDate) {

	}
}
