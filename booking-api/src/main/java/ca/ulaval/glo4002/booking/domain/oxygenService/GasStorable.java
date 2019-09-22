package ca.ulaval.glo4002.booking.domain.oxygenService;

import java.time.LocalDate;
import java.util.List;

public interface GasStorable {
    public void adjustInventory(LocalDate orderDate, GasNeedable gasNeedable);

    public List<Inventory> getInventories();
}
