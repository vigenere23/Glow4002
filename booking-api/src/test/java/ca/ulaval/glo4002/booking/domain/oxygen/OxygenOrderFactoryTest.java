package ca.ulaval.glo4002.booking.domain.oxygen;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class OxygenOrderFactoryTest {

    private final static LocalDate SOME_FESTIVAL_STARTING_DATE = LocalDate.of(2050, 7, 17);

    private OxygenOrderFactory oxygenOrderFactory;

    @BeforeEach
    public void setUpOxygenOrderFactory() {
        oxygenOrderFactory = new OxygenOrderFactory(SOME_FESTIVAL_STARTING_DATE);
    }

    @Test
    void whenCreateOxygenOrderOfOxygenGradeA_thenCreatesNewGradeAOxygenOrder() {
        OxygenOrder oxygenOrder = oxygenOrderFactory.create(OxygenGrade.A);
        assertTrue(oxygenOrder instanceof GradeAOxygenOrder);
    }

    @Test
    void whenCreateOxygenOrderOfOxygenGradeB_thenCreatesNewGradeBOxygenOrder() {
        OxygenOrder oxygenOrder = oxygenOrderFactory.create(OxygenGrade.B);
        assertTrue(oxygenOrder instanceof GradeBOxygenOrder);
    }

    @Test
    void whenCreateOxygenOrderOfOxygenGradeE_thenCreatesNewGradeEOxygenOrder() {
        OxygenOrder oxygenOrder = oxygenOrderFactory.create(OxygenGrade.E);
        assertTrue(oxygenOrder instanceof GradeEOxygenOrder);
    }
}