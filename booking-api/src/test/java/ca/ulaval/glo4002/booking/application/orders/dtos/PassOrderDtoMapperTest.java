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

import ca.ulaval.glo4002.booking.domain.orders.PassOrder;
import ca.ulaval.glo4002.booking.domain.orders.VendorCode;
import ca.ulaval.glo4002.booking.domain.orders.order_number.OrderNumber;
import ca.ulaval.glo4002.booking.domain.passes.Pass;

@ExtendWith(MockitoExtension.class)
public class PassOrderDtoMapperTest {

    private PassOrder passOrder;
    private List<Pass> passes;
    
    @Mock List<PassDto> passDtos;
    @Mock PassDtoMapper passDtoMapper;
    @InjectMocks PassOrderDtoMapper passOrderDtoMapper;

    @BeforeEach
    public void setup() {
        passes = new ArrayList<>();
        when(passDtoMapper.toDtos(passes)).thenReturn(passDtos);
        passOrder = createPassOrder();
    }

    @Test
    public void whenMappingToDto_itSetsTheSameOrderNumberAsValue() {
        PassOrderDto passOrderDto = passOrderDtoMapper.toDto(passOrder);
        assertThat(passOrderDto.orderNumber).isEqualTo(passOrder.getOrderNumber());
    }

    @Test
    public void whenMappingToDto_itSetsTheSamePriceRounded() {
        PassOrderDto passOrderDto = passOrderDtoMapper.toDto(passOrder);
        assertThat(passOrderDto.price).isEqualTo(passOrder.getPrice());
    }

    @Test
    public void whenMappingToDto_itSetsTheSamePassesAsDtos() {
        PassOrderDto passOrderDto = passOrderDtoMapper.toDto(passOrder);
        assertThat(passOrderDto.passes).isEqualTo(passDtos);
    }

    private PassOrder createPassOrder() {
        return new PassOrder(new OrderNumber(VendorCode.TEAM, 10), passes, Optional.empty());
    }
}
