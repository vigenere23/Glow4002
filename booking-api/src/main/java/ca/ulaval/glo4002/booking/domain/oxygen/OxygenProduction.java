package ca.ulaval.glo4002.booking.domain.oxygen;

import java.time.LocalDate;
import java.util.EnumMap;
import java.util.SortedMap;

public class OxygenProduction {
    private LocalDate limitDeliveryDate;
    private int fabricationTimeInDays;
    private int tankFabricationQuantity;
    private EnumMap<HistoryType, Integer> quantityPerFabricationBatch;
    private int quantityOfTanksLacking = 0;

    public OxygenProduction(LocalDate limitDeliveryDate, int fabricationTimeInDays, int tankFabricationQuantity, EnumMap<HistoryType, Integer> quantityPerFabricationBatch) {
        this.limitDeliveryDate = limitDeliveryDate;
        this.fabricationTimeInDays = fabricationTimeInDays;
        this.tankFabricationQuantity = tankFabricationQuantity;
        this.quantityPerFabricationBatch = quantityPerFabricationBatch;
    }

    public void setQuantityOfTanksLacking(int quantityOfTanksLacking) {
        this.quantityOfTanksLacking = quantityOfTanksLacking;
    }


    public SortedMap<LocalDate, OxygenProductionInventory> updateOxygenHistory(SortedMap<LocalDate, OxygenProductionInventory> history, LocalDate orderDate, int requirementQuantity) {
        OxygenProductionInventory orderDateInventory = getOrderDateProductionInventory(orderDate, requirementQuantity);
        OxygenProductionInventory completionDateInventory = getCompletionDateProductionInventory(orderDate, requirementQuantity);
        updateOxygenHistory(history, orderDateInventory, completionDateInventory);
        return history;
    }

    private OxygenProductionInventory getOrderDateProductionInventory(LocalDate orderDate, int requirementQuantity) {
        OxygenProductionInventory oxygenProductionInventory = new OxygenProductionInventory(orderDate);
        quantityPerFabricationBatch.forEach(
                (historyType, fabricationQuantity) -> oxygenProductionInventory.updateQuantity(historyType, getQuantityToFabricate(quantityOfTanksLacking, fabricationQuantity))
        );
        return oxygenProductionInventory;
    }

    private OxygenProductionInventory getCompletionDateProductionInventory(LocalDate orderDate, int requirementQuantity) {
        OxygenProductionInventory oxygenProductionInventory = new OxygenProductionInventory(getFabricationCompletionDate(orderDate));
        oxygenProductionInventory.updateQuantity(HistoryType.OXYGEN_TANK_MADE, getQuantityToFabricate(requirementQuantity));
        return oxygenProductionInventory;
    }

    private int getQuantityToFabricate(int requirementQuantity) {
        return getQuantityOfFabricationBatchesNeeded(quantityOfTanksLacking) * tankFabricationQuantity;
    }

    private int getQuantityToFabricate(int quantityOfTanksLacking, int fabricationQuantity) {
        int quantityOfBatchesToFabricate = getQuantityOfFabricationBatchesNeeded(quantityOfTanksLacking);
        return quantityOfBatchesToFabricate * fabricationQuantity;
    }

    public boolean enoughTimeForFabrication(LocalDate orderDate) {
        LocalDate fabricationCompletionDate = orderDate.plusDays(fabricationTimeInDays);
        return fabricationCompletionDate.isBefore(limitDeliveryDate) || fabricationCompletionDate.equals(limitDeliveryDate);
    }

    public int getQuantityOfFabricationBatchesNeeded(int quantityLacking) {
        if (quantityLacking % tankFabricationQuantity > 0) {
            return quantityLacking / tankFabricationQuantity + 1;
        }
        return quantityLacking / tankFabricationQuantity;
    }

    public LocalDate getFabricationCompletionDate(LocalDate orderDate) {
        return orderDate.plusDays(fabricationTimeInDays);
    }

    private SortedMap<LocalDate, OxygenProductionInventory> updateOxygenHistory(SortedMap<LocalDate, OxygenProductionInventory> history, OxygenProductionInventory orderDateInventory, OxygenProductionInventory completionDateInventory) {
        updateHistoryWithOxygenProductionInventory(history, completionDateInventory);
        updateHistoryWithOxygenProductionInventory(history, orderDateInventory);
        return history;
    }

    private void updateHistoryWithOxygenProductionInventory(SortedMap<LocalDate, OxygenProductionInventory> history, OxygenProductionInventory oxygenProductionInventory) {
        LocalDate date = oxygenProductionInventory.getDate();
        if (history.containsKey(date)) {
            history.get(date).updateQuantities(oxygenProductionInventory);
        } else {
            history.put(date, oxygenProductionInventory);
        }
    }
}
