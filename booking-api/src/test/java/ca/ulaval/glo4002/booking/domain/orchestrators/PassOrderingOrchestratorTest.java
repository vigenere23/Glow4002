package ca.ulaval.glo4002.booking.domain.orchestrators;

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

import ca.ulaval.glo4002.booking.domain.artists.ArtistRepository;
import ca.ulaval.glo4002.booking.infrastructure.apiArtistsRepository.ApiArtistRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.api.dtos.orders.PassRequest;
import ca.ulaval.glo4002.booking.domain.orders.ID;
import ca.ulaval.glo4002.booking.domain.orders.PassOrder;
import ca.ulaval.glo4002.booking.domain.orders.PassOrderRequester;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenGrade;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenRequester;
import ca.ulaval.glo4002.booking.domain.passes.Pass;
import ca.ulaval.glo4002.booking.domain.passes.PassCategory;
import ca.ulaval.glo4002.booking.domain.passes.passTypes.NebulaPackagePass;
import ca.ulaval.glo4002.booking.domain.passes.passTypes.NebulaSinglePass;
import ca.ulaval.glo4002.booking.domain.passes.passTypes.SupergiantPackagePass;
import ca.ulaval.glo4002.booking.domain.passes.passTypes.SupergiantSinglePass;
import ca.ulaval.glo4002.booking.domain.passes.passTypes.SupernovaPackagePass;
import ca.ulaval.glo4002.booking.domain.passes.passTypes.SupernovaSinglePass;
import ca.ulaval.glo4002.booking.domain.transport.ShuttleCategory;
import ca.ulaval.glo4002.booking.domain.transport.TransportRequester;

public class PassOrderingOrchestratorTest {

    private static final String VENDOR_CODE = "CODE";
    private static final LocalDate ORDER_DATE = LocalDate.of(2050, 1, 1);
    private static final OffsetDateTime ORDER_DATE_TIME = OffsetDateTime.of(ORDER_DATE, LocalTime.MIDNIGHT, ZoneOffset.UTC);
    private static final int NUMBER_OF_FESTIVAL_DAYS = 5;
    private static final LocalDate FESTIVAL_START = ORDER_DATE.plusMonths(12);
    private static final LocalDate FESTIVAL_END = FESTIVAL_START.plusDays(NUMBER_OF_FESTIVAL_DAYS - 1);
    private static final LocalDate IN_BETWEEN_FESTIVAL_DATE = FESTIVAL_START.plusDays(1);
    private static final ID PASS_ID = new ID(0L);

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
    private OxygenRequester oxygenRequester;
    private ArtistRepository artistsRepository;
    private PassOrderRequester passOrderRequester;
    private PassOrderingOrchestrator orchestrator;
    private PassOrder passOrder;
    private PassRequest passRequest;

    @BeforeEach
    public void setUp() throws Exception {
        transportRequester = mock(TransportRequester.class);
        oxygenRequester = mock(OxygenRequester.class);
        artistsRepository = mock(ApiArtistRepository.class);
        passOrderRequester = mock(PassOrderRequester.class);
        passOrder = mock(PassOrder.class);
        when(passOrderRequester.orderPasses(any(OffsetDateTime.class), any(String.class), any(PassRequest.class)))
            .thenReturn(passOrder);
        orchestrator = new PassOrderingOrchestrator(transportRequester, oxygenRequester, passOrderRequester, artistsRepository);
        passRequest = mock(PassRequest.class);
    }

    @Test
    public void givenFestivalOf5Days_whenCreatingANebulaPackagePass_itCallsTheRightServices() throws Exception {
        mockPass(mock(NebulaPackagePass.class), PassCategory.NEBULA, FESTIVAL_START, FESTIVAL_END);

        orchestrator.orchestPassCreation(ORDER_DATE_TIME, VENDOR_CODE, passRequest);

        verify(passOrderRequester).orderPasses(ORDER_DATE_TIME, VENDOR_CODE, passRequest);
        verify(transportRequester).reserveDeparture(NEBULA_SHUTTLE_CATEGORY, FESTIVAL_START, PASS_ID);
        verify(transportRequester).reserveArrival(NEBULA_SHUTTLE_CATEGORY, FESTIVAL_END, PASS_ID);
        verify(oxygenRequester).orderOxygen(ORDER_DATE, NEBULA_OXYGEN_GRADE, NEBULA_OXYGEN_QUANTITY * NUMBER_OF_FESTIVAL_DAYS);
    }

