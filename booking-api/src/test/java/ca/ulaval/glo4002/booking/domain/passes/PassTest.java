package ca.ulaval.glo4002.booking.domain.passes;

import ca.ulaval.glo4002.booking.domain.Price;
import ca.ulaval.glo4002.booking.domain.festivals.FestivalDates;
import ca.ulaval.glo4002.booking.domain.festivals.Glow4002Dates;
import ca.ulaval.glo4002.booking.domain.orders.VendorCode;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenGrade;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenRequester;
import ca.ulaval.glo4002.booking.domain.transport.*;
import ca.ulaval.glo4002.booking.infrastructure.persistance.heap.HeapShuttleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

class PassTest {
    private static final PassOption SOME_PASS_OPTION = PassOption.SINGLE_PASS;
    private static final PassCategory SOME_PASS_CATEGORY = PassCategory.NEBULA;
    private static final VendorCode SOME_VENDOR_CODE = VendorCode.TEAM;
    private static final LocalDate SOME_DATE = LocalDate.of(2050, 1, 1);
    private static final OffsetDateTime SOME_ORDER_DATE = OffsetDateTime.of(SOME_DATE, LocalTime.MIDNIGHT, ZoneOffset.UTC);
    private static final LocalDate SOME_START_DATE = LocalDate.of(2050, 7,18);
    private static final LocalDate SOME_END_DATE = LocalDate.of(2050, 7,20);
    private static final int NUMBER_OF_FESTIVAL_DAYS = 5;
    private static final LocalDate FESTIVAL_START = SOME_DATE.plusMonths(12);
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

    private FestivalDates someFestivalDates;
    private TransportReservation transportReservation;
    private OxygenRequester oxygenRequester;
    private List<Shuttle> shuttlesEarth = new LinkedList<>();
    private List<Shuttle> shuttlesUlavalogy = new LinkedList<>();
    private Price price;
    private ShuttleRepository shuttleRepository;
    
    @BeforeEach
    public void setUp() {
        mockShuttles();
        mockShuttleRepository();
        mockTransportReservation();
        someFestivalDates = new Glow4002Dates();
        price = mock(Price.class);
        transportReservation = mock(TransportReservation.class);
        oxygenRequester = mock(OxygenRequester.class);
    }

