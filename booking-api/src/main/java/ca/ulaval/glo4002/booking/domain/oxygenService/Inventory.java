package ca.ulaval.glo4002.booking.domain.oxygenService;

import java.time.LocalDate;

public interface Inventory {
    public void adjustInventory(LocalDate orderDate, GasNeedable gasNeed);

    public int getInventory();
}
