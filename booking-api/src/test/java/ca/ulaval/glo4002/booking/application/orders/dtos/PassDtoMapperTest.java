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

    private final static PassNumber PASS_NUMBER = new PassNumber(10);
    private final static PassengerNumber PASSENGER_NUMBER = new PassengerNumber(1234);
    private final static PassOption PASS_OPTION = PassOption.SINGLE_PASS;
    private final static PassCategory PASS_CATEGORY = PassCategory.NEBULA;
    private final static Price PRICE = new Price(123.456);
    private final static LocalDate EVENT_DATE = LocalDate.now();

    private PassDtoMapper passDtoMapper;
    private Pass pass;

    @Mock FestivalDates festivalDates;

    @BeforeEach
    public void setup() {
        passDtoMapper = new PassDtoMapper();
        pass = new Pass(festivalDates, PASS_NUMBER, PASSENGER_NUMBER, PASS_OPTION, PASS_CATEGORY, PRICE, EVENT_DATE);
    }

    @Test
    public void whenMappingToDto_itSetsTheSamePassNumberValue() {
        PassDto passDto = passDtoMapper.toDto(pass);
        assertThat(passDto.passNumber).isEqualTo(PASS_NUMBER.getValue());
    }

    @Test
    public void whenMappingToDto_itSetsTheSamePassOptionString() {
        PassDto passDto = passDtoMapper.toDto(pass);
        assertThat(passDto.passOption).isEqualTo(PASS_OPTION.toString());
    }

    @Test
    public void whenMappingToDto_itSetsTheSamePassCategoryString() {
        PassDto passDto = passDtoMapper.toDto(pass);
        assertThat(passDto.passCategory).isEqualTo(PASS_CATEGORY.toString());
    }
}
