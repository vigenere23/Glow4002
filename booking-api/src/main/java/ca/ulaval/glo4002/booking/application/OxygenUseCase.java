package ca.ulaval.glo4002.booking.application;

import ca.ulaval.glo4002.booking.domain.oxygen.OxygenHistoryRepository;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenInventoryRepository;

public class OxygenUseCase {
    private final OxygenHistoryRepository oxygenHistoryRepository;
    private final OxygenInventoryRepository oxygenInventoryRepository;

    // TODO create and bind
    public OxygenUseCase(OxygenHistoryRepository oxygenHistoryRepository, OxygenInventoryRepository oxygenInventoryRepository) {
        this.oxygenHistoryRepository = oxygenHistoryRepository;
        this.oxygenInventoryRepository = oxygenInventoryRepository;
    }

    public void getOxygenReport() {
        // TODO
    }
}
