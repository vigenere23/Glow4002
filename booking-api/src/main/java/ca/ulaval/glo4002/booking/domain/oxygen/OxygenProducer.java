package ca.ulaval.glo4002.booking.domain.oxygen;

import ca.ulaval.glo4002.booking.domain.exceptions.NotEnoughTimeException;

import java.time.LocalDate;
import java.util.SortedMap;

public class OxygenProducer {
    private OxygenFactory oxygenFactory;

    public OxygenProducer(OxygenFactory oxygenFactory) {
        this.oxygenFactory = oxygenFactory;
    }

    public Oxygen orderOxygen(LocalDate orderDate, OxygenGrade grade, int requiredQuantity, int totalQuantity, int storageQuantity, SortedMap<LocalDate, OxygenHistory>  history) throws NotEnoughTimeException {
        Oxygen oxygen = oxygenFactory.create(grade, totalQuantity, storageQuantity);
        oxygen.adjustInventory(orderDate, requiredQuantity);
        oxygen.updateOxygenHistory(history, orderDate, requiredQuantity);

        return oxygen;
    }
}
