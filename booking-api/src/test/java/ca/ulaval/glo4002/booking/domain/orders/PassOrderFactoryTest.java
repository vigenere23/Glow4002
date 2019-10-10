package ca.ulaval.glo4002.booking.domain.orders;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.any;

import java.time.OffsetDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.festivals.Glow4002;

public class PassOrderFactoryTest {

    private PassOrderFactory passOrderFactory;

    @BeforeEach
    public void setUp() {
        Glow4002 festival = mock(Glow4002.class);
        when(festival.isDuringSaleTime(any(OffsetDateTime.class))).thenReturn(true);
        passOrderFactory = new PassOrderFactory(festival);
    }

    @Test
    public void whenPassingNullAsPassDtos_thenItThrowsAnException() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            passOrderFactory.create(OffsetDateTime.now(), "CODE", null);
        });
    }
}
