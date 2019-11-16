package ca.ulaval.glo4002.booking.domain.oxygen2.ordering;

import java.time.LocalDate;

import ca.ulaval.glo4002.booking.domain.oxygen2.OxygenHistory;
import ca.ulaval.glo4002.booking.domain.oxygen2.OxygenInventory;
import ca.ulaval.glo4002.booking.domain.oxygen2.settings.OxygenOrderingSettings;

public class OxygenGradeAOrdering extends OxygenOrderer {

    public OxygenGradeAOrdering(OxygenInventory oxygenInventory, OxygenHistory oxygenHistory, OxygenOrderingSettings oxygenOrderingSettings) {
        super(oxygenInventory, oxygenHistory, oxygenOrderingSettings);
    }

    @Override
    public void order(LocalDate orderDate, int minQuantityToProduce) {
        // TODO Auto-generated method stub
    }
}
