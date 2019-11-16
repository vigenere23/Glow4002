package ca.ulaval.glo4002.booking.domain.oxygen2;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class OxygenGradeTest {

    @Test
    public void whenComparing_GradeEIsGreaterThanGradeB() {
        assertThat(OxygenGrade.E.compareTo(OxygenGrade.B)).isGreaterThan(0);
    }

    @Test
    public void whenComparing_GradeBIsGreaterThanGradeA() {
        assertThat(OxygenGrade.B.compareTo(OxygenGrade.A)).isGreaterThan(0);
    }
}
