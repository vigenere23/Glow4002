package ca.ulaval.glo4002.booking.domain.passes;

import ca.ulaval.glo4002.booking.domain.Price;
import ca.ulaval.glo4002.booking.domain.festivals.FestivalDates;
import ca.ulaval.glo4002.booking.domain.festivals.Glow4002Dates;
import ca.ulaval.glo4002.booking.domain.oxygen.*;
import ca.ulaval.glo4002.booking.domain.transport.ShuttleCategory;
import ca.ulaval.glo4002.booking.domain.transport.TransportReserver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.EnumMap;
import java.util.SortedMap;
import java.util.TreeMap;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PassTest {
    private static final PassOption SOME_PASS_OPTION = PassOption.SINGLE_PASS;
    private static final PassCategory SOME_PASS_CATEGORY = PassCategory.NEBULA;
    private static final ShuttleCategory SOME_SHUTTLE_CATEGORY = ShuttleCategory.SPACE_X;
    private static final OxygenGrade SOME_OXYGEN_GRADE = OxygenGrade.A;
    private static final int SOME_OXYGEN_QUANTITY = 3;
    private static final LocalDate SOME_ORDER_DATE = LocalDate.of(2050, 1, 1);
    private static final LocalDate SOME_START_DATE = LocalDate.of(2050, 7,18);
    private static final LocalDate SOME_END_DATE = SOME_START_DATE.plusDays(3);
    private static final int NUMBER_OF_FESTIVAL_DAYS = 5;
    private static final LocalDate FESTIVAL_START = LocalDate.of(2050, 7,17);
    private static final LocalDate FESTIVAL_END = FESTIVAL_START.plusDays(NUMBER_OF_FESTIVAL_DAYS - 1);
    private static final LocalDate IN_BETWEEN_FESTIVAL_DATE = FESTIVAL_START.plusDays(1);
    private static final int NEBULA_OXYGEN_QUANTITY = 3;
    private static final int SUPERGIANT_OXYGEN_QUANTITY = 3;
    private static final int SUPERNOVA_OXYGEN_QUANTITY = 5;
    private static final OxygenGrade NEBULA_OXYGEN_GRADE = OxygenGrade.A;
    private static final OxygenGrade SUPERGIANT_OXYGEN_GRADE = OxygenGrade.B;
    private static final OxygenGrade SUPERNOVA_OXYGEN_GRADE = OxygenGrade.E;

    private FestivalDates festivalDates;
    private TransportReserver transportReserver;
    private OxygenReserver oxygenReserver;
    private Price price;
    private OxygenInventoryRepository oxygenInventoryRepository;
    private OxygenHistoryRepository oxygenHistoryRepository;
    private EnumMap<OxygenGrade, OxygenInventory> someOxygenInventories = new EnumMap<>(OxygenGrade.class);
    private SortedMap<LocalDate, OxygenDateHistory> someOxygenHistory = new TreeMap<>();
    
    @BeforeEach
    public void setUp() {
        mockOxygenIventoryRepository();
        mockOxygenHistoryRepository();
        festivalDates = new Glow4002Dates();
        price = mock(Price.class);
        transportReserver = mock(TransportReserver.class);
        oxygenReserver = mock(OxygenReserver.class);
    }

    @Test
    public void whenReservingShuttles_thenItReservesASingleDeparture() {
        Pass pass = createSimplePass(PassOption.SINGLE_PASS, SOME_PASS_CATEGORY, SOME_START_DATE, SOME_END_DATE);
        pass.reserveShuttles(transportReserver);
        verify(transportReserver, times(1)).reserveDeparture(any(ShuttleCategory.class), any(LocalDate.class), any(PassNumber.class));
    }

    @Test
    public void whenReservingShuttles_thenItReservesASingleArrival() {
        Pass pass = createSimplePass(PassOption.SINGLE_PASS, SOME_PASS_CATEGORY, SOME_START_DATE, SOME_END_DATE);
        pass.reserveShuttles(transportReserver);
        verify(transportReserver, times(1)).reserveArrival(any(ShuttleCategory.class), any(LocalDate.class), any(PassNumber.class));
    }

    @Test
    public void whenReservingShuttles_thenDepartureShuttlesAreReservedWithStartDateAndPassNumber() {
        Pass pass = createSimplePass(PassOption.SINGLE_PASS, SOME_PASS_CATEGORY, SOME_START_DATE, SOME_END_DATE);
        pass.reserveShuttles(transportReserver);
        verify(transportReserver).reserveDeparture(SOME_SHUTTLE_CATEGORY, SOME_START_DATE, pass.getPassNumber());
    }

    @Test
    public void whenReserveShuttles_thenArrivalShuttlesAreReservedWithEndDateAndPassNumber() {
        Pass pass = createSimplePass(PassOption.SINGLE_PASS, SOME_PASS_CATEGORY, SOME_START_DATE, SOME_END_DATE);
        pass.reserveShuttles(transportReserver);
        verify(transportReserver).reserveArrival(SOME_SHUTTLE_CATEGORY, SOME_END_DATE, pass.getPassNumber());
    }

    @Test
    public void givenNebulaPass_whenReserveShuttles_thenSpaceXShuttlesAreReserved() {
        Pass pass = createSimplePass(SOME_PASS_OPTION, PassCategory.NEBULA, SOME_START_DATE, SOME_END_DATE);

        pass.reserveShuttles(transportReserver);

        verify(transportReserver).reserveDeparture(ShuttleCategory.SPACE_X, SOME_START_DATE, pass.getPassNumber());
        verify(transportReserver).reserveArrival(ShuttleCategory.SPACE_X, SOME_END_DATE, pass.getPassNumber());
    }

    @Test
    public void givenSupergiantPass_whenReserveShuttles_thenMillenniumFalconShuttlesAreReserved() {
        Pass pass = createSimplePass(SOME_PASS_OPTION, PassCategory.SUPERGIANT, SOME_START_DATE, SOME_END_DATE);

        pass.reserveShuttles(transportReserver);

        verify(transportReserver).reserveDeparture(ShuttleCategory.MILLENNIUM_FALCON, SOME_START_DATE, pass.getPassNumber());
        verify(transportReserver).reserveArrival(ShuttleCategory.MILLENNIUM_FALCON, SOME_END_DATE, pass.getPassNumber());
    }

    @Test
    public void givenSupernovaPass_whenReserveShuttles_thenEtSpaceshipShuttlesAreReserved() {
        Pass pass = createSimplePass(SOME_PASS_OPTION, PassCategory.SUPERNOVA, SOME_START_DATE, SOME_END_DATE);

        pass.reserveShuttles(transportReserver);

        verify(transportReserver).reserveDeparture(ShuttleCategory.ET_SPACESHIP, SOME_START_DATE, pass.getPassNumber());
        verify(transportReserver).reserveArrival(ShuttleCategory.ET_SPACESHIP, SOME_END_DATE, pass.getPassNumber());
    }

    @Test
    public void givenNebulaPackagePass_whenOrderOxygen_thenOxygenIsOrdered() {
        Pass pass = createSimplePass(PassOption.PACKAGE, PassCategory.NEBULA, FESTIVAL_START, FESTIVAL_END);

        pass.reserveOxygen(SOME_ORDER_DATE, oxygenReserver);

        verify(oxygenReserver).reserveOxygen(SOME_ORDER_DATE, NEBULA_OXYGEN_GRADE, NEBULA_OXYGEN_QUANTITY * NUMBER_OF_FESTIVAL_DAYS);
    }

    @Test
    public void givenNebulaSinglePass_whenOrderOxygen_thenOxygenIsOrdered() {
        Pass pass = createSimplePass(PassOption.SINGLE_PASS, PassCategory.NEBULA, IN_BETWEEN_FESTIVAL_DATE, IN_BETWEEN_FESTIVAL_DATE);

        pass.reserveOxygen(SOME_ORDER_DATE, oxygenReserver);

        verify(oxygenReserver).reserveOxygen(SOME_ORDER_DATE, NEBULA_OXYGEN_GRADE, NEBULA_OXYGEN_QUANTITY);
    }

    @Test
    public void givenSupergiantPackagePass_whenOrderOxygen_thenOxygenIsOrdered()  {
        Pass pass = createSimplePass(PassOption.PACKAGE, PassCategory.SUPERGIANT, FESTIVAL_START, FESTIVAL_END);

        pass.reserveOxygen(SOME_ORDER_DATE, oxygenReserver);

        verify(oxygenReserver).reserveOxygen(SOME_ORDER_DATE, SUPERGIANT_OXYGEN_GRADE, SUPERGIANT_OXYGEN_QUANTITY * NUMBER_OF_FESTIVAL_DAYS);
    }

    @Test
    public void givenSupergiantSinglePass_whenOrderOxygen_thenOxygenIsOrdered() {
        Pass pass = createSimplePass(PassOption.SINGLE_PASS, PassCategory.SUPERGIANT, IN_BETWEEN_FESTIVAL_DATE, IN_BETWEEN_FESTIVAL_DATE);

        pass.reserveOxygen(SOME_ORDER_DATE, oxygenReserver);

        verify(oxygenReserver).reserveOxygen(SOME_ORDER_DATE, SUPERGIANT_OXYGEN_GRADE, SUPERGIANT_OXYGEN_QUANTITY);
    }

    @Test
    public void givenSupernovaPackagePass_whenOrderOxygen_thenOxygenIsOrdered() {
        Pass pass = createSimplePass(PassOption.PACKAGE, PassCategory.SUPERNOVA, FESTIVAL_START, FESTIVAL_END);

        pass.reserveOxygen(SOME_ORDER_DATE, oxygenReserver);

        verify(oxygenReserver).reserveOxygen(SOME_ORDER_DATE, SUPERNOVA_OXYGEN_GRADE, SUPERNOVA_OXYGEN_QUANTITY * NUMBER_OF_FESTIVAL_DAYS);
    }

    @Test
    public void givenSupernovaSinglePass_whenOrderOxygen_thenOxygenIsOrdered() {
        Pass pass = createSimplePass(PassOption.SINGLE_PASS, PassCategory.SUPERNOVA, IN_BETWEEN_FESTIVAL_DATE, IN_BETWEEN_FESTIVAL_DATE);

        pass.reserveOxygen(SOME_ORDER_DATE, oxygenReserver);

        verify(oxygenReserver).reserveOxygen(SOME_ORDER_DATE, SUPERNOVA_OXYGEN_GRADE, SUPERNOVA_OXYGEN_QUANTITY);
    }

    private void mockOxygenIventoryRepository() {
        oxygenInventoryRepository = mock(OxygenInventoryRepository.class);
        when(oxygenInventoryRepository.findAll()).thenReturn(someOxygenInventories);
    }

    private void mockOxygenHistoryRepository() {
        oxygenHistoryRepository = mock(OxygenHistoryRepository.class);
        when(oxygenHistoryRepository.findOxygenHistory()).thenReturn(someOxygenHistory);
    }

    private Pass createSimplePass(PassOption passOption, PassCategory passCategory, LocalDate startDate, LocalDate endDate) {
        return new Pass(festivalDates, passOption, passCategory, price, startDate, endDate);
    }
}
