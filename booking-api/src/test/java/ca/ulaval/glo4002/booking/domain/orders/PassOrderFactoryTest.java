package ca.ulaval.glo4002.booking.domain.orders;

import static org.assertj.core.api.Assertions.assertThat;
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
    private static final Optional<List<LocalDate>> NO_EVENT_DATES_LIST = Optional.empty();
    private static final Optional<LocalDate> NO_EVENT_DATE = Optional.empty();

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

    @Test
    public void givenNoEventDates_whenCreatingOrder_thenThePassFactoryIsCalledOneTimeWithNoEventDate() {
        passOrderFactory.create(ANY_ORDER_DATE, VENDOR_CODE, ANY_PASS_OPTION, ANY_PASS_CATEGORY, NO_EVENT_DATES_LIST);
        verify(passFactory, times(1)).create(ANY_PASS_OPTION, ANY_PASS_CATEGORY, NO_EVENT_DATE);
    }

    @Test
    public void givenEmptyListOfEventDates_whenCreatingOrder_thenThePassFactoryIsCalledOneTimeWithNoEventDate() {
        Optional<List<LocalDate>> emptyList = Optional.of(new ArrayList<>());
        passOrderFactory.create(ANY_ORDER_DATE, VENDOR_CODE, ANY_PASS_OPTION, ANY_PASS_CATEGORY, emptyList);
        verify(passFactory, times(1)).create(ANY_PASS_OPTION, ANY_PASS_CATEGORY, NO_EVENT_DATE);
    }

    @Test
    public void givenListOfNEventDates_whenCreatingOrder_thenThePassFactoryIsCalledNTimes() {
        final int NUMBER_OF_DATES = 5;
        LocalDate date = LocalDate.now();
        Optional<List<LocalDate>> eventDates = Optional.of(getListOfDates(NUMBER_OF_DATES, date));

        passOrderFactory.create(ANY_ORDER_DATE, VENDOR_CODE, ANY_PASS_OPTION, ANY_PASS_CATEGORY, eventDates);
        
        verify(passFactory, times(NUMBER_OF_DATES)).create(ANY_PASS_OPTION, ANY_PASS_CATEGORY, Optional.of(date));
    }

    @Test
    public void givenNoEventDates_whenCreatingValidOrder_thenTheReturnedPassOrderHasOnePass() {
        PassOrder passOrder = passOrderFactory.create(ANY_ORDER_DATE, VENDOR_CODE, ANY_PASS_OPTION, ANY_PASS_CATEGORY, NO_EVENT_DATES_LIST);
        int numberOfPasses = passOrder.getPasses().size();
        assertThat(numberOfPasses).isEqualTo(1);
    }

    @Test
    public void givenEmptyListOfEventDates_whenCreatingValidOrder_thenTheReturnedPassOrderHasOnePass() {
        Optional<List<LocalDate>> emptyList = Optional.of(new ArrayList<>());
        
        PassOrder passOrder = passOrderFactory.create(ANY_ORDER_DATE, VENDOR_CODE, ANY_PASS_OPTION, ANY_PASS_CATEGORY, emptyList);
        
        int numberOfPasses = passOrder.getPasses().size();
        assertThat(numberOfPasses).isEqualTo(1);
    }

    @Test
    public void givenListOfNEventDates_whenCreatingOrder_thenTheReturnedOrderHasNPasses() {
        final int NUMBER_OF_DATES = 5;
        LocalDate date = LocalDate.now();
        Optional<List<LocalDate>> eventDates = Optional.of(getListOfDates(NUMBER_OF_DATES, date));

        PassOrder passOrder = passOrderFactory.create(ANY_ORDER_DATE, VENDOR_CODE, ANY_PASS_OPTION, ANY_PASS_CATEGORY, eventDates);

        int numberOfPasses = passOrder.getPasses().size();
        assertThat(numberOfPasses).isEqualTo(NUMBER_OF_DATES);
    }

    private List<LocalDate> getListOfDates(int numberOfDates, LocalDate date) {
        List<LocalDate> listOfDates = new ArrayList<>();
        for (int i = 0; i < numberOfDates; i++) {
            listOfDates.add(date);
        }
        return listOfDates;
    }
}
