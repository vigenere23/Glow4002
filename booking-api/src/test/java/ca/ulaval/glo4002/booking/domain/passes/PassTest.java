package ca.ulaval.glo4002.booking.domain.passes;

import ca.ulaval.glo4002.booking.domain.Price;
import ca.ulaval.glo4002.booking.domain.festivals.FestivalDates;
import ca.ulaval.glo4002.booking.domain.festivals.Glow4002Dates;
import ca.ulaval.glo4002.booking.domain.oxygen.*;
import ca.ulaval.glo4002.booking.domain.transport.ShuttleCategory;
import ca.ulaval.glo4002.booking.domain.transport.ShuttleRepository;
import ca.ulaval.glo4002.booking.domain.transport.TransportReserver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.EnumMap;
import java.util.SortedMap;
import java.util.TreeMap;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PassTest {
    private static final PassOption SOME_PASS_OPTION = PassOption.SINGLE_PASS;
    private static final PassCategory SOME_PASS_CATEGORY = PassCategory.NEBULA;
    private static final ShuttleCategory SOME_SHUTTLE_CATEGORY = ShuttleCategory.SPACE_X;
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
    private static final ShuttleCategory NEBULA_SHUTTLE_CATEGORY = ShuttleCategory.SPACE_X;
    private static final ShuttleCategory SUPERGIANT_SHUTTLE_CATEGORY = ShuttleCategory.MILLENNIUM_FALCON;
    private static final ShuttleCategory SUPERNOVA_SHUTTLE_CATEGORY = ShuttleCategory.ET_SPACESHIP;

    private FestivalDates someFestivalDates;
    private TransportReserver transportReserver;
    private OxygenProducer oxygenProducer;
    private Price price;
    private ShuttleRepository shuttleRepository;
    private OxygenInventoryRepository oxygenInventoryRepository;
    private OxygenHistoryRepository oxygenHistoryRepository;
    private EnumMap<OxygenGrade, OxygenInventory> someOxygenInventories = new EnumMap<>(OxygenGrade.class);
    private SortedMap<LocalDate, OxygenDateHistory> someOxygenHistory = new TreeMap<>();
    
    @BeforeEach
    public void setUp() {
        mockOxygenIventoryRepository();
        mockOxygenHistoryRepository();
        someFestivalDates = new Glow4002Dates();
        price = mock(Price.class);
        transportReserver = mock(TransportReserver.class);
        oxygenProducer = mock(OxygenProducer.class);
    }

    @Test
    public void givenSomePass_whenOrderOxygen_thenGetOxygenInventoriesFromRepository() {
        Pass pass = createPass(SOME_PASS_OPTION, SOME_PASS_CATEGORY, SOME_START_DATE, SOME_END_DATE);

        pass.orderOxygen(SOME_ORDER_DATE, oxygenProducer, oxygenInventoryRepository, oxygenHistoryRepository);

        verify(oxygenInventoryRepository).findInventories();
    }

    @Test
    public void givenSomePass_whenOrderOxygen_thenGetOxygenHistoryFromRepository() {
        Pass pass = createPass(SOME_PASS_OPTION, SOME_PASS_CATEGORY, SOME_START_DATE, SOME_END_DATE);

        pass.orderOxygen(SOME_ORDER_DATE, oxygenProducer, oxygenInventoryRepository, oxygenHistoryRepository);

        verify(oxygenHistoryRepository).findOxygenHistory();
    }

    @Test
    public void givenSomePass_whenOrderOxygen_thenSaveOxygenInventoriesInRepository() {
        Pass pass = createPass(SOME_PASS_OPTION, SOME_PASS_CATEGORY, SOME_START_DATE, SOME_END_DATE);

        pass.orderOxygen(SOME_ORDER_DATE, oxygenProducer, oxygenInventoryRepository, oxygenHistoryRepository);

        verify(oxygenInventoryRepository).saveOxygenInventories(someOxygenInventories);
    }

    @Test
    public void givenSomePass_whenOrderOxygen_thenSaveOxygenHistoryInRepository() {
        Pass pass = createPass(SOME_PASS_OPTION, SOME_PASS_CATEGORY, SOME_START_DATE, SOME_END_DATE);

        pass.orderOxygen(SOME_ORDER_DATE, oxygenProducer, oxygenInventoryRepository, oxygenHistoryRepository);

        verify(oxygenHistoryRepository).saveOxygenHistory(someOxygenHistory);
    }

    @Test
    public void givenSomePass_whenReserveShuttles_thenDepartureShuttlesAreReserved() {
        Pass pass = createPass(SOME_PASS_OPTION, SOME_PASS_CATEGORY, SOME_START_DATE, SOME_END_DATE);
        PassNumber passNumber = pass.getPassNumber();

        pass.reserveShuttles(transportReserver, shuttleRepository);

        verify(transportReserver).reserveDeparture(SOME_SHUTTLE_CATEGORY, SOME_START_DATE, passNumber);
    }

    @Test
    public void givenSomePass_whenReserveShuttles_thenArrivalShuttlesAreReserved() {
        Pass pass = createPass(SOME_PASS_OPTION, SOME_PASS_CATEGORY, SOME_START_DATE, SOME_END_DATE);
        PassNumber passNumber = pass.getPassNumber();

        pass.reserveShuttles(transportReserver, shuttleRepository);

        verify(transportReserver).reserveArrival(SOME_SHUTTLE_CATEGORY, SOME_START_DATE, passNumber);
    }

    @Test
    public void givenNebulaPackagePass_whenReserveShuttles_thenSpaceXShuttlesAreReserved() {
        Pass pass = createPass(PassOption.PACKAGE, PassCategory.NEBULA, FESTIVAL_START, FESTIVAL_END);
        PassNumber passNumber = pass.getPassNumber();

        pass.reserveShuttles(transportReserver, shuttleRepository);

        verify(transportReserver).reserveDeparture(NEBULA_SHUTTLE_CATEGORY, FESTIVAL_START, passNumber);
    }

    @Test
    public void givenNebulaPackagePass_whenOrderOxygen_thenOxygenIsOrdered() {
        Pass pass = createPass(PassOption.PACKAGE, PassCategory.NEBULA, FESTIVAL_START, FESTIVAL_END);

        pass.orderOxygen(SOME_ORDER_DATE, oxygenProducer, oxygenInventoryRepository, oxygenHistoryRepository);

        verify(oxygenProducer).orderOxygen(SOME_ORDER_DATE, NEBULA_OXYGEN_GRADE, NEBULA_OXYGEN_QUANTITY * NUMBER_OF_FESTIVAL_DAYS, someOxygenInventories, someOxygenHistory);
    }

    @Test
    public void givenNebulaSinglePass_whenReserveShuttles_thenSpaceXShuttlesAreReserved() {
        Pass pass = createPass(PassOption.SINGLE_PASS, PassCategory.NEBULA, IN_BETWEEN_FESTIVAL_DATE, IN_BETWEEN_FESTIVAL_DATE);
        PassNumber passNumber = pass.getPassNumber();

        pass.reserveShuttles(transportReserver, shuttleRepository);

        verify(transportReserver).reserveDeparture(NEBULA_SHUTTLE_CATEGORY, IN_BETWEEN_FESTIVAL_DATE, passNumber);
    }

    @Test
    public void givenNebulaSinglePass_whenOrderOxygen_thenOxygenIsOrdered() {
        Pass pass = createPass(PassOption.SINGLE_PASS, PassCategory.NEBULA, IN_BETWEEN_FESTIVAL_DATE, IN_BETWEEN_FESTIVAL_DATE);

        pass.orderOxygen(SOME_ORDER_DATE, oxygenProducer, oxygenInventoryRepository, oxygenHistoryRepository);

        verify(oxygenProducer).orderOxygen(SOME_ORDER_DATE, NEBULA_OXYGEN_GRADE, NEBULA_OXYGEN_QUANTITY, someOxygenInventories, someOxygenHistory);
    }

    @Test
    public void givenSupergiantPackagePass_whenReserveShuttles_thenMillenniumFalconShuttlesAreReserved()  {
        Pass pass = createPass(PassOption.PACKAGE, PassCategory.SUPERGIANT, FESTIVAL_START, FESTIVAL_END);
        PassNumber passNumber = pass.getPassNumber();

        pass.reserveShuttles(transportReserver, shuttleRepository);

        verify(transportReserver).reserveDeparture(SUPERGIANT_SHUTTLE_CATEGORY, FESTIVAL_START, passNumber);
    }

    @Test
    public void givenSupergiantPackagePass_whenOrderOxygen_thenOxygenIsOrdered()  {
        Pass pass = createPass(PassOption.PACKAGE, PassCategory.SUPERGIANT, FESTIVAL_START, FESTIVAL_END);

        pass.orderOxygen(SOME_ORDER_DATE, oxygenProducer, oxygenInventoryRepository, oxygenHistoryRepository);

        verify(oxygenProducer).orderOxygen(SOME_ORDER_DATE, SUPERGIANT_OXYGEN_GRADE, SUPERGIANT_OXYGEN_QUANTITY * NUMBER_OF_FESTIVAL_DAYS, someOxygenInventories, someOxygenHistory);
    }

    @Test
    public void givenSupergiantSinglePass_whenReserveShuttles_thenMillenniumFalconShuttlesAreReserved() {
        Pass pass = createPass(PassOption.SINGLE_PASS, PassCategory.SUPERGIANT, IN_BETWEEN_FESTIVAL_DATE, IN_BETWEEN_FESTIVAL_DATE);
        PassNumber passNumber = pass.getPassNumber();

        pass.reserveShuttles(transportReserver, shuttleRepository);

        verify(transportReserver).reserveDeparture(SUPERGIANT_SHUTTLE_CATEGORY, IN_BETWEEN_FESTIVAL_DATE, passNumber);
    }

    @Test
    public void givenSupergiantSinglePass_whenOrderOxygen_thenOxygenIsOrdered() {
        Pass pass = createPass(PassOption.SINGLE_PASS, PassCategory.SUPERGIANT, IN_BETWEEN_FESTIVAL_DATE, IN_BETWEEN_FESTIVAL_DATE);

        pass.orderOxygen(SOME_ORDER_DATE, oxygenProducer, oxygenInventoryRepository, oxygenHistoryRepository);

        verify(oxygenProducer).orderOxygen(SOME_ORDER_DATE, SUPERGIANT_OXYGEN_GRADE, SUPERGIANT_OXYGEN_QUANTITY, someOxygenInventories, someOxygenHistory);
    }

    @Test
    public void givenSupernovaPackagePass_whenReserveShuttles_thenEtSpaceshipShuttlesAreReserved() {
        Pass pass = createPass(PassOption.PACKAGE, PassCategory.SUPERNOVA, FESTIVAL_START, FESTIVAL_END);
        PassNumber passNumber = pass.getPassNumber();

        pass.reserveShuttles(transportReserver, shuttleRepository);

        verify(transportReserver).reserveDeparture(SUPERNOVA_SHUTTLE_CATEGORY, FESTIVAL_START, passNumber);
    }

    @Test
    public void givenSupernovaPackagePass_whenOrderOxygen_thenOxygenIsOrdered() {
        Pass pass = createPass(PassOption.PACKAGE, PassCategory.SUPERNOVA, FESTIVAL_START, FESTIVAL_END);

        pass.orderOxygen(SOME_ORDER_DATE, oxygenProducer, oxygenInventoryRepository, oxygenHistoryRepository);

        verify(oxygenProducer).orderOxygen(SOME_ORDER_DATE, SUPERNOVA_OXYGEN_GRADE, SUPERNOVA_OXYGEN_QUANTITY * NUMBER_OF_FESTIVAL_DAYS, someOxygenInventories, someOxygenHistory);
    }

    @Test
    public void givenSupernovaSinglePass_whenReserveShuttles_thenEtSpaceshipShuttlesAreReserved() {
        Pass pass = createPass(PassOption.SINGLE_PASS, PassCategory.SUPERNOVA, IN_BETWEEN_FESTIVAL_DATE, IN_BETWEEN_FESTIVAL_DATE);
        PassNumber passNumber = pass.getPassNumber();

        pass.reserveShuttles(transportReserver, shuttleRepository);

        verify(transportReserver).reserveDeparture(SUPERNOVA_SHUTTLE_CATEGORY, IN_BETWEEN_FESTIVAL_DATE, passNumber);
    }

    @Test
    public void givenSupernovaSinglePass_whenOrderOxygen_thenOxygenIsOrdered() {
        Pass pass = createPass(PassOption.SINGLE_PASS, PassCategory.SUPERNOVA, IN_BETWEEN_FESTIVAL_DATE, IN_BETWEEN_FESTIVAL_DATE);

        pass.orderOxygen(SOME_ORDER_DATE, oxygenProducer, oxygenInventoryRepository, oxygenHistoryRepository);

        verify(oxygenProducer).orderOxygen(SOME_ORDER_DATE, SUPERNOVA_OXYGEN_GRADE, SUPERNOVA_OXYGEN_QUANTITY, someOxygenInventories, someOxygenHistory);
    }

    private void mockOxygenIventoryRepository() {
        oxygenInventoryRepository = mock(OxygenInventoryRepository.class);
        when(oxygenInventoryRepository.findInventories()).thenReturn(someOxygenInventories);
    }

    private void mockOxygenHistoryRepository() {
        oxygenHistoryRepository = mock(OxygenHistoryRepository.class);
        when(oxygenHistoryRepository.findOxygenHistory()).thenReturn(someOxygenHistory);
    }

    private Pass createPass(PassOption passOption, PassCategory passCategory, LocalDate start, LocalDate end) {
        return new Pass(someFestivalDates, passOption, passCategory, price, start, end);
    }
}