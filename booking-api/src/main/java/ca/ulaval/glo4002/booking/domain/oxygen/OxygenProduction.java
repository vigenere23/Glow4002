package ca.ulaval.glo4002.booking.domain.oxygen;

import java.time.LocalDate;
import java.util.SortedMap;

public class OxygenProduction {
    private LocalDate limitDeliveryDate;
    private int fabricationTimeInDays;
    private int tankFabricationQuantity;

    public OxygenProduction(LocalDate limitDeliveryDate, int fabricationTimeInDays, int tankFabricationQuantity) {
        this.limitDeliveryDate = limitDeliveryDate;
        this.fabricationTimeInDays = fabricationTimeInDays;
        this.tankFabricationQuantity = tankFabricationQuantity;
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

    public SortedMap<LocalDate, OxygenProductionInventory> updateOxygenHistory(SortedMap<LocalDate, OxygenProductionInventory> history, OxygenProductionInventory orderDateInventory, OxygenProductionInventory completionDateInventory) {
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
