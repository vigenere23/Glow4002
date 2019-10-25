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

    public SortedMap<LocalDate, OxygenDateHistory> updateOxygenHistory(SortedMap<LocalDate, OxygenDateHistory> history, LocalDate orderDate, int quantityOfTanksLacking) {
        OxygenDateHistory orderDateHistory = getOrderDateHistory(orderDate, quantityOfTanksLacking);
        updateHistory(history, orderDateHistory);
        OxygenDateHistory completionDateHistory = getCompletionDateHistory(orderDate, quantityOfTanksLacking);
        updateHistory(history, completionDateHistory);
        return history;
    }

    private OxygenDateHistory getOrderDateHistory(LocalDate orderDate, int quantityOfTanksLacking) {
        OxygenDateHistory oxygenDateHistory = new OxygenDateHistory(orderDate);
        quantityPerFabricationBatch.forEach(
                (historyType, fabricationQuantity) -> oxygenDateHistory.updateQuantity(historyType, getQuantityToFabricate(quantityOfTanksLacking, fabricationQuantity))
        );
        return oxygenDateHistory;
    }

    private OxygenDateHistory getCompletionDateHistory(LocalDate orderDate, int quantityOfTanksLacking) {
        OxygenDateHistory oxygenDateHistory = new OxygenDateHistory(getFabricationCompletionDate(orderDate));
        oxygenDateHistory.updateQuantity(HistoryType.OXYGEN_TANK_MADE, getQuantityToFabricate(quantityOfTanksLacking, tankFabricationQuantity));
        return oxygenDateHistory;
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

    private void updateHistory(SortedMap<LocalDate, OxygenDateHistory> history, OxygenDateHistory oxygenDateHistory) {
        LocalDate date = oxygenDateHistory.getDate();
        if (history.containsKey(date)) {
            history.get(date).updateQuantities(oxygenDateHistory);
        } else {
            history.put(date, oxygenDateHistory);
        }
    }
}
