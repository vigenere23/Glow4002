package ca.ulaval.glo4002.booking.application.oxygen;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.ulaval.glo4002.booking.application.oxygen.dtos.OxygenHistoryDto;
import ca.ulaval.glo4002.booking.application.oxygen.dtos.OxygenHistoryDtoMapper;
import ca.ulaval.glo4002.booking.application.oxygen.dtos.OxygenInventoryDto;
import ca.ulaval.glo4002.booking.application.oxygen.dtos.OxygenInventoryDtoMapper;
import ca.ulaval.glo4002.booking.domain.oxygen.history.OxygenHistoryEntry;
import ca.ulaval.glo4002.booking.domain.oxygen.history.OxygenHistoryRepository;
import ca.ulaval.glo4002.booking.domain.oxygen.inventory.OxygenInventoryEntry;
import ca.ulaval.glo4002.booking.domain.oxygen.inventory.OxygenInventoryRepository;

@ExtendWith(MockitoExtension.class)
public class OxygenUseCaseTest {

    @Mock OxygenInventoryRepository oxygenInventoryRepository;
    @Mock OxygenHistoryRepository oxygenHistoryRepository;
    @Mock OxygenInventoryDtoMapper oxygenInventoryDtoMapper;
    @Mock OxygenHistoryDtoMapper oxygenHistoryDtoMapper;
    @InjectMocks OxygenUseCase oxygenUseCase;

    @Test
    public void whenGettingInventory_itFetchesFromTheInventoryRepository() {
        oxygenUseCase.getOxygenInventory();
        verify(oxygenInventoryRepository).findAll();
    }

    @Test
    public void whenGettingInventory_itReturnsTheEntireInventory() {
        List<OxygenInventoryEntry> inventoryEntries = createInventoryEntries();
        OxygenInventoryDto inventoryDto = mock(OxygenInventoryDto.class);
        when(oxygenInventoryRepository.findAll()).thenReturn(inventoryEntries);
        when(oxygenInventoryDtoMapper.toDto(inventoryEntries)).thenReturn(inventoryDto);
        
        OxygenInventoryDto dto = oxygenUseCase.getOxygenInventory();
        assertThat(dto).isEqualTo(inventoryDto);
    }

    @Test
    public void whenGettingHistory_itFetchesFromTheHistoryRepository() {
        oxygenUseCase.getOxygenHistory();
        verify(oxygenHistoryRepository).findAll();
    }

    @Test
    public void whenGettingHistory_itReturnsTheEntireHistory() {
        List<OxygenHistoryEntry> historyEntries = createHistoryEntries();
        OxygenHistoryDto historyDto = mock(OxygenHistoryDto.class);
        when(oxygenHistoryRepository.findAll()).thenReturn(historyEntries);
        when(oxygenHistoryDtoMapper.toDto(historyEntries)).thenReturn(historyDto);
        
        OxygenHistoryDto dto = oxygenUseCase.getOxygenHistory();
        assertThat(dto).isEqualTo(historyDto);
    }

    private List<OxygenInventoryEntry> createInventoryEntries() {
        List<OxygenInventoryEntry> entries = new ArrayList<>();
        entries.add(mock(OxygenInventoryEntry.class));
        return entries;
    }

    private List<OxygenHistoryEntry> createHistoryEntries() {
        List<OxygenHistoryEntry> entries = new ArrayList<>();
        entries.add(mock(OxygenHistoryEntry.class));
        return entries;
    }
}
