package ca.ulaval.glo4002.booking.domain.orders;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.OffsetDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.api.dtos.orders.PassRequest;
import ca.ulaval.glo4002.booking.domain.exceptions.OutOfSaleDatesException;
import ca.ulaval.glo4002.booking.domain.festivals.FestivalDates;
import ca.ulaval.glo4002.booking.domain.passes.PassCategory;
import ca.ulaval.glo4002.booking.domain.passes.PassOption;

public class PassOrderFactoryTest {

    private FestivalDates festivalDates;
    private PassOrderFactory passOrderFactory;
    private PassRequest passRequest;
    
    private static final VendorCode VENDOR_CODE = VendorCode.TEAM;
    private static final PassOption PASS_OPTION = PassOption.SINGLE_PASS;
    private static final PassCategory PASS_CATEGORY = PassCategory.NEBULA;

    @BeforeEach
    public void setUp() {
        festivalDates = mock(FestivalDates.class);
        passOrderFactory = new PassOrderFactory(festivalDates);
        passRequest = mock(PassRequest.class);
        when(passRequest.getPassOption()).thenReturn(PassOption.PACKAGE);
        when(passRequest.getPassCategory()).thenReturn(PassCategory.NEBULA);
    }

    @Test
    public void givenOrderDateOutsideFestivalSaleDates_whenCreatingOrder_itThrowsOutOfSaleDateException() {
        doThrow(new OutOfSaleDatesException(OffsetDateTime.now(), OffsetDateTime.now()))
            .when(festivalDates).validateOrderDate(any(OffsetDateTime.class));

        assertThatExceptionOfType(OutOfSaleDatesException.class).isThrownBy(() -> {
            passOrderFactory.create(OffsetDateTime.now(), VENDOR_CODE, PASS_OPTION, PASS_CATEGORY, Optional.empty());
        });
    }

    // TODO  #129 verify that PassFactory is called with correct arguments (once it will be injected)
}
