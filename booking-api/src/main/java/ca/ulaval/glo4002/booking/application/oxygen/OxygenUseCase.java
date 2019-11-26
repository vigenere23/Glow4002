package ca.ulaval.glo4002.booking.application.oxygen;

import javax.inject.Inject;

import ca.ulaval.glo4002.booking.application.oxygen.dtos.OxygenInventoryDto;
import ca.ulaval.glo4002.booking.application.oxygen.dtos.OxygenHistoryDto;
import ca.ulaval.glo4002.booking.application.oxygen.dtos.OxygenHistoryDtoMapper;
import ca.ulaval.glo4002.booking.application.oxygen.dtos.OxygenInventoryDtoMapper;
import ca.ulaval.glo4002.booking.domain.oxygen.history.OxygenHistoryRepository;
import ca.ulaval.glo4002.booking.domain.oxygen.inventory.OxygenInventoryRepository;

public class OxygenUseCase {

    @Inject private OxygenHistoryRepository historyRepository;
    @Inject private OxygenInventoryRepository inventoryRepository;
    @Inject private OxygenHistoryDtoMapper oxygenHistoryDtoMapper;
    @Inject private OxygenInventoryDtoMapper oxygenInventoryDtoMapper;

    public OxygenHistoryDto getOxygenHistory() {
        return oxygenHistoryDtoMapper.toDto(historyRepository.findAll());
    }

    public OxygenInventoryDto getOxygenInventory() {
        return oxygenInventoryDtoMapper.toDto(inventoryRepository.findAll());
    }
}
