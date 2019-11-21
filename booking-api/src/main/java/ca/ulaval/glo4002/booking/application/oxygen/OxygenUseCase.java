package ca.ulaval.glo4002.booking.application.oxygen;

import java.util.List;

import ca.ulaval.glo4002.booking.application.oxygen.dtos.OxygenHistoryEntryDto;
import ca.ulaval.glo4002.booking.application.oxygen.dtos.OxygenHistoryEntryDtoMapper;
import ca.ulaval.glo4002.booking.application.oxygen.dtos.OxygenInventoryEntryDto;
import ca.ulaval.glo4002.booking.application.oxygen.dtos.OxygenInventoryEntryDtoMapper;
import ca.ulaval.glo4002.booking.domain.oxygen.history.OxygenHistoryRepository;
import ca.ulaval.glo4002.booking.domain.oxygen.inventory.OxygenInventoryRepository;

public class OxygenUseCase {
    private OxygenHistoryRepository oxygenHistory;
    private OxygenInventoryRepository oxygenInventory;
    private OxygenHistoryEntryDtoMapper oxygenHistoryEntryDtoMapper;
    private OxygenInventoryEntryDtoMapper oxygenInventoryEntryDtoMapper;

    public OxygenUseCase(
        OxygenHistoryRepository oxygenHistory,
        OxygenInventoryRepository oxygenInventory,
        OxygenHistoryEntryDtoMapper oxygenHistoryEntryDtoMapper,
        OxygenInventoryEntryDtoMapper oxygenInventoryEntryDtoMapper
    ) {
        this.oxygenHistory = oxygenHistory;
        this.oxygenInventory = oxygenInventory;
        this.oxygenHistoryEntryDtoMapper = oxygenHistoryEntryDtoMapper;
        this.oxygenInventoryEntryDtoMapper = oxygenInventoryEntryDtoMapper;
    }

    public List<OxygenHistoryEntryDto> getOxygenHistory() {
        return oxygenHistoryEntryDtoMapper.toDtos(oxygenHistory.findAll());
    }

    public List<OxygenInventoryEntryDto> getOxygenInventory() {
        return oxygenInventoryEntryDtoMapper.toDtos(oxygenInventory.findAll());
    }
}
