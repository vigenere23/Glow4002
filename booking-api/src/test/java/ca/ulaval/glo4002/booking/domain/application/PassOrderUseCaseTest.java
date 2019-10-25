package ca.ulaval.glo4002.booking.domain.application;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import ca.ulaval.glo4002.booking.application.PassOrderUseCase;
import ca.ulaval.glo4002.booking.domain.orders.PassOrderFactory;
import ca.ulaval.glo4002.booking.domain.orders.PassOrderRepository;
import ca.ulaval.glo4002.booking.domain.oxygen.*;
import ca.ulaval.glo4002.booking.infrastructure.persistance.heap.HeapPassOrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.api.dtos.orders.PassRequest;
import ca.ulaval.glo4002.booking.domain.orders.PassOrder;
import ca.ulaval.glo4002.booking.domain.orders.VendorCode;
import ca.ulaval.glo4002.booking.domain.passes.Pass;
import ca.ulaval.glo4002.booking.domain.passes.PassCategory;
import ca.ulaval.glo4002.booking.domain.passes.PassNumber;
import ca.ulaval.glo4002.booking.domain.passes.passTypes.NebulaPackagePass;
import ca.ulaval.glo4002.booking.domain.passes.passTypes.NebulaSinglePass;
import ca.ulaval.glo4002.booking.domain.passes.passTypes.SupergiantPackagePass;
import ca.ulaval.glo4002.booking.domain.passes.passTypes.SupergiantSinglePass;
import ca.ulaval.glo4002.booking.domain.passes.passTypes.SupernovaPackagePass;
import ca.ulaval.glo4002.booking.domain.passes.passTypes.SupernovaSinglePass;
import ca.ulaval.glo4002.booking.domain.transport.ShuttleCategory;
import ca.ulaval.glo4002.booking.domain.transport.TransportRequester;

public class PassOrderUseCaseTest {

    private static final VendorCode VENDOR_CODE = VendorCode.TEAM;
    private static final LocalDate ORDER_DATE = LocalDate.of(2050, 1, 1);
    private static final OffsetDateTime ORDER_DATE_TIME = OffsetDateTime.of(ORDER_DATE, LocalTime.MIDNIGHT, ZoneOffset.UTC);
    private static final int NUMBER_OF_FESTIVAL_DAYS = 5;
    private static final LocalDate FESTIVAL_START = ORDER_DATE.plusMonths(12);
    private static final LocalDate FESTIVAL_END = FESTIVAL_START.plusDays(NUMBER_OF_FESTIVAL_DAYS - 1);
    private static final LocalDate IN_BETWEEN_FESTIVAL_DATE = FESTIVAL_START.plusDays(1);
    private static final PassNumber PASS_ID = mock(PassNumber.class);
    private static final int SOME_OXYGEN_REMAINING = 2;
    private static final int SOME_OXYGEN_INVENTORY = 3;
    private static final SortedMap<LocalDate, OxygenDateHistory> SOME_OXYGEN_HISTORIES = new TreeMap<LocalDate, OxygenDateHistory>();

    private static final int NEBULA_OXYGEN_QUANTITY = 3;
    private static final int SUPERGIANT_OXYGEN_QUANTITY = 3;
    private static final int SUPERNOVA_OXYGEN_QUANTITY = 5;
    private static final OxygenGrade NEBULA_OXYGEN_GRADE = OxygenGrade.A;
    private static final OxygenGrade SUPERGIANT_OXYGEN_GRADE = OxygenGrade.B;
    private static final OxygenGrade SUPERNOVA_OXYGEN_GRADE = OxygenGrade.E;
    private static final ShuttleCategory NEBULA_SHUTTLE_CATEGORY = ShuttleCategory.SPACE_X;
    private static final ShuttleCategory SUPERGIANT_SHUTTLE_CATEGORY = ShuttleCategory.MILLENNIUM_FALCON;
    private static final ShuttleCategory SUPERNOVA_SHUTTLE_CATEGORY = ShuttleCategory.ET_SPACESHIP;

    private TransportRequester transportRequester;
    private OxygenProducer oxygenProducer;
    private PassOrderFactory passOrderFactory;
    private PassOrderUseCase passOrderUseCase;
    private PassOrder passOrder;
    private PassRequest passRequest;
    private OxygenInventory oxygenInventory;

