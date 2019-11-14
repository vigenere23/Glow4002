package ca.ulaval.glo4002.booking.domain.passes.passNumber;

import java.util.concurrent.atomic.AtomicLong;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PassNumberFactoryTest {

    private PassNumberFactory passNumberFactory;

    @BeforeEach
    public void setupOrderNumberFactory() {
        passNumberFactory = new PassNumberFactory(new AtomicLong(0));
    }

    @Test
    public void givenOnFirstUse_whenCreating_thenTheNumberIsZero() {
        PassNumber passNumber = passNumberFactory.create();
        assertEquals(0, passNumber.getValue());
    }

    @Test
    public void givenMultipleCreation_whenCreating_thenTheNumberIsIncrementedByNumberOfCreations() {
        int numberOfCreations = 10;
        PassNumber passNumber = null;
        for (int i = 0; i <= numberOfCreations; i++) {
            passNumber = passNumberFactory.create();
        }

        assertEquals(numberOfCreations, passNumber.getValue());
    }
}
