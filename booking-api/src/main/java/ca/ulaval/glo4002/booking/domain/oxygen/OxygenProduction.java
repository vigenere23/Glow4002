package ca.ulaval.glo4002.booking.domain.oxygen;

import java.time.LocalDate;
import java.util.EnumMap;
import java.util.SortedMap;

public class OxygenProduction {
    private int fabricationTimeInDays;
    private int tankFabricationQuantity;
    private EnumMap<HistoryType, Integer> quantityPerFabricationBatch;

    public OxygenProduction(int fabricationTimeInDays, int tankFabricationQuantity, EnumMap<HistoryType, Integer> quantityPerFabricationBatch) {
        this.fabricationTimeInDays = fabricationTimeInDays;
        this.tankFabricationQuantity = tankFabricationQuantity;
        this.quantityPerFabricationBatch = quantityPerFabricationBatch;
    }

    public SortedMap<LocalDate, OxygenHistory> updateOxygenHistory(SortedMap<LocalDate, OxygenHistory> history, LocalDate orderDate, int quantityOfTanksLacking) {
        OxygenHistory orderDateHistory = getOrderDateHistory(orderDate, quantityOfTanksLacking);
        updateHistory(history, orderDateHistory);
        OxygenHistory completionDateHistory = getCompletionDateHistory(orderDate, quantityOfTanksLacking);
        updateHistory(history, completionDateHistory);
        return history;
    }

    private OxygenHistory getOrderDateHistory(LocalDate orderDate, int quantityOfTanksLacking) {
        OxygenHistory oxygenHistory = new OxygenHistory(orderDate);
        quantityPerFabricationBatch.forEach(
                (historyType, fabricationQuantity) -> oxygenHistory.updateQuantity(historyType, getQuantityToFabricate(quantityOfTanksLacking, fabricationQuantity))
        );
        return oxygenHistory;
    }

    private OxygenHistory getCompletionDateHistory(LocalDate orderDate, int quantityOfTanksLacking) {
        OxygenHistory oxygenHistory = new OxygenHistory(getFabricationCompletionDate(orderDate));
        oxygenHistory.updateQuantity(HistoryType.OXYGEN_TANK_MADE, getQuantityToFabricate(quantityOfTanksLacking, tankFabricationQuantity));
        return oxygenHistory;
    }

    public LocalDate getFabricationCompletionDate(LocalDate orderDate) {
        return orderDate.plusDays(fabricationTimeInDays);
    }

    public int getQuantityToFabricate(int quantityOfTanksLacking, int fabricationQuantity) {
        return getQuantityOfFabricationBatchesNeeded(quantityOfTanksLacking) * fabricationQuantity;
    }

    public int getQuantityOfFabricationBatchesNeeded(int quantityLacking) {
        if (quantityLacking % tankFabricationQuantity > 0) {
            return quantityLacking / tankFabricationQuantity + 1;
        }
        return quantityLacking / tankFabricationQuantity;
    }

    private void updateHistory(SortedMap<LocalDate, OxygenHistory> history, OxygenHistory oxygenHistory) {
        LocalDate date = oxygenHistory.getDate();
        if (history.containsKey(date)) {
            history.get(date).updateQuantities(oxygenHistory);
        } else {
            history.put(date, oxygenHistory);
        }
    }
}
