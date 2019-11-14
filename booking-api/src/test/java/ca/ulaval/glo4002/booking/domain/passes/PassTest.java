package ca.ulaval.glo4002.booking.domain.passes;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.Price;
import ca.ulaval.glo4002.booking.domain.festivals.FestivalDates;
import ca.ulaval.glo4002.booking.domain.festivals.Glow4002Dates;
import ca.ulaval.glo4002.booking.domain.oxygen.*;
import ca.ulaval.glo4002.booking.domain.passes.passNumber.PassNumber;
import ca.ulaval.glo4002.booking.domain.transport.ShuttleCategory;
import ca.ulaval.glo4002.booking.domain.transport.TransportReserver;

import static org.mockito.Matchers.any;

class PassTest {
    private static final PassOption SOME_PASS_OPTION = PassOption.SINGLE_PASS;
    private static final PassCategory SOME_PASS_CATEGORY = PassCategory.NEBULA;
    private static final ShuttleCategory SOME_SHUTTLE_CATEGORY = ShuttleCategory.SPACE_X;
    private static final LocalDate SOME_ORDER_DATE = LocalDate.of(2050, 01, 01);
    private static final LocalDate SOME_START_DATE = LocalDate.of(2050, 07, 18);
    private static final LocalDate SOME_END_DATE = SOME_START_DATE.plusDays(3);
    private static final int A_NUMBER_OF_FESTIVAL_DAYS = 5;
    private static final int ONE_PLACE = 1;
    private static final LocalDate A_FESTIVAL_START = LocalDate.of(2050, 07, 17);
    private static final LocalDate A_FESTIVAL_END = A_FESTIVAL_START.plusDays(A_NUMBER_OF_FESTIVAL_DAYS - 1);
    private static final LocalDate BETWEEN_A_FESTIVAL_DATE = A_FESTIVAL_START.plusDays(1);

    private FestivalDates festivalDates;
    private PassNumber passNumber;
    private Price price;
    private TransportReserver transportReserver;
    private OxygenReserver oxygenReserver;
    
    @BeforeEach
    public void setUp() {
        festivalDates = new Glow4002Dates();
        passNumber = new PassNumber(0);
        price = Price.zero();
        transportReserver = mock(TransportReserver.class);
        oxygenReserver = mock(OxygenReserver.class);
    }

    @Test
    public void whenReservingShuttles_thenItReservesASingleDeparture() {
        Pass pass = createSimplePass(PassOption.SINGLE_PASS, SOME_PASS_CATEGORY, SOME_START_DATE, SOME_END_DATE);
        pass.reserveShuttles(transportReserver);
        verify(transportReserver, times(1)).reserveDeparture(any(ShuttleCategory.class), any(LocalDate.class), any(PassNumber.class), any(Integer.class));
    }

    @Test
    public void whenReservingShuttles_thenItReservesASingleArrival() {
        Pass pass = createSimplePass(PassOption.SINGLE_PASS, SOME_PASS_CATEGORY, SOME_START_DATE, SOME_END_DATE);
        pass.reserveShuttles(transportReserver);
        verify(transportReserver, times(1)).reserveArrival(any(ShuttleCategory.class), any(LocalDate.class), any(PassNumber.class), any(Integer.class));
    }

    @Test
    public void whenReservingShuttles_thenDepartureShuttlesAreReservedWithStartDateAndPassNumber() {
        Pass pass = createSimplePass(PassOption.SINGLE_PASS, SOME_PASS_CATEGORY, SOME_START_DATE, SOME_END_DATE);
        pass.reserveShuttles(transportReserver);
        verify(transportReserver).reserveDeparture(SOME_SHUTTLE_CATEGORY, SOME_START_DATE, pass.getPassNumber(), ONE_PLACE);
    }

    @Test
    public void whenReserveShuttles_thenArrivalShuttlesAreReservedWithEndDateAndPassNumber() {
        Pass pass = createSimplePass(PassOption.SINGLE_PASS, SOME_PASS_CATEGORY, SOME_START_DATE, SOME_END_DATE);
        pass.reserveShuttles(transportReserver);
        verify(transportReserver).reserveArrival(SOME_SHUTTLE_CATEGORY, SOME_END_DATE, pass.getPassNumber(), ONE_PLACE);
    }

    @Test
    public void givenNebulaPass_whenReserveShuttles_thenSpaceXShuttlesAreReserved() {
        Pass pass = createSimplePass(SOME_PASS_OPTION, PassCategory.NEBULA, SOME_START_DATE, SOME_END_DATE);

        pass.reserveShuttles(transportReserver);

        verify(transportReserver).reserveDeparture(ShuttleCategory.SPACE_X, SOME_START_DATE, pass.getPassNumber(), ONE_PLACE);
        verify(transportReserver).reserveArrival(ShuttleCategory.SPACE_X, SOME_END_DATE, pass.getPassNumber(), ONE_PLACE);
    }

    @Test
    public void givenSupergiantPass_whenReserveShuttles_thenMillenniumFalconShuttlesAreReserved() {
        Pass pass = createSimplePass(SOME_PASS_OPTION, PassCategory.SUPERGIANT, SOME_START_DATE, SOME_END_DATE);

        pass.reserveShuttles(transportReserver);

        verify(transportReserver).reserveDeparture(ShuttleCategory.MILLENNIUM_FALCON, SOME_START_DATE, pass.getPassNumber(), ONE_PLACE);
        verify(transportReserver).reserveArrival(ShuttleCategory.MILLENNIUM_FALCON, SOME_END_DATE, pass.getPassNumber(), ONE_PLACE);
    }

