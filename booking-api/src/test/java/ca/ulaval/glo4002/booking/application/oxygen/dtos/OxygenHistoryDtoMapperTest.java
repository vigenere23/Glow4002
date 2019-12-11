package ca.ulaval.glo4002.booking.application.oxygen.dtos;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.oxygen.history.OxygenHistoryEntry;

public class OxygenHistoryDtoMapperTest {

    private OxygenHistoryDtoMapper oxygenHistoryDtoMapper;
    private OxygenHistoryEntry oxygenHistoryEntry;

    @BeforeEach
    public void setup() {
        oxygenHistoryDtoMapper = new OxygenHistoryDtoMapper();
        oxygenHistoryEntry = createOxygenHistoryEntry();
    }

    @Test
    public void whenMappingToDto_itSetsTheSameDate() {
        OxygenHistoryEntryDto historyDto = oxygenHistoryDtoMapper.toEntryDto(oxygenHistoryEntry);
        assertThat(historyDto.date).isEqualTo(oxygenHistoryEntry.getDate());
    }

    @Test
    public void whenMappingToDto_itSetsTheSameCandlesUsed() {
        OxygenHistoryEntryDto historyDto = oxygenHistoryDtoMapper.toEntryDto(oxygenHistoryEntry);
        assertThat(historyDto.candlesQuantity).isEqualTo(oxygenHistoryEntry.getCandlesUsed());
    }

    @Test
    public void whenMappingToDto_itSetsTheSameWaterUsed() {
        OxygenHistoryEntryDto historyDto = oxygenHistoryDtoMapper.toEntryDto(oxygenHistoryEntry);
        assertThat(historyDto.waterQuantity).isEqualTo(oxygenHistoryEntry.getWaterUsed());
    }

    @Test
    public void whenMappingToDto_itSetsTheSameOxygenTankBought() {
        OxygenHistoryEntryDto historyDto = oxygenHistoryDtoMapper.toEntryDto(oxygenHistoryEntry);
        assertThat(historyDto.tankBought).isEqualTo(oxygenHistoryEntry.getTankBought());
    }

    @Test
    public void whenMappingToDto_itSetsTheSameOxygenTankMade() {
        OxygenHistoryEntryDto historyDto = oxygenHistoryDtoMapper.toEntryDto(oxygenHistoryEntry);
        assertThat(historyDto.tankMade).isEqualTo(oxygenHistoryEntry.getTankMade());
    }

    private OxygenHistoryEntry createOxygenHistoryEntry() {
        return new OxygenHistoryEntry(LocalDate.now());
    }
}
