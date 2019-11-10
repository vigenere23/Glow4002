package ca.ulaval.glo4002.booking.domain.oxygen;

import java.time.LocalDate;
import java.util.SortedMap;

public class OxygenReserver {

    private OxygenFactory oxygenFactory;
    private OxygenInventoryRepository oxygenInventoryRepository;
    private OxygenHistoryRepository oxygenHistoryRepository;

    public OxygenReserver(OxygenFactory oxygenFactory, OxygenInventoryRepository oxygenInventoryRepository, OxygenHistoryRepository oxygenHistoryRepository) {
        this.oxygenFactory = oxygenFactory;
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
        Oxygen oxygen = oxygenFactory.create(grade, oxygenInventory);
        boolean adjustInventory = oxygen.adjustInventory(orderDate, requiredQuantity);
        if (!adjustInventory) {
            int quantityToOrder = requiredQuantity - oxygenInventory.getRemainingQuantity();
            oxygenInventory.setRemainingQuantity(0);
            reserveOxygen(orderDate, getLowerGradeOf(grade), quantityToOrder);
        }
        oxygen.updateOxygenHistory(oxygenHistory, orderDate, requiredQuantity);
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