    @Test
    public void givenSupernovaPass_whenReserveShuttles_thenEtSpaceshipShuttlesAreReserved() {
        Pass pass = createSimplePass(SOME_PASS_OPTION, PassCategory.SUPERNOVA, SOME_START_DATE, SOME_END_DATE);

        pass.reserveShuttles(transportReserver);

        verify(transportReserver).reserveDeparture(ShuttleCategory.ET_SPACESHIP, SOME_START_DATE, pass.getPassNumber(), ONE_PLACE);
        verify(transportReserver).reserveArrival(ShuttleCategory.ET_SPACESHIP, SOME_END_DATE, pass.getPassNumber(), ONE_PLACE);
    }

    @Test
    public void givenNebulaSinglePass_whenOrderOxygen_thenThreeGradeAOxygenIsOrdered() {
        Pass pass = createSimplePass(PassOption.SINGLE_PASS, PassCategory.NEBULA, BETWEEN_A_FESTIVAL_DATE, BETWEEN_A_FESTIVAL_DATE);

        pass.reserveOxygen(SOME_ORDER_DATE, oxygenReserver);

        OxygenGrade expectedGrade = OxygenGrade.A;
        int expectedQuantity = 3;
        verify(oxygenReserver).reserveOxygen(SOME_ORDER_DATE, expectedGrade, expectedQuantity);
    }

    @Test
    public void givenSupergiantSinglePass_whenOrderOxygen_thenThreeGradeBOxygenIsOrdered() {
        Pass pass = createSimplePass(PassOption.SINGLE_PASS, PassCategory.SUPERGIANT, BETWEEN_A_FESTIVAL_DATE, BETWEEN_A_FESTIVAL_DATE);

        pass.reserveOxygen(SOME_ORDER_DATE, oxygenReserver);

        OxygenGrade expectedGrade = OxygenGrade.B;
        int expectedQuantity = 3;
        verify(oxygenReserver).reserveOxygen(SOME_ORDER_DATE, expectedGrade, expectedQuantity);
    }

    @Test
    public void givenSupernovaSinglePass_whenOrderOxygen_thenFiveGradeEOxygenIsOrdered() {
        Pass pass = createSimplePass(PassOption.SINGLE_PASS, PassCategory.SUPERNOVA, BETWEEN_A_FESTIVAL_DATE, BETWEEN_A_FESTIVAL_DATE);

        pass.reserveOxygen(SOME_ORDER_DATE, oxygenReserver);

        OxygenGrade expectedGrade = OxygenGrade.E;
        int expectedQuantity = 5;
        verify(oxygenReserver).reserveOxygen(SOME_ORDER_DATE, expectedGrade, expectedQuantity);
    }

    @Test
    public void givenNebulaPackagePass_whenOrderOxygen_thenThreeTimesNumberOfDaysGradeAOxygenIsOrdered() {
        Pass pass = createSimplePass(PassOption.SINGLE_PASS, PassCategory.NEBULA, A_FESTIVAL_START, A_FESTIVAL_END);

        pass.reserveOxygen(SOME_ORDER_DATE, oxygenReserver);

        OxygenGrade expectedGrade = OxygenGrade.A;
        int expectedQuantity = 3 * A_NUMBER_OF_FESTIVAL_DAYS;
        verify(oxygenReserver).reserveOxygen(SOME_ORDER_DATE, expectedGrade, expectedQuantity);
    }

    @Test
    public void givenSupergiantPackagePass_whenOrderOxygen_thenThreeTimesNumberOfDaysGradeBOxygenIsOrdered()  {
        Pass pass = createSimplePass(PassOption.PACKAGE, PassCategory.SUPERGIANT, A_FESTIVAL_START, A_FESTIVAL_END);

        pass.reserveOxygen(SOME_ORDER_DATE, oxygenReserver);

        OxygenGrade expectedGrade = OxygenGrade.B;
        int expectedQuantity = 3 * A_NUMBER_OF_FESTIVAL_DAYS;
        verify(oxygenReserver).reserveOxygen(SOME_ORDER_DATE, expectedGrade, expectedQuantity);
    }

    @Test
    public void givenSupernovaPackagePass_whenOrderOxygen_thenFiveTimesNumberOfDaysGradeEOxygenIsOrdered() {
        Pass pass = createSimplePass(PassOption.PACKAGE, PassCategory.SUPERNOVA, A_FESTIVAL_START, A_FESTIVAL_END);

        pass.reserveOxygen(SOME_ORDER_DATE, oxygenReserver);

        OxygenGrade expectedGrade = OxygenGrade.E;
        int expectedQuantity = 5 * A_NUMBER_OF_FESTIVAL_DAYS;
        verify(oxygenReserver).reserveOxygen(SOME_ORDER_DATE, expectedGrade, expectedQuantity);
    }

    private Pass createSimplePass(PassOption passOption, PassCategory passCategory, LocalDate startDate, LocalDate endDate) {
        return new Pass(festivalDates, passNumber, passOption, passCategory, price, startDate, endDate);
    }
}