    @BeforeEach
    public void setUp() throws Exception {
        transportRequester = mock(TransportRequester.class);
        oxygenProducer = mock(OxygenProducer.class);
        passOrder = mock(PassOrder.class);
        passOrderFactory = mock(PassOrderFactory.class);
        when(passOrderFactory.create(any(), any(), any())).thenReturn(passOrder);
        PassOrderRepository passOrderRepository = mock(HeapPassOrderRepository.class);
        OxygenInventoryRepository oxygenInventoryRepository = mock(OxygenInventoryRepository.class);
        OxygenHistoryRepository oxygenHistoryRepository = mock(OxygenHistoryRepository.class);
        oxygenInventory = mock(OxygenInventory.class);
        mockRepositories(oxygenInventoryRepository, oxygenHistoryRepository);

        passOrderUseCase = new PassOrderUseCase(transportRequester, oxygenProducer, passOrderFactory, passOrderRepository, oxygenInventoryRepository,oxygenHistoryRepository);
        passRequest = mock(PassRequest.class);
    }

    @Test
    public void givenFestivalOf5Days_whenCreatingANebulaPackagePass_itCallsTheRightServices() throws Exception {
        mockPass(mock(NebulaPackagePass.class), PassCategory.NEBULA, FESTIVAL_START, FESTIVAL_END);

        passOrderUseCase.orchestPassCreation(ORDER_DATE_TIME, VENDOR_CODE, passRequest);

        verify(passOrderFactory).create(ORDER_DATE_TIME, VENDOR_CODE, passRequest);
        verify(transportRequester).reserveDeparture(NEBULA_SHUTTLE_CATEGORY, FESTIVAL_START, PASS_ID);
        verify(transportRequester).reserveArrival(NEBULA_SHUTTLE_CATEGORY, FESTIVAL_END, PASS_ID);
        verify(oxygenProducer).orderOxygen(ORDER_DATE, NEBULA_OXYGEN_GRADE, NEBULA_OXYGEN_QUANTITY * NUMBER_OF_FESTIVAL_DAYS, SOME_OXYGEN_INVENTORY, SOME_OXYGEN_REMAINING, SOME_OXYGEN_HISTORIES);
    }

    @Test
    public void whenCreatingANebulaSinglePass_itCallsTheRightServices() throws Exception {
        mockPass(mock(NebulaSinglePass.class), PassCategory.NEBULA, IN_BETWEEN_FESTIVAL_DATE, IN_BETWEEN_FESTIVAL_DATE);

        passOrderUseCase.orchestPassCreation(ORDER_DATE_TIME, VENDOR_CODE, passRequest);

        verify(passOrderFactory).create(ORDER_DATE_TIME, VENDOR_CODE, passRequest);
        verify(transportRequester).reserveDeparture(NEBULA_SHUTTLE_CATEGORY, IN_BETWEEN_FESTIVAL_DATE, PASS_ID);
        verify(transportRequester).reserveArrival(NEBULA_SHUTTLE_CATEGORY, IN_BETWEEN_FESTIVAL_DATE, PASS_ID);
        verify(oxygenProducer).orderOxygen(ORDER_DATE, NEBULA_OXYGEN_GRADE, NEBULA_OXYGEN_QUANTITY, SOME_OXYGEN_INVENTORY, SOME_OXYGEN_REMAINING, SOME_OXYGEN_HISTORIES);
    }

    @Test
    public void givenFestivalOf5Days_whenCreatingASupergiantPackagePass_itCallsTheRightServices() throws Exception {
        mockPass(mock(SupergiantPackagePass.class), PassCategory.SUPERGIANT, FESTIVAL_START, FESTIVAL_END);

        passOrderUseCase.orchestPassCreation(ORDER_DATE_TIME, VENDOR_CODE, passRequest);

        verify(passOrderFactory).create(ORDER_DATE_TIME, VENDOR_CODE, passRequest);
        verify(transportRequester).reserveDeparture(SUPERGIANT_SHUTTLE_CATEGORY, FESTIVAL_START, PASS_ID);
        verify(transportRequester).reserveArrival(SUPERGIANT_SHUTTLE_CATEGORY, FESTIVAL_END, PASS_ID);
        verify(oxygenProducer).orderOxygen(ORDER_DATE, SUPERGIANT_OXYGEN_GRADE, SUPERGIANT_OXYGEN_QUANTITY * NUMBER_OF_FESTIVAL_DAYS, SOME_OXYGEN_INVENTORY, SOME_OXYGEN_REMAINING, SOME_OXYGEN_HISTORIES);
    }

