package ca.ulaval.glo4002.booking.domain.oxygen;

import java.time.LocalDate;
import java.util.EnumMap;
import java.util.SortedMap;

public class OxygenReserver {

    private OxygenFactory oxygenFactory;

    public OxygenReserver(OxygenFactory oxygenFactory) {
        this.oxygenFactory = oxygenFactory;
    }

    public void orderOxygen(LocalDate orderDate, OxygenGrade grade, int requiredQuantity, EnumMap<OxygenGrade, OxygenInventory> oxygenInventories, SortedMap<LocalDate, OxygenDateHistory>  history) {
        OxygenInventory oxygenInventory = oxygenInventories.get(grade);
        OxygenOrder oxygenOrder = oxygenFactory.create(grade, oxygenInventory);

        boolean adjustInventory = oxygenOrder.adjustInventory(orderDate, requiredQuantity);
        if (!adjustInventory) {
            int quantityToOrder = requiredQuantity - oxygenInventory.getRemainingQuantity();
            oxygenInventory.setRemainingQuantity(0);
            orderOxygen(orderDate, getLowerGradeOf(grade), quantityToOrder, oxygenInventories, history);
        }
        oxygenOrder.updateOxygenHistory(history, orderDate, requiredQuantity);
    }

    private OxygenGrade getLowerGradeOf(OxygenGrade grade) {
        switch (grade) {
            case A:
                return OxygenGrade.B;
            case B:
                return OxygenGrade.E;
            default:
                throw new IllegalArgumentException(String.format("No lower oxygen grade exists for grade %s.", grade));
        }
    }
}
