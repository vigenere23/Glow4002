package ca.ulaval.glo4002.booking.domain.passOrdering.orders;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.time.OffsetDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PassOrderFactoryTest {

    private PassOrderFactory passOrderFactory;

    @BeforeEach
    public void setUp() {
        passOrderFactory = new PassOrderFactory(OffsetDateTime.now(), OffsetDateTime.now());
    }

    @Test
    public void whenPassingNullAsPassDTOs_thenItThrowsAnException() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            passOrderFactory.create(OffsetDateTime.now(), "CODE", null);
        });
    }
}