    @Test
    public void whenCreatingASupergiantSinglePass_itCallsTheRightServices() throws Exception {
        mockPass(mock(SupergiantSinglePass.class), PassCategory.SUPERGIANT, IN_BETWEEN_FESTIVAL_DATE, IN_BETWEEN_FESTIVAL_DATE);

        passOrderUseCase.orchestPassCreation(ORDER_DATE_TIME, VENDOR_CODE, passRequest);

        verify(passOrderFactory).create(ORDER_DATE_TIME, VENDOR_CODE, passRequest);
        verify(transportRequester).reserveDeparture(SUPERGIANT_SHUTTLE_CATEGORY, IN_BETWEEN_FESTIVAL_DATE, PASS_ID);
        verify(transportRequester).reserveArrival(SUPERGIANT_SHUTTLE_CATEGORY, IN_BETWEEN_FESTIVAL_DATE, PASS_ID);
        verify(oxygenProducer).orderOxygen(ORDER_DATE, SUPERGIANT_OXYGEN_GRADE, SUPERGIANT_OXYGEN_QUANTITY, SOME_OXYGEN_INVENTORY, SOME_OXYGEN_REMAINING, SOME_OXYGEN_HISTORIES);
    }

    @Test
    public void givenFestivalOf5Days_whenCreatingASupernovaPackagePass_itCallsTheRightServices() throws Exception {
        mockPass(mock(SupernovaPackagePass.class), PassCategory.SUPERNOVA, FESTIVAL_START, FESTIVAL_END);

        passOrderUseCase.orchestPassCreation(ORDER_DATE_TIME, VENDOR_CODE, passRequest);

        verify(passOrderFactory).create(ORDER_DATE_TIME, VENDOR_CODE, passRequest);
        verify(transportRequester).reserveDeparture(SUPERNOVA_SHUTTLE_CATEGORY, FESTIVAL_START, PASS_ID);
        verify(transportRequester).reserveArrival(SUPERNOVA_SHUTTLE_CATEGORY, FESTIVAL_END, PASS_ID);
        verify(oxygenProducer).orderOxygen(ORDER_DATE, SUPERNOVA_OXYGEN_GRADE, SUPERNOVA_OXYGEN_QUANTITY * NUMBER_OF_FESTIVAL_DAYS, SOME_OXYGEN_INVENTORY, SOME_OXYGEN_REMAINING, SOME_OXYGEN_HISTORIES);
    }

    @Test
    public void whenCreatingASupernovaSinglePass_itCallsTheRightServices() throws Exception {
        mockPass(mock(SupernovaSinglePass.class), PassCategory.SUPERNOVA, IN_BETWEEN_FESTIVAL_DATE, IN_BETWEEN_FESTIVAL_DATE);

        passOrderUseCase.orchestPassCreation(ORDER_DATE_TIME, VENDOR_CODE, passRequest);

        verify(passOrderFactory).create(ORDER_DATE_TIME, VENDOR_CODE, passRequest);
        verify(transportRequester).reserveDeparture(SUPERNOVA_SHUTTLE_CATEGORY, IN_BETWEEN_FESTIVAL_DATE, PASS_ID);
        verify(transportRequester).reserveArrival(SUPERNOVA_SHUTTLE_CATEGORY, IN_BETWEEN_FESTIVAL_DATE, PASS_ID);
        verify(oxygenProducer).orderOxygen(ORDER_DATE, SUPERNOVA_OXYGEN_GRADE, SUPERNOVA_OXYGEN_QUANTITY, SOME_OXYGEN_INVENTORY, SOME_OXYGEN_REMAINING, SOME_OXYGEN_HISTORIES);
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

    private void mockRepositories(OxygenInventoryRepository oxygenInventoryRepository, OxygenHistoryRepository oxygenHistoryRepository) {
        when(oxygenInventory.getRemainingQuantity()).thenReturn(SOME_OXYGEN_REMAINING);
        when(oxygenInventory.getTotalQuantity()).thenReturn(SOME_OXYGEN_INVENTORY);

        when(oxygenInventoryRepository.findInventoryOfGrade(NEBULA_OXYGEN_GRADE)).thenReturn(oxygenInventory);
        when(oxygenInventoryRepository.findInventoryOfGrade(SUPERGIANT_OXYGEN_GRADE)).thenReturn(oxygenInventory);
        when(oxygenInventoryRepository.findInventoryOfGrade(SUPERNOVA_OXYGEN_GRADE)).thenReturn(oxygenInventory);

        when(oxygenHistoryRepository.findOxygenHistory()).thenReturn(SOME_OXYGEN_HISTORIES);
    }
}
