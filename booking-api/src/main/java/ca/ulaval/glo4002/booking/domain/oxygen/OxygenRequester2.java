package ca.ulaval.glo4002.booking.domain.oxygen;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OxygenRequester2 extends OxygenExposer {

    private OxygenProducer2 oxygenProducer;
    private OxygenProductionResults results;
    private OxygenHistoryRepository oxygenHistoryRepository;
    private OxygenInventoryRepository oxygenInventoryRepository;

    public OxygenRequester2(LocalDate limitDeliveryDate, OxygenHistoryRepository oxygenHistoryRepository, OxygenInventoryRepository oxygenInventoryRepository) {
        this.oxygenHistoryRepository = oxygenHistoryRepository;
        this.oxygenInventoryRepository = oxygenInventoryRepository;
        oxygenProducer = new OxygenProducer2(limitDeliveryDate);
    }

    @Override
    public List<Inventory> getInventory() {
        return oxygenInventoryRepository.findCompleteInventory();
    }

    @Override
    public List<History> getOxygenHistory() {
        return new ArrayList<>(oxygenHistoryRepository.findCreationHistory().values());
    }

    public void orderOxygen(LocalDate orderDate, OxygenGrade grade, int quantity) {
        int remainingQuantity = oxygenInventoryRepository.findOxygenRemaining(grade);

        if (hasToProduce(quantity, remainingQuantity)) {
            oxygenInventoryRepository.saveOxygenRemaining(grade, 0);
            int totalToProduce = oxygenProducer.calculateTotalToProduce(quantity, remainingQuantity);

            initializeResults(orderDate, grade);
            OxygenGrade gradeProduced = results.gradeProduced;
            if (!gradeProduced.equals(grade)) {
                orderOxygen(orderDate, gradeProduced, totalToProduce);
            } else {
                oxygenProducer.produceOxygen(gradeProduced, totalToProduce, results);

                updateInventory();
                updateHistory();
            }
        } else {
            oxygenInventoryRepository.saveOxygenRemaining(grade, remainingQuantity - quantity);
        }
    }

    private boolean hasToProduce(int quantityToProduce, int remainingQuantity) {
        return quantityToProduce > remainingQuantity;
    }

    private void initializeResults(LocalDate orderDate, OxygenGrade grade) {
        results = new OxygenProductionResults();
        results.orderDateHistory = oxygenHistoryRepository.findCreationHistoryPerDate(orderDate);
        results.gradeProduced = oxygenProducer.getNextGradeToProduce(orderDate, grade);

        LocalDate deliveryDate = oxygenProducer.getFabricationCompletionDate(orderDate, results.gradeProduced);
        results.deliveryDateHistory = oxygenHistoryRepository.findCreationHistoryPerDate(deliveryDate);
    }

    private void updateHistory() {
        oxygenHistoryRepository.saveCreationHistory(results.orderDateHistory.date, results.orderDateHistory);
        oxygenHistoryRepository.saveCreationHistory(results.deliveryDateHistory.date, results.deliveryDateHistory);
    }

    private void updateInventory() {
        OxygenGrade gradeProduced = results.gradeProduced;
        oxygenInventoryRepository.saveOxygenInventory(gradeProduced, oxygenInventoryRepository.findInventoryOfGrade(gradeProduced) + results.quantityTankToAddToInventory);
        oxygenInventoryRepository.saveOxygenRemaining(gradeProduced, results.quantityTankRemaining);
    }
}
