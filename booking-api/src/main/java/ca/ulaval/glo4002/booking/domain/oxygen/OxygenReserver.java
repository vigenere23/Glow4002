package ca.ulaval.glo4002.booking.domain.oxygen;

import java.time.LocalDate;
import java.util.EnumMap;
import java.util.SortedMap;

public class OxygenReserver {

    private OxygenOrderFactory oxygenOrderFactory;
    private OxygenInventoryRepository oxygenInventoryRepository;
    private OxygenHistoryRepository oxygenHistoryRepository;

    public OxygenReserver(OxygenOrderFactory oxygenOrderFactory, OxygenInventoryRepository oxygenInventoryRepository, OxygenHistoryRepository oxygenHistoryRepository) {
        this.oxygenOrderFactory = oxygenOrderFactory;
        this.oxygenInventoryRepository = oxygenInventoryRepository;
        this.oxygenHistoryRepository = oxygenHistoryRepository;
    }

    public void reserveOxygen(LocalDate orderDate, OxygenGrade grade, int requiredQuantity) {
        OxygenInventory oxygenInventory = oxygenInventoryRepository.findByGrade(grade);
        SortedMap<LocalDate, OxygenDateHistory> history = oxygenHistoryRepository.findOxygenHistory();

        orderOxygen(orderDate, grade, requiredQuantity, oxygenInventory, history);

        oxygenInventoryRepository.save(oxygenInventory);
        oxygenHistoryRepository.save(history);
    }

    private void orderOxygen(LocalDate orderDate, OxygenGrade grade, int requiredQuantity, OxygenInventory oxygenInventory, SortedMap<LocalDate, OxygenDateHistory> oxygenHistory) {
        int quantityRemaining = oxygenInventory.getRemainingQuantity();
        int quantityToOrder = requiredQuantity > quantityRemaining ? requiredQuantity - oxygenInventory.getRemainingQuantity() : 0;

        if (quantityToOrder > 0) {
            oxygenInventory.setRemainingQuantity(0);
            OxygenOrder oxygenOrder = oxygenOrderFactory.create(grade);

            updateOxygenStatus(oxygenOrder, orderDate, grade, quantityToOrder, oxygenInventory, oxygenHistory);
        }
        oxygenInventory.setRemainingQuantity(quantityRemaining - requiredQuantity);
    }

    private void updateOxygenStatus(OxygenOrder oxygenOrder, LocalDate orderDate, OxygenGrade grade, int quantityToOrder, OxygenInventory oxygenInventory, SortedMap<LocalDate, OxygenDateHistory>  history) {
        if (!oxygenOrder.enoughTimeToFabricate(orderDate)) {
            reserveOxygen(orderDate, getLowerGradeOf(grade), quantityToOrder);
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