    // TODO terminer les tests
    
//    @Test
//    public void givenSomePass_whenReserveShuttles_thenGetDepartureShuttleFromRepository() {
//        Pass pass = createPass(SOME_PASS_OPTION, SOME_PASS_CATEGORY, SOME_START_DATE, SOME_END_DATE);
//
//        pass.reserveShuttles(transportReservation, shuttleRepository);
//
//        verify(shuttleRepository).findShuttlesByLocation(Location.EARTH);
//    }
//
//    @Test
//    public void givenSomePass_whenReserveShuttles_thenSaveDepartureShuttleInRepository() {
//        Pass pass = createPass(SOME_PASS_OPTION, SOME_PASS_CATEGORY, SOME_START_DATE, SOME_END_DATE);
//
//        pass.reserveShuttles(transportReservation, shuttleRepository);
//
//        verify(shuttleRepository).saveDeparture(shuttlesEarth);
//    }
//
//    @Test
//    public void givenSomePass_whenReserveShuttles_thenGetArrivalShuttleFromRepository() {
//        Pass pass = createPass(SOME_PASS_OPTION, SOME_PASS_CATEGORY, SOME_START_DATE, SOME_END_DATE);
//
//        pass.reserveShuttles(transportReservation, shuttleRepository);
//
//        verify(shuttleRepository).findShuttlesByLocation(Location.ULAVALOGY);
//    }
//
//    @Test
//    public void givenSomePass_whenReserveShuttles_thenSaveArrivalShuttleInRepository() {
//        Pass pass = createPass(SOME_PASS_OPTION, SOME_PASS_CATEGORY, SOME_START_DATE, SOME_END_DATE);
//
//        pass.reserveShuttles(transportReservation, shuttleRepository);
//
//        verify(shuttleRepository).saveArrival(shuttlesUlavalogy);
//    }

//    @Test
//    public void givenFestivalOf5Days_whenOrderANebulaPackagePass_thenShuttlesAreReserved() {
//        Pass pass = createPass(PassOption.PACKAGE, PassCategory.NEBULA, FESTIVAL_START, FESTIVAL_END);
//
//        pass.orderPasses(ORDER_DATE_TIME, SOME_VENDOR_CODE, passRequest, shuttlesEarth, shuttlesUlavalogy);
//
//        verify(transportReservation).reserveShuttles(NEBULA_SHUTTLE_CATEGORY, FESTIVAL_START, PASS_ID, shuttlesEarth, shuttlesUlavalogy);
//    }
//
//    @Test
//    public void givenFestivalOf5Days_whenOrderANebulaPackagePass_thenOxygenIsOrdered() {
//        Pass pass = createPass(PassOption.PACKAGE, PassCategory.NEBULA, FESTIVAL_START, FESTIVAL_END);
//
//        pass.orderPasses(ORDER_DATE_TIME, SOME_VENDOR_CODE, passRequest, shuttlesEarth, shuttlesUlavalogy);
//
//        verify(oxygenRequester).orderOxygen(ORDER_DATE, NEBULA_OXYGEN_GRADE, NEBULA_OXYGEN_QUANTITY * NUMBER_OF_FESTIVAL_DAYS);
//    }
//
//    @Test
//    public void whenOrderANebulaSinglePass_thenShuttlesAreReserved() {
//        Pass pass = createPass(PassOption.SINGLE_PASS, PassCategory.NEBULA, IN_BETWEEN_FESTIVAL_DATE, IN_BETWEEN_FESTIVAL_DATE);
//
//        pass.orderPasses(ORDER_DATE_TIME, SOME_VENDOR_CODE, passRequest, shuttlesEarth, shuttlesUlavalogy);
//
//        verify(transportReservation).reserveShuttles(NEBULA_SHUTTLE_CATEGORY, IN_BETWEEN_FESTIVAL_DATE, PASS_ID, shuttlesEarth, shuttlesUlavalogy);
//    }
//
//    @Test
//    public void whenOrderANebulaSinglePass_thenOxygenIsOrdered() {
//        Pass pass = createPass(PassOption.SINGLE_PASS, PassCategory.NEBULA, IN_BETWEEN_FESTIVAL_DATE, IN_BETWEEN_FESTIVAL_DATE);
//
//        pass.orderPasses(ORDER_DATE_TIME, SOME_VENDOR_CODE, passRequest, shuttlesEarth, shuttlesUlavalogy);
//
//        verify(oxygenRequester).orderOxygen(ORDER_DATE, NEBULA_OXYGEN_GRADE, NEBULA_OXYGEN_QUANTITY);
//    }
//
//    @Test
//    public void givenFestivalOf5Days_whenOrderASupergiantPackagePass_thenShuttlesAreReserved()  {
//        Pass pass = createPass(PassOption.PACKAGE, PassCategory.SUPERGIANT, FESTIVAL_START, FESTIVAL_END);
//
//        pass.orderPasses(ORDER_DATE_TIME, SOME_VENDOR_CODE, passRequest, shuttlesEarth, shuttlesUlavalogy);
//
//        verify(transportReservation).reserveShuttles(SUPERGIANT_SHUTTLE_CATEGORY, FESTIVAL_START, PASS_ID, shuttlesEarth, shuttlesUlavalogy);
//    }
//
//    @Test
//    public void givenFestivalOf5Days_whenOrderASupergiantPackagePass_thenOxygenIsOrdered()  {
//        Pass pass = createPass(PassOption.PACKAGE, PassCategory.SUPERGIANT, FESTIVAL_START, FESTIVAL_END);
//
//        pass.orderPasses(ORDER_DATE_TIME, SOME_VENDOR_CODE, passRequest, shuttlesEarth, shuttlesUlavalogy);
//
//        verify(oxygenRequester).orderOxygen(ORDER_DATE, SUPERGIANT_OXYGEN_GRADE, SUPERGIANT_OXYGEN_QUANTITY * NUMBER_OF_FESTIVAL_DAYS);
//    }
//
//    @Test
//    public void whenOrderASupergiantSinglePass_thenShuttlesAreReserved() {
//        Pass pass = createPass(PassOption.SINGLE_PASS, PassCategory.SUPERGIANT, IN_BETWEEN_FESTIVAL_DATE, IN_BETWEEN_FESTIVAL_DATE);
//
//        pass.orderPasses(ORDER_DATE_TIME, SOME_VENDOR_CODE, passRequest, shuttlesEarth, shuttlesUlavalogy);
//
//        verify(transportReservation).reserveShuttles(SUPERGIANT_SHUTTLE_CATEGORY, IN_BETWEEN_FESTIVAL_DATE, PASS_ID, shuttlesEarth, shuttlesUlavalogy);
//    }
//
//    @Test
//    public void whenOrderASupergiantSinglePass_thenOxygenIsOrdered() {
//        Pass pass = createPass(PassOption.SINGLE_PASS, PassCategory.SUPERGIANT, IN_BETWEEN_FESTIVAL_DATE, IN_BETWEEN_FESTIVAL_DATE);
//
//        pass.orderPasses(ORDER_DATE_TIME, SOME_VENDOR_CODE, passRequest, shuttlesEarth, shuttlesUlavalogy);
//
//        verify(oxygenRequester).orderOxygen(ORDER_DATE, SUPERGIANT_OXYGEN_GRADE, SUPERGIANT_OXYGEN_QUANTITY);
//    }
//
//    @Test
//    public void givenFestivalOf5Days_whenOrderASupernovaPackagePass_thenShuttlesAreReserved() {
//        Pass pass = createPass(PassOption.PACKAGE, PassCategory.SUPERNOVA, FESTIVAL_START, FESTIVAL_END);
//
//        pass.orderPasses(ORDER_DATE_TIME, SOME_VENDOR_CODE, passRequest, shuttlesEarth, shuttlesUlavalogy);
//
//        verify(transportReservation).reserveShuttles(SUPERNOVA_SHUTTLE_CATEGORY, FESTIVAL_START, PASS_ID, shuttlesEarth, shuttlesUlavalogy);
//    }
//
//    @Test
//    public void givenFestivalOf5Days_whenOrderASupernovaPackagePass_thenOxygenIsOrdered() {
//        Pass pass = createPass(PassOption.PACKAGE, PassCategory.SUPERNOVA, FESTIVAL_START, FESTIVAL_END);
//
//        pass.orderPasses(ORDER_DATE_TIME, SOME_VENDOR_CODE, passRequest, shuttlesEarth, shuttlesUlavalogy);
//
//        verify(oxygenRequester).orderOxygen(ORDER_DATE, SUPERNOVA_OXYGEN_GRADE, SUPERNOVA_OXYGEN_QUANTITY * NUMBER_OF_FESTIVAL_DAYS);
//    }
//
//    @Test
//    public void whenOrderASupernovaSinglePass_thenShuttlesAreReserved() {
//        Pass pass = createPass(PassOption.SINGLE_PASS, PassCategory.SUPERNOVA, IN_BETWEEN_FESTIVAL_DATE, IN_BETWEEN_FESTIVAL_DATE);
//
//        pass.orderPasses(ORDER_DATE_TIME, SOME_VENDOR_CODE, passRequest, shuttlesEarth, shuttlesUlavalogy);
//
//        verify(transportReservation).reserveShuttles(SUPERNOVA_SHUTTLE_CATEGORY, IN_BETWEEN_FESTIVAL_DATE, PASS_ID, shuttlesEarth, shuttlesUlavalogy);
//    }
//
//    @Test
//    public void whenOrderASupernovaSinglePass_thenOxygenIsOrdered() {
//        Pass pass = createPass(PassOption.SINGLE_PASS, PassCategory.SUPERNOVA, IN_BETWEEN_FESTIVAL_DATE, IN_BETWEEN_FESTIVAL_DATE);
//
//        pass.orderPasses(ORDER_DATE_TIME, SOME_VENDOR_CODE, passRequest, shuttlesEarth, shuttlesUlavalogy);
//
//        verify(oxygenRequester).orderOxygen(ORDER_DATE, SUPERNOVA_OXYGEN_GRADE, SUPERNOVA_OXYGEN_QUANTITY);
//    }

    private void mockShuttles() {
        Shuttle mockedShuttle = mock(SpaceX.class);

        shuttlesEarth.add(mockedShuttle);
        shuttlesUlavalogy.add(mockedShuttle);
    }

    private void mockTransportReservation() {
        transportReservation = mock(TransportReservation.class);
    }

    private void mockShuttleRepository() {
        shuttleRepository = mock(HeapShuttleRepository.class);

        when(shuttleRepository.findShuttlesByLocation(Location.EARTH)).thenReturn(shuttlesEarth);
        when(shuttleRepository.findShuttlesByLocation(Location.ULAVALOGY)).thenReturn(shuttlesUlavalogy);
    }
    
    private Pass createPass(PassOption passOption, PassCategory passCategory, LocalDate start, LocalDate end) {
        return new Pass(someFestivalDates, passOption, passCategory, price, start, end);
    }
}