    @Test
    public void whenCreatingANebulaSinglePass_itCallsTheRightServices() throws Exception {
        mockPass(mock(NebulaSinglePass.class), PassCategory.NEBULA, IN_BETWEEN_FESTIVAL_DATE, IN_BETWEEN_FESTIVAL_DATE);

        orchestrator.orchestPassCreation(ORDER_DATE_TIME, VENDOR_CODE, passRequest);

        verify(passOrderRequester).orderPasses(ORDER_DATE_TIME, VENDOR_CODE, passRequest);
        verify(transportRequester).reserveDeparture(NEBULA_SHUTTLE_CATEGORY, IN_BETWEEN_FESTIVAL_DATE, PASS_ID);
        verify(transportRequester).reserveArrival(NEBULA_SHUTTLE_CATEGORY, IN_BETWEEN_FESTIVAL_DATE, PASS_ID);
        verify(oxygenRequester).orderOxygen(ORDER_DATE, NEBULA_OXYGEN_GRADE, NEBULA_OXYGEN_QUANTITY);
    }

    @Test
    public void givenFestivalOf5Days_whenCreatingASupergiantPackagePass_itCallsTheRightServices() throws Exception {
        mockPass(mock(SupergiantPackagePass.class), PassCategory.SUPERGIANT, FESTIVAL_START, FESTIVAL_END);

        orchestrator.orchestPassCreation(ORDER_DATE_TIME, VENDOR_CODE, passRequest);

        verify(passOrderRequester).orderPasses(ORDER_DATE_TIME, VENDOR_CODE, passRequest);
        verify(transportRequester).reserveDeparture(SUPERGIANT_SHUTTLE_CATEGORY, FESTIVAL_START, PASS_ID);
        verify(transportRequester).reserveArrival(SUPERGIANT_SHUTTLE_CATEGORY, FESTIVAL_END, PASS_ID);
        verify(oxygenRequester).orderOxygen(ORDER_DATE, SUPERGIANT_OXYGEN_GRADE, SUPERGIANT_OXYGEN_QUANTITY * NUMBER_OF_FESTIVAL_DAYS);
    }

    @Test
    public void whenCreatingASupergiantSinglePass_itCallsTheRightServices() throws Exception {
        mockPass(mock(SupergiantSinglePass.class), PassCategory.SUPERGIANT, IN_BETWEEN_FESTIVAL_DATE, IN_BETWEEN_FESTIVAL_DATE);

        orchestrator.orchestPassCreation(ORDER_DATE_TIME, VENDOR_CODE, passRequest);

        verify(passOrderRequester).orderPasses(ORDER_DATE_TIME, VENDOR_CODE, passRequest);
        verify(transportRequester).reserveDeparture(SUPERGIANT_SHUTTLE_CATEGORY, IN_BETWEEN_FESTIVAL_DATE, PASS_ID);
        verify(transportRequester).reserveArrival(SUPERGIANT_SHUTTLE_CATEGORY, IN_BETWEEN_FESTIVAL_DATE, PASS_ID);
        verify(oxygenRequester).orderOxygen(ORDER_DATE, SUPERGIANT_OXYGEN_GRADE, SUPERGIANT_OXYGEN_QUANTITY);
    }

    @Test
    public void givenFestivalOf5Days_whenCreatingASupernovaPackagePass_itCallsTheRightServices() throws Exception {
        mockPass(mock(SupernovaPackagePass.class), PassCategory.SUPERNOVA, FESTIVAL_START, FESTIVAL_END);

        orchestrator.orchestPassCreation(ORDER_DATE_TIME, VENDOR_CODE, passRequest);

        verify(passOrderRequester).orderPasses(ORDER_DATE_TIME, VENDOR_CODE, passRequest);
        verify(transportRequester).reserveDeparture(SUPERNOVA_SHUTTLE_CATEGORY, FESTIVAL_START, PASS_ID);
        verify(transportRequester).reserveArrival(SUPERNOVA_SHUTTLE_CATEGORY, FESTIVAL_END, PASS_ID);
        verify(oxygenRequester).orderOxygen(ORDER_DATE, SUPERNOVA_OXYGEN_GRADE, SUPERNOVA_OXYGEN_QUANTITY * NUMBER_OF_FESTIVAL_DAYS);
    }

    @Test
    public void whenCreatingASupernovaSinglePass_itCallsTheRightServices() throws Exception {
        mockPass(mock(SupernovaSinglePass.class), PassCategory.SUPERNOVA, IN_BETWEEN_FESTIVAL_DATE, IN_BETWEEN_FESTIVAL_DATE);

        orchestrator.orchestPassCreation(ORDER_DATE_TIME, VENDOR_CODE, passRequest);

        verify(passOrderRequester).orderPasses(ORDER_DATE_TIME, VENDOR_CODE, passRequest);
        verify(transportRequester).reserveDeparture(SUPERNOVA_SHUTTLE_CATEGORY, IN_BETWEEN_FESTIVAL_DATE, PASS_ID);
        verify(transportRequester).reserveArrival(SUPERNOVA_SHUTTLE_CATEGORY, IN_BETWEEN_FESTIVAL_DATE, PASS_ID);
        verify(oxygenRequester).orderOxygen(ORDER_DATE, SUPERNOVA_OXYGEN_GRADE, SUPERNOVA_OXYGEN_QUANTITY);
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
