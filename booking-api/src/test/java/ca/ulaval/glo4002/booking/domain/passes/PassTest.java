package ca.ulaval.glo4002.booking.domain.passes;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.time.OffsetDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.Price;
import ca.ulaval.glo4002.booking.domain.dates.FestivalDates;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenGrade;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenRequester;
import ca.ulaval.glo4002.booking.domain.passes.pass_number.PassNumber;
import ca.ulaval.glo4002.booking.domain.transport.PassengerNumber;
import ca.ulaval.glo4002.booking.domain.transport.TransportReserver;
import ca.ulaval.glo4002.booking.domain.transport.shuttles.ShuttleCategory;

public class PassTest {
    private final static PassNumber A_PASS_NUMBER = new PassNumber(0);
    private final static PassengerNumber A_PASSENGER_NUMBER = new PassengerNumber(A_PASS_NUMBER);
    private final static PassOption A_PASS_OPTION = PassOption.SINGLE_PASS;
    private final static PassOption ANOTHER_PASS_OPTION = PassOption.PACKAGE;
    private final static PassCategory A_PASS_CATEGORY = PassCategory.NEBULA;
    private final static PassCategory ANOTHER_PASS_CATEGORY = PassCategory.SUPERGIANT;
    private final static ShuttleCategory A_SHUTTLE_CATEGORY = ShuttleCategory.SPACE_X;
    private final static OffsetDateTime AN_ORDER_DATE = OffsetDateTime.now();
    private final static LocalDate A_START_DATE = LocalDate.now();
    private final static LocalDate AN_END_DATE = A_START_DATE.plusDays(3);
    private final static int A_NUMBER_OF_FESTIVAL_DAYS = 5;
    private final static LocalDate A_FESTIVAL_START = LocalDate.now();
    private final static LocalDate A_FESTIVAL_END = A_FESTIVAL_START.plusDays(A_NUMBER_OF_FESTIVAL_DAYS - 1);
    private final static LocalDate BETWEEN_A_FESTIVAL_DATE = A_FESTIVAL_START.plusDays(1);

    private FestivalDates festivalDates;
    private TransportReserver transportReserver;
    private OxygenRequester oxygenRequester;
    
    @BeforeEach
    public void setUpPass() {
        festivalDates = mock(FestivalDates.class);
        doNothing().when(festivalDates).validateEventDate(any(LocalDate.class));
        doNothing().when(festivalDates).validateOrderDate(any(OffsetDateTime.class));

        transportReserver = mock(TransportReserver.class);
        oxygenRequester = mock(OxygenRequester.class);
    }

    @Test
    public void givenSamePassOptionAndCategory_whenCheckingIfOfType_itReturnsTrue() {
        Pass pass = createSimplePass(A_PASS_OPTION, A_PASS_CATEGORY, A_START_DATE, AN_END_DATE);
        PassOption passOption = pass.getPassOption();
        PassCategory passCategory = pass.getPassCategory();

        assertThat(pass.isOfType(passOption, passCategory)).isTrue();
    }

    @Test
    public void givenSamePassOptionOnly_whenCheckingIfOfType_itReturnsFalse() {
        Pass pass = createSimplePass(A_PASS_OPTION, A_PASS_CATEGORY, A_START_DATE, AN_END_DATE);
        PassOption passOption = pass.getPassOption();
        PassCategory passCategory = ANOTHER_PASS_CATEGORY;

        assertThat(pass.isOfType(passOption, passCategory)).isFalse();
    }

    @Test
    public void givenSamePassCategoryOnly_whenCheckingIfOfType_itReturnsFalse() {
        Pass pass = createSimplePass(A_PASS_OPTION, A_PASS_CATEGORY, A_START_DATE, AN_END_DATE);
        PassOption passOption = ANOTHER_PASS_OPTION;
        PassCategory passCategory = pass.getPassCategory();

        assertThat(pass.isOfType(passOption, passCategory)).isFalse();
    }

    @Test
    public void givenStartDate_whenCheckingIfAttendingAtDate_itReturnsTrue() {
        Pass pass = createSimplePass(A_PASS_OPTION, A_PASS_CATEGORY, A_START_DATE, AN_END_DATE);
        assertThat(pass.isAttendingAtDate(A_START_DATE)).isTrue();
    }

    @Test
    public void givenEndDate_whenCheckingIfAttendingAtDate_itReturnsTrue() {
        Pass pass = createSimplePass(A_PASS_OPTION, A_PASS_CATEGORY, A_START_DATE, AN_END_DATE);
        assertThat(pass.isAttendingAtDate(AN_END_DATE)).isTrue();
    }

