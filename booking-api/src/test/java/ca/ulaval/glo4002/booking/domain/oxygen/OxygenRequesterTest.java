package ca.ulaval.glo4002.booking.domain.oxygen;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.OffsetDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.ulaval.glo4002.booking.domain.dates.OxygenDates;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenGrade;
import ca.ulaval.glo4002.booking.domain.oxygen.orderers.OxygenOrderer;
import ca.ulaval.glo4002.booking.domain.oxygen.orderers.LinkedOxygenOrdererFactory;

@ExtendWith(MockitoExtension.class)
public class OxygenRequesterTest {

    private final static OffsetDateTime SOME_DATETIME = OffsetDateTime.now();
    private final static OxygenGrade SOME_OXYGEN_GRADE = OxygenGrade.A;
    private final static int SOME_OXYGEN_QUANTITY = 5;
    private final static OxygenOrderer SOME_OXYGEN_ORDERER = mock(OxygenOrderer.class);
    
    @Mock LinkedOxygenOrdererFactory oxygenOrdererFactory;
    @Mock OxygenDates someOxygenDates;
    @InjectMocks OxygenRequester oxygenRequester;

    @BeforeEach
    public void setup() {
        when(oxygenOrdererFactory.create(any(OxygenGrade.class)))
            .thenReturn(SOME_OXYGEN_ORDERER);
    }

    @Test
    public void whenRequestingOxygen_itDelegatesToAnOrderer() {
        oxygenRequester.requestOxygen(SOME_DATETIME, SOME_OXYGEN_GRADE, SOME_OXYGEN_QUANTITY);
        LocalDate orderDate = SOME_DATETIME.toLocalDate();
        verify(SOME_OXYGEN_ORDERER).order(orderDate, SOME_OXYGEN_QUANTITY);
    }
}
