package ca.ulaval.glo4002.booking.domain.pressurizedGaz;

import java.time.LocalDate;

public class OxygenTanks {
    private int fabricationQuantity;
    private int fabricationTimeInDays;
    private int totalQuantity = 0;
    private int storageQuantity = 0;

    public OxygenTanks(int fabricationQuantity, int fabricationTimeInDays) {
	this.fabricationQuantity = fabricationQuantity;
	this.fabricationTimeInDays = fabricationTimeInDays;
    }

    public boolean isEnoughTimeForFabrication(LocalDate orderDate, LocalDate limitDeliveryDate) {
	LocalDate fabricationCompletionDate = orderDate.plusDays(fabricationTimeInDays);
	if (fabricationCompletionDate.isAfter(limitDeliveryDate)) {
	    return false;
	}
	return true;
    }

    public void adjustInventory(int requirementQuantity) {
	if (storageQuantity < requirementQuantity) {
	    totalQuantity += getNumberOfFabricationsNeeded(requirementQuantity) * fabricationQuantity;
	    adjustStorageInventory(requirementQuantity);
	} else {
	    removeTanksToStorageInventory(requirementQuantity);
	}
    }

    private int getNumberOfFabricationsNeeded(int requirementQuantity) {
	return (int) Math.ceil((double) requirementQuantity / fabricationQuantity);
    }

    private void adjustStorageInventory(int requirementQuantity) {
	storageQuantity += totalQuantity - requirementQuantity;
    }

    private void removeTanksToStorageInventory(int tankQuantity) {
	storageQuantity -= tankQuantity;
    }

    public int getTotalQuantity() {
	return totalQuantity;
    }
}
