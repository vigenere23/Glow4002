package ca.ulaval.glo4002.booking.domain.orders;

import ca.ulaval.glo4002.booking.api.dtos.orders.PassRequest;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenGrade;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenRequester;
import ca.ulaval.glo4002.booking.domain.passes.Pass;
import ca.ulaval.glo4002.booking.domain.passes.PassCategory;
import ca.ulaval.glo4002.booking.domain.passes.PassNumber;
import ca.ulaval.glo4002.booking.domain.passes.passTypes.*;
import ca.ulaval.glo4002.booking.domain.transport.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PassUtilitiesTest {
    private static final VendorCode VENDOR_CODE = VendorCode.TEAM;
    private static final LocalDate ORDER_DATE = LocalDate.of(2050, 1, 1);
    private static final OffsetDateTime ORDER_DATE_TIME = OffsetDateTime.of(ORDER_DATE, LocalTime.MIDNIGHT, ZoneOffset.UTC);
    private static final int NUMBER_OF_FESTIVAL_DAYS = 5;
    private static final LocalDate FESTIVAL_START = ORDER_DATE.plusMonths(12);
    private static final LocalDate FESTIVAL_END = FESTIVAL_START.plusDays(NUMBER_OF_FESTIVAL_DAYS - 1);
    private static final LocalDate IN_BETWEEN_FESTIVAL_DATE = FESTIVAL_START.plusDays(1);
    private static final PassNumber PASS_ID = mock(PassNumber.class);

    private static final int NEBULA_OXYGEN_QUANTITY = 3;
    private static final int SUPERGIANT_OXYGEN_QUANTITY = 3;
    private static final int SUPERNOVA_OXYGEN_QUANTITY = 5;
    private static final OxygenGrade NEBULA_OXYGEN_GRADE = OxygenGrade.A;
    private static final OxygenGrade SUPERGIANT_OXYGEN_GRADE = OxygenGrade.B;
    private static final OxygenGrade SUPERNOVA_OXYGEN_GRADE = OxygenGrade.E;
    private static final ShuttleCategory NEBULA_SHUTTLE_CATEGORY = ShuttleCategory.SPACE_X;
    private static final ShuttleCategory SUPERGIANT_SHUTTLE_CATEGORY = ShuttleCategory.MILLENNIUM_FALCON;
    private static final ShuttleCategory SUPERNOVA_SHUTTLE_CATEGORY = ShuttleCategory.ET_SPACESHIP;
    private List<Shuttle> shuttlesEarth = new LinkedList<>();
    private List<Shuttle> shuttlesUlavalogy = new LinkedList<>();
    private TransportReservation transportReservation;
    private OxygenRequester oxygenRequester;
    private PassOrderFactory passOrderFactory;
    private PassOrder passOrder;
    private PassRequest passRequest;
    private PassUtilities passUtilities;

    @BeforeEach
    public void setUp() {
        mockShuttles();
        transportReservation = mock(TransportReservation.class);
        oxygenRequester = mock(OxygenRequester.class);
        passOrder = mock(PassOrder.class);
        passOrderFactory = mock(PassOrderFactory.class);
        when(passOrderFactory.create(any(), any(), any())).thenReturn(passOrder);
        passRequest = mock(PassRequest.class);
        passUtilities = new PassUtilities(transportReservation, oxygenRequester, passOrderFactory);
    }
    
    @Test
    public void whenOrderAPass_thenPassOrderIsCreated() {
        passUtilities.orderPasses(ORDER_DATE_TIME, VENDOR_CODE, passRequest, shuttlesEarth, shuttlesUlavalogy);

        verify(passOrderFactory).create(ORDER_DATE_TIME, VENDOR_CODE, passRequest);
    }

    @Test
    public void givenFestivalOf5Days_whenOrderANebulaPackagePass_thenShuttlesAreReserved() {
        mockPass(mock(NebulaPackagePass.class), PassCategory.NEBULA, FESTIVAL_START, FESTIVAL_END);

        passUtilities.orderPasses(ORDER_DATE_TIME, VENDOR_CODE, passRequest, shuttlesEarth, shuttlesUlavalogy);

        verify(transportReservation).reserveShuttles(NEBULA_SHUTTLE_CATEGORY, FESTIVAL_START, PASS_ID, shuttlesEarth, shuttlesUlavalogy);
    }

    @Test
    public void givenFestivalOf5Days_whenOrderANebulaPackagePass_thenOxygenIsOrdered() {
        mockPass(mock(NebulaPackagePass.class), PassCategory.NEBULA, FESTIVAL_START, FESTIVAL_END);

        passUtilities.orderPasses(ORDER_DATE_TIME, VENDOR_CODE, passRequest, shuttlesEarth, shuttlesUlavalogy);

        verify(oxygenRequester).orderOxygen(ORDER_DATE, NEBULA_OXYGEN_GRADE, NEBULA_OXYGEN_QUANTITY * NUMBER_OF_FESTIVAL_DAYS);
    }

    @Test
    public void whenOrderANebulaSinglePass_thenShuttlesAreReserved() {
        mockPass(mock(NebulaSinglePass.class), PassCategory.NEBULA, IN_BETWEEN_FESTIVAL_DATE, IN_BETWEEN_FESTIVAL_DATE);

        passUtilities.orderPasses(ORDER_DATE_TIME, VENDOR_CODE, passRequest, shuttlesEarth, shuttlesUlavalogy);

        verify(transportReservation).reserveShuttles(NEBULA_SHUTTLE_CATEGORY, IN_BETWEEN_FESTIVAL_DATE, PASS_ID, shuttlesEarth, shuttlesUlavalogy);
    }

    @Test
    public void whenOrderANebulaSinglePass_thenOxygenIsOrdered() {
        mockPass(mock(NebulaSinglePass.class), PassCategory.NEBULA, IN_BETWEEN_FESTIVAL_DATE, IN_BETWEEN_FESTIVAL_DATE);

        passUtilities.orderPasses(ORDER_DATE_TIME, VENDOR_CODE, passRequest, shuttlesEarth, shuttlesUlavalogy);

        verify(oxygenRequester).orderOxygen(ORDER_DATE, NEBULA_OXYGEN_GRADE, NEBULA_OXYGEN_QUANTITY);
    }

    @Test
    public void givenFestivalOf5Days_whenOrderASupergiantPackagePass_thenShuttlesAreReserved()  {
        mockPass(mock(SupergiantPackagePass.class), PassCategory.SUPERGIANT, FESTIVAL_START, FESTIVAL_END);

        passUtilities.orderPasses(ORDER_DATE_TIME, VENDOR_CODE, passRequest, shuttlesEarth, shuttlesUlavalogy);

        verify(transportReservation).reserveShuttles(SUPERGIANT_SHUTTLE_CATEGORY, FESTIVAL_START, PASS_ID, shuttlesEarth, shuttlesUlavalogy);
    }

    @Test
    public void givenFestivalOf5Days_whenOrderASupergiantPackagePass_thenOxygenIsOrdered()  {
        mockPass(mock(SupergiantPackagePass.class), PassCategory.SUPERGIANT, FESTIVAL_START, FESTIVAL_END);

        passUtilities.orderPasses(ORDER_DATE_TIME, VENDOR_CODE, passRequest, shuttlesEarth, shuttlesUlavalogy);

        verify(oxygenRequester).orderOxygen(ORDER_DATE, SUPERGIANT_OXYGEN_GRADE, SUPERGIANT_OXYGEN_QUANTITY * NUMBER_OF_FESTIVAL_DAYS);
    }

    @Test
    public void whenOrderASupergiantSinglePass_thenShuttlesAreReserved() {
        mockPass(mock(SupergiantSinglePass.class), PassCategory.SUPERGIANT, IN_BETWEEN_FESTIVAL_DATE, IN_BETWEEN_FESTIVAL_DATE);

        passUtilities.orderPasses(ORDER_DATE_TIME, VENDOR_CODE, passRequest, shuttlesEarth, shuttlesUlavalogy);

        verify(transportReservation).reserveShuttles(SUPERGIANT_SHUTTLE_CATEGORY, IN_BETWEEN_FESTIVAL_DATE, PASS_ID, shuttlesEarth, shuttlesUlavalogy);
    }

    @Test
    public void whenOrderASupergiantSinglePass_thenOxygenIsOrdered() {
        mockPass(mock(SupergiantSinglePass.class), PassCategory.SUPERGIANT, IN_BETWEEN_FESTIVAL_DATE, IN_BETWEEN_FESTIVAL_DATE);

        passUtilities.orderPasses(ORDER_DATE_TIME, VENDOR_CODE, passRequest, shuttlesEarth, shuttlesUlavalogy);

        verify(oxygenRequester).orderOxygen(ORDER_DATE, SUPERGIANT_OXYGEN_GRADE, SUPERGIANT_OXYGEN_QUANTITY);
    }

    @Test
    public void givenFestivalOf5Days_whenOrderASupernovaPackagePass_thenShuttlesAreReserved() {
        mockPass(mock(SupernovaPackagePass.class), PassCategory.SUPERNOVA, FESTIVAL_START, FESTIVAL_END);

        passUtilities.orderPasses(ORDER_DATE_TIME, VENDOR_CODE, passRequest, shuttlesEarth, shuttlesUlavalogy);

        verify(transportReservation).reserveShuttles(SUPERNOVA_SHUTTLE_CATEGORY, FESTIVAL_START, PASS_ID, shuttlesEarth, shuttlesUlavalogy);
    }

    @Test
    public void givenFestivalOf5Days_whenOrderASupernovaPackagePass_thenOxygenIsOrdered() {
        mockPass(mock(SupernovaPackagePass.class), PassCategory.SUPERNOVA, FESTIVAL_START, FESTIVAL_END);

        passUtilities.orderPasses(ORDER_DATE_TIME, VENDOR_CODE, passRequest, shuttlesEarth, shuttlesUlavalogy);

        verify(oxygenRequester).orderOxygen(ORDER_DATE, SUPERNOVA_OXYGEN_GRADE, SUPERNOVA_OXYGEN_QUANTITY * NUMBER_OF_FESTIVAL_DAYS);
    }

    @Test
    public void whenOrderASupernovaSinglePass_thenShuttlesAreReserved() {
        mockPass(mock(SupernovaSinglePass.class), PassCategory.SUPERNOVA, IN_BETWEEN_FESTIVAL_DATE, IN_BETWEEN_FESTIVAL_DATE);

        passUtilities.orderPasses(ORDER_DATE_TIME, VENDOR_CODE, passRequest, shuttlesEarth, shuttlesUlavalogy);

        verify(transportReservation).reserveShuttles(SUPERNOVA_SHUTTLE_CATEGORY, IN_BETWEEN_FESTIVAL_DATE, PASS_ID, shuttlesEarth, shuttlesUlavalogy);
    }

    @Test
    public void whenOrderASupernovaSinglePass_thenOxygenIsOrdered() {
        mockPass(mock(SupernovaSinglePass.class), PassCategory.SUPERNOVA, IN_BETWEEN_FESTIVAL_DATE, IN_BETWEEN_FESTIVAL_DATE);

        passUtilities.orderPasses(ORDER_DATE_TIME, VENDOR_CODE, passRequest, shuttlesEarth, shuttlesUlavalogy);

        verify(oxygenRequester).orderOxygen(ORDER_DATE, SUPERNOVA_OXYGEN_GRADE, SUPERNOVA_OXYGEN_QUANTITY);
    }

    private void mockShuttles() {
        Shuttle mockedShuttle = mock(SpaceX.class);

        shuttlesEarth.add(mockedShuttle);
        shuttlesUlavalogy.add(mockedShuttle);
    }

    private void mockPass(Pass pass, PassCategory passCategory, LocalDate start, LocalDate end) {
        List<Pass> passes = new ArrayList<>();
        when(pass.getStartDate()).thenReturn(start);
        when(pass.getEndDate()).thenReturn(end);
        when(pass.getPassNumber()).thenReturn(PASS_ID);
        when(pass.getPassCategory()).thenReturn(passCategory);
        passes.add(pass);
        when(passOrder.getPasses()).thenReturn(passes);
    }
}