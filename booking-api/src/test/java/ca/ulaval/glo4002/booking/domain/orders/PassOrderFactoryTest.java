package ca.ulaval.glo4002.booking.domain.orders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
import ca.ulaval.glo4002.booking.domain.orders.orderNumber.OrderNumberFactory;
import ca.ulaval.glo4002.booking.domain.passes.PassCategory;
import ca.ulaval.glo4002.booking.domain.passes.PassFactory;
import ca.ulaval.glo4002.booking.domain.passes.PassOption;

public class PassOrderFactoryTest {

    private static final OffsetDateTime SOME_DATE = OffsetDateTime.now();
    private static final VendorCode SOME_VENDOR_CODE = VendorCode.TEAM;
    private static final PassOption SOME_PASS_OPTION = PassOption.PACKAGE;
    private static final PassCategory SOME_PASS_CATEGORY = PassCategory.NEBULA;
    private static final Optional<List<LocalDate>> NO_EVENT_DATES_LIST = Optional.empty();
    private static final Optional<LocalDate> NO_EVENT_DATE = Optional.empty();

    private FestivalDates festivalDates;
    private OrderNumberFactory orderNumberFactory;
    private PassFactory passFactory;
    private PassOrderFactory passOrderFactory;

    @BeforeEach
    public void setupPassOrderFactory() {
        festivalDates = mock(FestivalDates.class);
        orderNumberFactory = mock(OrderNumberFactory.class);
        passFactory = mock(PassFactory.class);
        passOrderFactory = new PassOrderFactory(festivalDates, orderNumberFactory, passFactory);
    }

    @Test
    public void givenOrderDateOutsideFestivalSaleDates_whenCreatingOrder_itThrowsOutOfSaleDateException() {
        doThrow(new OutOfSaleDatesException(SOME_DATE, SOME_DATE))
            .when(festivalDates).validateOrderDate(any(OffsetDateTime.class));
        Optional<List<LocalDate>> anyEventDates = Optional.empty();

        assertThrows(OutOfSaleDatesException.class, () -> passOrderFactory.create(SOME_DATE, SOME_VENDOR_CODE, SOME_PASS_OPTION, SOME_PASS_CATEGORY, anyEventDates));
    }

    @Test
    public void givenNoEventDates_whenCreatingOrder_thenThePassFactoryIsCalledOneTimeWithNoEventDate() {
        passOrderFactory.create(SOME_DATE, SOME_VENDOR_CODE, SOME_PASS_OPTION, SOME_PASS_CATEGORY, NO_EVENT_DATES_LIST);
        verify(passFactory, times(1)).create(SOME_PASS_OPTION, SOME_PASS_CATEGORY, NO_EVENT_DATE);
    }

    @Test
    public void givenEmptyListOfEventDates_whenCreatingOrder_thenThePassFactoryIsCalledOneTimeWithNoEventDate() {
        Optional<List<LocalDate>> emptyList = Optional.of(new ArrayList<>());
        passOrderFactory.create(SOME_DATE, SOME_VENDOR_CODE, SOME_PASS_OPTION, SOME_PASS_CATEGORY, emptyList);
        verify(passFactory, times(1)).create(SOME_PASS_OPTION, SOME_PASS_CATEGORY, NO_EVENT_DATE);
    }

    @Test
    public void givenListOfNEventDates_whenCreatingOrder_thenThePassFactoryIsCalledNTimes() {
        final int NUMBER_OF_DATES = 5;
        LocalDate date = LocalDate.now();
        Optional<List<LocalDate>> eventDates = Optional.of(getListOfDates(NUMBER_OF_DATES, date));

        passOrderFactory.create(SOME_DATE, SOME_VENDOR_CODE, SOME_PASS_OPTION, SOME_PASS_CATEGORY, eventDates);
        
        verify(passFactory, times(NUMBER_OF_DATES)).create(SOME_PASS_OPTION, SOME_PASS_CATEGORY, Optional.of(date));
    }

    @Test
    public void givenNoEventDates_whenCreatingValidOrder_thenTheReturnedPassOrderHasOnePass() {
        PassOrder passOrder = passOrderFactory.create(SOME_DATE, SOME_VENDOR_CODE, SOME_PASS_OPTION, SOME_PASS_CATEGORY, NO_EVENT_DATES_LIST);
        int numberOfPasses = passOrder.getPasses().size();
        assertEquals(1, numberOfPasses);
    }

    @Test
    public void givenEmptyListOfEventDates_whenCreatingValidOrder_thenTheReturnedPassOrderHasOnePass() {
        Optional<List<LocalDate>> emptyList = Optional.of(new ArrayList<>());
        
        PassOrder passOrder = passOrderFactory.create(SOME_DATE, SOME_VENDOR_CODE, SOME_PASS_OPTION, SOME_PASS_CATEGORY, emptyList);
        
        int numberOfPasses = passOrder.getPasses().size();
        assertEquals(1, numberOfPasses);
    }

    @Test
    public void givenListOfNEventDates_whenCreatingOrder_thenTheReturnedOrderHasNPasses() {
        final int NUMBER_OF_DATES = 5;
        LocalDate date = LocalDate.now();
        Optional<List<LocalDate>> eventDates = Optional.of(getListOfDates(NUMBER_OF_DATES, date));

        PassOrder passOrder = passOrderFactory.create(SOME_DATE, SOME_VENDOR_CODE, SOME_PASS_OPTION, SOME_PASS_CATEGORY, eventDates);

        int numberOfPasses = passOrder.getPasses().size();
        assertEquals(NUMBER_OF_DATES, numberOfPasses);
    }

    private List<LocalDate> getListOfDates(int numberOfDates, LocalDate date) {
        List<LocalDate> listOfDates = new ArrayList<>();
        for (int i = 0; i < numberOfDates; i++) {
            listOfDates.add(date);
        }
        return listOfDates;
    }
}
