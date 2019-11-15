package ca.ulaval.glo4002.booking.domain.oxygen2;

import java.time.LocalDate;

public class OxygenProducerGradeB extends OxygenProducer {

	private OxygenHistory oxygenHistory;

	public OxygenProducerGradeB(LocalDate limitDate, OxygenInventory oxygenInventory, OxygenHistory oxygenHistory) {
		super(limitDate, oxygenInventory);
		productionSettings = new OxygenProductionSettingsGradeB();
        this.oxygenHistory = oxygenHistory;
	}

	@Override
	public void produceOxygen(int quantity, LocalDate orderDate) {
        
	}
}
