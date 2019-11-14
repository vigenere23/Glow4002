package ca.ulaval.glo4002.booking.domain.passes.passNumber;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PassNumberTest {

    @Test
    public void whenCreatingOrderNumberFromLong_itReturnsTheSameValueAsTheGivenLong() {
        long longPassNumber = 518191516455L;
        PassNumber passNumber = new PassNumber(longPassNumber);
        assertThat(passNumber.getValue()).isEqualTo(longPassNumber);
    }

    @Test
    public void givenTwoCreatedOrderNumbersWithDifferentArguments_whenComparingEquality_itReturnsFalse() {
        PassNumber passNumber1 = new PassNumber(1);
        PassNumber passNumber2 = new PassNumber(2);
        assertThat(passNumber1).isNotEqualTo(passNumber2);
    }

    @Test
    public void givenTwoCreatedOrderNumbersWithSameArguments_whenComparingEquality_itReturnsTrue() {
        PassNumber passNumber1 = new PassNumber(1);
        PassNumber passNumber2 = new PassNumber(1);
        assertThat(passNumber1).isEqualTo(passNumber2);
    }
}
