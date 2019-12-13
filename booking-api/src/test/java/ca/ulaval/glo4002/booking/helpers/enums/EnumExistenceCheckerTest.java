package ca.ulaval.glo4002.booking.helpers.enums;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class EnumExistenceCheckerTest {

    @Test
    public void givenStringCorrespondingToEnumValue_whenCheckingExistance_itReturnsTrue() {
        String value = TestEnum.VALUE.name();
        assertThat(EnumExistenceChecker.isInEnum(value, TestEnum.class)).isTrue();
    }

    @Test
    public void givenStringCorrespondingToEnumStringValue_whenCheckingExistance_itReturnsTrue() {
        String value = TestEnum.VALUE.toString();
        assertThat(EnumExistenceChecker.isInEnum(value, TestEnum.class)).isTrue();
    }

    @Test
    public void givenStringNotCorrespondingToEnum_whenCheckingExistance_itReturnsFalse() {
        String value = "NON_EXISTANT";
        assertThat(EnumExistenceChecker.isInEnum(value, TestEnum.class)).isFalse();
    }
}
