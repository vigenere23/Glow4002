package ca.ulaval.glo4002.booking.domain.oxygen;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.OffsetDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.dates.OxygenDates;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenGrade;
import ca.ulaval.glo4002.booking.domain.oxygen.orderers.OxygenOrderer;
import ca.ulaval.glo4002.booking.domain.oxygen.orderers.OxygenOrdererFactory;

public class OxygenRequesterTest {

    private final static OffsetDateTime SOME_DATETIME = OffsetDateTime.now();
    private final static OxygenGrade SOME_OXYGEN_GRADE = OxygenGrade.A;
    private final static int SOME_OXYGEN_QUANTITY = 5;
    private final static OxygenOrderer SOME_OXYGEN_ORDERER = mock(OxygenOrderer.class);
    
    private OxygenRequester oxygenRequester;
    private OxygenOrdererFactory oxygenOrdererFactory;
    private OxygenDates someOxygenDates;

    @BeforeEach
    public void setup() {
        oxygenOrdererFactory = mock(OxygenOrdererFactory.class);
        someOxygenDates = mock(OxygenDates.class);
        when(oxygenOrdererFactory.create(any(OxygenGrade.class), any(OxygenDates.class)))
            .thenReturn(SOME_OXYGEN_ORDERER);
        oxygenRequester = new OxygenRequester(oxygenOrdererFactory, someOxygenDates);
    }

    @Test
    public void whenRequestingOxygen_itDelegatesToAnOrderer() {
        oxygenRequester.requestOxygen(SOME_DATETIME, SOME_OXYGEN_GRADE, SOME_OXYGEN_QUANTITY);
        LocalDate orderDate = SOME_DATETIME.toLocalDate();
        verify(SOME_OXYGEN_ORDERER).order(orderDate, SOME_OXYGEN_QUANTITY);
    }
}
