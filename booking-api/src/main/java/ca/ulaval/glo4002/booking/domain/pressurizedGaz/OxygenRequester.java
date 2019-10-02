package ca.ulaval.glo4002.booking.domain.pressurizedGaz;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import ca.ulaval.glo4002.booking.domain.persistanceInterface.OxygenHistory;
import ca.ulaval.glo4002.booking.domain.persistanceInterface.OxygenInventory;
import ca.ulaval.glo4002.booking.domain.persistanceInterface.OxygenPersistance;

public class OxygenRequester extends OxygenResource {

    private OxygenProducer oxygenProducer;
    private OxygenProductionResults results;
    private OxygenInventory inventory;
    private OxygenHistory history;

    public OxygenRequester(OffsetDateTime limitDeliveryDate, OxygenPersistance oxygenPersistance) {
        this.inventory = oxygenPersistance.getOxygenInventory();
        this.history = oxygenPersistance.getOxygenHistory();
        this.oxygenProducer = new OxygenProducer(limitDeliveryDate);
    }

    public void orderOxygen(OffsetDateTime orderDate, OxygenGrade grade, int quantity) {

        int remainingQuantity = inventory.getOxygenRemaining(grade);

        if (hasToProduce(quantity, remainingQuantity)) {
            initializeResults(orderDate, grade);
            int totalToProduce = quantity - remainingQuantity;
            inventory.setOxygenRemaining(grade, 0);

            oxygenProducer.produceOxygen(grade, totalToProduce, results);

            OxygenGrade gradeProduced = results.gradeProduced;
            inventory.setOxygenInventory(gradeProduced, inventory.getInventoryOfGrade(gradeProduced) + results.quantityTankToAddToInventory);
            inventory.setOxygenRemaining(gradeProduced,
                    inventory.getOxygenRemaining(gradeProduced) + results.quantityTankToAddToRemaining);

            history.updateCreationHistory(results.orderDateHistory.date, results.orderDateHistory);
            history.updateCreationHistory(results.deliveryDateHistory.date, results.deliveryDateHistory);
        } else {
            inventory.setOxygenRemaining(grade, remainingQuantity - quantity);
        }
    }



    private void initializeResults(OffsetDateTime orderDate, OxygenGrade grade) {

        this.results = new OxygenProductionResults();
        HistoryDto orderDateHistory = history.getCreationHistoryPerDate(orderDate);
        this.results.orderDateHistory = orderDateHistory;

        OffsetDateTime deliveryDate = oxygenProducer.getNextAvailableDeliveryDate(orderDate, grade);
        HistoryDto deliveryDateHistory = history.getCreationHistoryPerDate(deliveryDate);
        this.results.deliveryDateHistory = deliveryDateHistory;
    }

    private boolean hasToProduce(int quantityToProduce, int remainingQuantity) {
        return quantityToProduce > remainingQuantity;
    }

    @Override
    public List<InventoryDto> getInventory() {
        return inventory.getCompleteInventory();
    }

    @Override
    public List<HistoryDto> getOxygenHistory() {
        return new ArrayList<>(history.getCreationHistory().values());
    }
}
