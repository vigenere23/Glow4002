package ca.ulaval.glo4002.booking.domain.orders;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.exceptions.OutOfSaleDatesException;
import ca.ulaval.glo4002.booking.domain.festivals.FestivalDates;
import ca.ulaval.glo4002.booking.domain.passes.PassCategory;
import ca.ulaval.glo4002.booking.domain.passes.PassFactory;
import ca.ulaval.glo4002.booking.domain.passes.PassOption;

public class PassOrderFactoryTest {

    private FestivalDates festivalDates;
    private PassFactory passFactory;
    private PassOrderFactory passOrderFactory;
    
    private static final OffsetDateTime ANY_ORDER_DATE = OffsetDateTime.now();
    private static final VendorCode VENDOR_CODE = VendorCode.TEAM;
    private static final PassOption ANY_PASS_OPTION = PassOption.PACKAGE;
    private static final PassCategory ANY_PASS_CATEGORY = PassCategory.NEBULA;

    @BeforeEach
    public void setUp() {
        festivalDates = mock(FestivalDates.class);
        passFactory = mock(PassFactory.class);
        passOrderFactory = new PassOrderFactory(festivalDates, passFactory);
    }

    @Test
    public void givenOrderDateOutsideFestivalSaleDates_whenCreatingOrder_itThrowsOutOfSaleDateException() {
        doThrow(new OutOfSaleDatesException(OffsetDateTime.now(), OffsetDateTime.now()))
            .when(festivalDates).validateOrderDate(any(OffsetDateTime.class));
        Optional<List<LocalDate>> anyEventDates = Optional.empty();

        assertThatExceptionOfType(OutOfSaleDatesException.class).isThrownBy(() -> {
            passOrderFactory.create(ANY_ORDER_DATE, VENDOR_CODE, ANY_PASS_OPTION, ANY_PASS_CATEGORY, anyEventDates);
        });
    }

    // TODO  #129 verify that PassFactory is called with correct arguments (once it will be injected)
}
