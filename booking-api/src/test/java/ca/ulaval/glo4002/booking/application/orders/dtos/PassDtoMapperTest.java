package ca.ulaval.glo4002.booking.application.orders.dtos;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.ulaval.glo4002.booking.domain.Price;
import ca.ulaval.glo4002.booking.domain.dates.FestivalDates;
import ca.ulaval.glo4002.booking.domain.passes.Pass;
import ca.ulaval.glo4002.booking.domain.passes.PassCategory;
import ca.ulaval.glo4002.booking.domain.passes.PassOption;
import ca.ulaval.glo4002.booking.domain.passes.pass_number.PassNumber;
import ca.ulaval.glo4002.booking.domain.transport.PassengerNumber;

@ExtendWith(MockitoExtension.class)
public class PassDtoMapperTest {

    private PassDtoMapper passDtoMapper;
    private Pass pass;

    @Mock FestivalDates festivalDates;

    @BeforeEach
    public void setup() {
        passDtoMapper = new PassDtoMapper();
        pass = createPass();
    }

    @Test
    public void whenMappingToDto_itSetsTheSamePassNumberValue() {
        PassDto passDto = passDtoMapper.toDto(pass);
        assertThat(passDto.passNumber).isEqualTo(pass.getPassNumber());
    }

    @Test
    public void whenMappingToDto_itSetsTheSamePassOptionString() {
        PassDto passDto = passDtoMapper.toDto(pass);
        assertThat(passDto.passOption).isEqualTo(pass.getPassOption());
    }

    @Test
    public void whenMappingToDto_itSetsTheSamePassCategoryString() {
        PassDto passDto = passDtoMapper.toDto(pass);
        assertThat(passDto.passCategory).isEqualTo(pass.getPassCategory());
    }

    private Pass createPass() {
        return new Pass(festivalDates, new PassNumber(10), new PassengerNumber(1234),
            PassOption.SINGLE_PASS, PassCategory.NEBULA, new Price(123.456), LocalDate.now());
    }
}