    @Test
    public void givenInBetweenDate_whenCheckingIfAttendingAtDate_itReturnsTrue() {
        LocalDate date = A_START_DATE.plusDays(1);
        Pass pass = createSimplePass(A_PASS_OPTION, A_PASS_CATEGORY, A_START_DATE, AN_END_DATE);
        assertThat(pass.isAttendingAtDate(date)).isTrue();
    }

    @Test
    public void givenBeforeStartDate_whenCheckingIfAttendingAtDate_itReturnsFalse() {
        LocalDate date = A_START_DATE.minusDays(1);
        Pass pass = createSimplePass(A_PASS_OPTION, A_PASS_CATEGORY, A_START_DATE, AN_END_DATE);
        assertThat(pass.isAttendingAtDate(date)).isFalse();
    }

    @Test
    public void givenAfterEndDate_whenCheckingIfAttendingAtDate_itReturnsFalse() {
        LocalDate date = AN_END_DATE.plusDays(1);
        Pass pass = createSimplePass(A_PASS_OPTION, A_PASS_CATEGORY, A_START_DATE, AN_END_DATE);
        assertThat(pass.isAttendingAtDate(date)).isFalse();
    }

    @Test
    public void whenReservingShuttles_thenItReservesASingleDeparture() {
        Pass pass = createSimplePass(PassOption.SINGLE_PASS, A_PASS_CATEGORY, A_START_DATE, AN_END_DATE);
        pass.reserveShuttles(transportReserver);
        
        verify(transportReserver, times(1)).reserveDeparture(any(ShuttleCategory.class), any(LocalDate.class), any(PassengerNumber.class));
    }

    @Test
    public void whenReservingShuttles_thenItReservesASingleArrival() {
        Pass pass = createSimplePass(PassOption.SINGLE_PASS, A_PASS_CATEGORY, A_START_DATE, AN_END_DATE);
        pass.reserveShuttles(transportReserver);
        
        verify(transportReserver, times(1)).reserveArrival(any(ShuttleCategory.class), any(LocalDate.class), any(PassengerNumber.class));
    }

    @Test
    public void whenReservingShuttles_thenDepartureShuttlesAreReservedWithStartDateAndPassNumber() {
        Pass pass = createSimplePass(PassOption.SINGLE_PASS, A_PASS_CATEGORY, A_START_DATE, AN_END_DATE);
        pass.reserveShuttles(transportReserver);
        
        verify(transportReserver).reserveDeparture(A_SHUTTLE_CATEGORY, A_START_DATE, A_PASSENGER_NUMBER);
    }

    @Test
    public void whenReserveShuttles_thenArrivalShuttlesAreReservedWithEndDateAndPassNumber() {
        Pass pass = createSimplePass(PassOption.SINGLE_PASS, A_PASS_CATEGORY, A_START_DATE, AN_END_DATE);
        pass.reserveShuttles(transportReserver);

        verify(transportReserver).reserveArrival(A_SHUTTLE_CATEGORY, AN_END_DATE, A_PASSENGER_NUMBER);
    }

    @Test
    public void givenNebulaPass_whenReserveShuttles_thenSpaceXShuttlesAreReserved() {
        Pass pass = createSimplePass(A_PASS_OPTION, PassCategory.NEBULA, A_START_DATE, AN_END_DATE);

        pass.reserveShuttles(transportReserver);

        verify(transportReserver).reserveDeparture(ShuttleCategory.SPACE_X, A_START_DATE, A_PASSENGER_NUMBER);
        verify(transportReserver).reserveArrival(ShuttleCategory.SPACE_X, AN_END_DATE, A_PASSENGER_NUMBER);
    }

    @Test
    public void givenSupergiantPass_whenReserveShuttles_thenMillenniumFalconShuttlesAreReserved() {
        Pass pass = createSimplePass(A_PASS_OPTION, PassCategory.SUPERGIANT, A_START_DATE, AN_END_DATE);

        pass.reserveShuttles(transportReserver);

        verify(transportReserver).reserveDeparture(ShuttleCategory.MILLENNIUM_FALCON, A_START_DATE, A_PASSENGER_NUMBER);
        verify(transportReserver).reserveArrival(ShuttleCategory.MILLENNIUM_FALCON, AN_END_DATE, A_PASSENGER_NUMBER);
    }

    @Test
    public void givenSupernovaPass_whenReserveShuttles_thenEtSpaceshipShuttlesAreReserved() {
        Pass pass = createSimplePass(A_PASS_OPTION, PassCategory.SUPERNOVA, A_START_DATE, AN_END_DATE);

        pass.reserveShuttles(transportReserver);

        verify(transportReserver).reserveDeparture(ShuttleCategory.ET_SPACESHIP, A_START_DATE, A_PASSENGER_NUMBER);
        verify(transportReserver).reserveArrival(ShuttleCategory.ET_SPACESHIP, AN_END_DATE, A_PASSENGER_NUMBER);
    }

