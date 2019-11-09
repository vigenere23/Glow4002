package ca.ulaval.glo4002.booking.domain.oxygen;

import java.time.LocalDate;
import java.util.EnumMap;
import java.util.SortedMap;

public class OxygenReserver {

    private OxygenOrderFactory oxygenOrderFactory;

    public OxygenReserver(OxygenOrderFactory oxygenOrderFactory) {
        this.oxygenOrderFactory = oxygenOrderFactory;
    }

    public void reserveOxygen(LocalDate orderDate, OxygenGrade grade, int requiredQuantity, EnumMap<OxygenGrade, OxygenInventory> oxygenInventories, SortedMap<LocalDate, OxygenDateHistory>  history) {
        OxygenInventory oxygenInventory = oxygenInventories.get(grade);

        int quantityRemaining = oxygenInventory.getRemainingQuantity();
        int quantityToOrder = requiredQuantity > quantityRemaining ? requiredQuantity - oxygenInventory.getRemainingQuantity() : 0;

        if (quantityToOrder > 0) {
            oxygenInventory.setRemainingQuantity(0);
            OxygenOrder oxygenOrder = oxygenOrderFactory.create(grade);

            updateOxygenStatus(oxygenOrder, orderDate, grade, quantityToOrder, oxygenInventories, history);
        }
        oxygenInventory.setRemainingQuantity(quantityRemaining - requiredQuantity);
    }

    private void updateOxygenStatus(OxygenOrder oxygenOrder, LocalDate orderDate, OxygenGrade grade, int quantityToOrder, EnumMap<OxygenGrade, OxygenInventory> oxygenInventories, SortedMap<LocalDate, OxygenDateHistory>  history) {
        if (!oxygenOrder.enoughTimeToFabricate(orderDate)) {
            reserveOxygen(orderDate, getLowerGradeOf(grade), quantityToOrder, oxygenInventories, history);
        }
        int updatedOxygenInventory = oxygenOrder.getQuantityToReserve(orderDate, quantityToOrder);
//        oxygenInventories.put(grade, updatedOxygenInventory);
        SortedMap<LocalDate, OxygenDateHistory> updatedHistory = oxygenOrder.updateOxygenHistory(history, orderDate, quantityToOrder);
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
