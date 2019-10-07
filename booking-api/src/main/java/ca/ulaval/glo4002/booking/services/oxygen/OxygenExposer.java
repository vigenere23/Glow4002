package ca.ulaval.glo4002.booking.services.oxygen;

import java.util.List;

import ca.ulaval.glo4002.booking.domain.oxygen.Inventory;
import ca.ulaval.glo4002.booking.domain.oxygen.History;

public abstract class OxygenExposer {

    public abstract List<Inventory> getInventory();
    public abstract List<History> getOxygenHistory();
}