    @Test
    public void givenNebulaSinglePass_whenOrderOxygen_thenThreeGradeAOxygenIsOrdered() {
        Pass pass = createSimplePass(PassOption.SINGLE_PASS, PassCategory.NEBULA, BETWEEN_A_FESTIVAL_DATE, BETWEEN_A_FESTIVAL_DATE);

        pass.reserveOxygen(AN_ORDER_DATE, oxygenRequester);

        OxygenGrade expectedGrade = OxygenGrade.A;
        int expectedQuantity = 3;
        verify(oxygenRequester).requestOxygen(AN_ORDER_DATE, expectedGrade, expectedQuantity);
    }

    @Test
    public void givenSupergiantSinglePass_whenOrderOxygen_thenThreeGradeBOxygenIsOrdered() {
        Pass pass = createSimplePass(PassOption.SINGLE_PASS, PassCategory.SUPERGIANT, BETWEEN_A_FESTIVAL_DATE, BETWEEN_A_FESTIVAL_DATE);

        pass.reserveOxygen(AN_ORDER_DATE, oxygenRequester);

        OxygenGrade expectedGrade = OxygenGrade.B;
        int expectedQuantity = 3;
        verify(oxygenRequester).requestOxygen(AN_ORDER_DATE, expectedGrade, expectedQuantity);
    }

    @Test
    public void givenSupernovaSinglePass_whenOrderOxygen_thenFiveGradeEOxygenIsOrdered() {
        Pass pass = createSimplePass(PassOption.SINGLE_PASS, PassCategory.SUPERNOVA, BETWEEN_A_FESTIVAL_DATE, BETWEEN_A_FESTIVAL_DATE);

        pass.reserveOxygen(AN_ORDER_DATE, oxygenRequester);

        OxygenGrade expectedGrade = OxygenGrade.E;
        int expectedQuantity = 5;
        verify(oxygenRequester).requestOxygen(AN_ORDER_DATE, expectedGrade, expectedQuantity);
    }

    @Test
    public void givenNebulaPackagePass_whenOrderOxygen_thenThreeTimesNumberOfDaysGradeAOxygenIsOrdered() {
        Pass pass = createSimplePass(PassOption.SINGLE_PASS, PassCategory.NEBULA, A_FESTIVAL_START, A_FESTIVAL_END);

        pass.reserveOxygen(AN_ORDER_DATE, oxygenRequester);

        OxygenGrade expectedGrade = OxygenGrade.A;
        int expectedQuantity = 3 * A_NUMBER_OF_FESTIVAL_DAYS;
        verify(oxygenRequester).requestOxygen(AN_ORDER_DATE, expectedGrade, expectedQuantity);
    }

    @Test
    public void givenSupergiantPackagePass_whenOrderOxygen_thenThreeTimesNumberOfDaysGradeBOxygenIsOrdered()  {
        Pass pass = createSimplePass(PassOption.PACKAGE, PassCategory.SUPERGIANT, A_FESTIVAL_START, A_FESTIVAL_END);

        pass.reserveOxygen(AN_ORDER_DATE, oxygenRequester);

        OxygenGrade expectedGrade = OxygenGrade.B;
        int expectedQuantity = 3 * A_NUMBER_OF_FESTIVAL_DAYS;
        verify(oxygenRequester).requestOxygen(AN_ORDER_DATE, expectedGrade, expectedQuantity);
    }

    @Test
    public void givenSupernovaPackagePass_whenOrderOxygen_thenFiveTimesNumberOfDaysGradeEOxygenIsOrdered() {
        Pass pass = createSimplePass(PassOption.PACKAGE, PassCategory.SUPERNOVA, A_FESTIVAL_START, A_FESTIVAL_END);

        pass.reserveOxygen(AN_ORDER_DATE, oxygenRequester);

        OxygenGrade expectedGrade = OxygenGrade.E;
        int expectedQuantity = 5 * A_NUMBER_OF_FESTIVAL_DAYS;
        verify(oxygenRequester).requestOxygen(AN_ORDER_DATE, expectedGrade, expectedQuantity);
    }

    private Pass createSimplePass(PassOption passOption, PassCategory passCategory, LocalDate startDate, LocalDate endDate) {
        return new Pass(festivalDates, A_PASS_NUMBER, A_PASSENGER_NUMBER, passOption, passCategory, Price.zero(), startDate, endDate);
    }
}
