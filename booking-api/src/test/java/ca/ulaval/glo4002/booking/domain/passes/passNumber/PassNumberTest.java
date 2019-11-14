package ca.ulaval.glo4002.booking.domain.passes.passNumber;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class PassNumberTest {

    @Test
    public void whenCreatingOrderNumberFromLong_itReturnsTheSameValueAsTheGivenLong() {
        long longPassNumber = 518191516455L;
        PassNumber passNumber = new PassNumber(longPassNumber);
        assertEquals(longPassNumber, passNumber.getValue());
    }

    @Test
    public void givenTwoCreatedOrderNumbersWithDifferentArguments_whenComparingEquality_itReturnsFalse() {
        PassNumber passNumber1 = new PassNumber(1);
        PassNumber passNumber2 = new PassNumber(2);
        assertNotEquals(passNumber1, passNumber2);
    }

    @Test
    public void givenTwoCreatedOrderNumbersWithSameArguments_whenComparingEquality_itReturnsTrue() {
        PassNumber passNumber1 = new PassNumber(1);
        PassNumber passNumber2 = new PassNumber(1);
        assertEquals(passNumber1, passNumber2);
    }
}
