package ca.ulaval.glo4002.booking.application.orders.dtos;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.ulaval.glo4002.booking.domain.Price;
import ca.ulaval.glo4002.booking.domain.orders.PassOrder;
import ca.ulaval.glo4002.booking.domain.orders.VendorCode;
import ca.ulaval.glo4002.booking.domain.orders.order_number.OrderNumber;
import ca.ulaval.glo4002.booking.domain.passes.Pass;

@ExtendWith(MockitoExtension.class)
public class PassOrderDtoMapperTest {

    private final static OrderNumber ORDER_NUMBER = new OrderNumber(VendorCode.TEAM, 10);

    private PassOrder passOrder;
    private List<Pass> passes;
    
    @Mock List<PassDto> passDtos;
    @Mock PassDtoMapper passDtoMapper;
    @InjectMocks PassOrderDtoMapper passOrderDtoMapper;

    @BeforeEach
    public void setup() {
        passes = new ArrayList<>();
        when(passDtoMapper.toDtos(passes)).thenReturn(passDtos);
        passOrder = new PassOrder(ORDER_NUMBER, passes, Optional.empty());
    }

    @Test
    public void whenMappingToDto_itSetsTheSameOrderNumberAsValue() {
        PassOrderDto passOrderDto = passOrderDtoMapper.toDto(passOrder);
        assertThat(passOrderDto.orderNumber).isEqualTo(ORDER_NUMBER.toString());
    }

    @Test
    public void whenMappingToDto_itSetsTheSamePriceRounded() {
        PassOrderDto passOrderDto = passOrderDtoMapper.toDto(passOrder);
        Price price = passOrder.getPrice();
        assertThat(passOrderDto.price).isEqualTo(price.getRoundedAmountFromCurrencyScale());
    }

    @Test
    public void whenMappingToDto_itSetsTheSamePassesAsDtos() {
        PassOrderDto passOrderDto = passOrderDtoMapper.toDto(passOrder);
        assertThat(passOrderDto.passes).isEqualTo(passDtos);
    }
}
