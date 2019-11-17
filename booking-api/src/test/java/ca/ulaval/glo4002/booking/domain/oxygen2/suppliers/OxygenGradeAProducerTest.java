package ca.ulaval.glo4002.booking.domain.oxygen2.suppliers;

import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.finance.ProfitCalculator;
import ca.ulaval.glo4002.booking.domain.oxygen2.OxygenInventory;
import ca.ulaval.glo4002.booking.domain.oxygen2.history.OxygenHistory;

public class OxygenGradeAProducerTest {

    private OxygenGradeAProducer oxygenGradeAProducer;
    private OxygenInventory oxygenInventory;
    private OxygenHistory oxygenHistory;
    private ProfitCalculator profitCalculator;

    @BeforeEach
    public void setup() {
        oxygenInventory = mock(OxygenInventory.class);
        oxygenHistory = mock(OxygenHistory.class);
        profitCalculator = mock(ProfitCalculator.class);
        oxygenGradeAProducer = new OxygenGradeAProducer(oxygenInventory, oxygenHistory, profitCalculator);
    }

    @Test
    public void whenOrdering_XXX() {
        
    }
}
