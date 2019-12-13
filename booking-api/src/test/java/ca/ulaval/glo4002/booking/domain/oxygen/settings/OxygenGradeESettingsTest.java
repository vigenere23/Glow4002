package ca.ulaval.glo4002.booking.domain.oxygen.settings;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.Price;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenGrade;

public class OxygenGradeESettingsTest {

    private OxygenGradeESettings oxygenGradeESettings;

    @BeforeEach
    public void setup() {
        oxygenGradeESettings = new OxygenGradeESettings();
    }

    @Test
    public void whenGettingOxygenGrade_itReturnsGradeE() {
        assertThat(oxygenGradeESettings.getGrade()).isEqualTo(OxygenGrade.E);
    }

    @Test
    public void whenGettingTimeToReceive_itReturnsZero() {
        assertThat(oxygenGradeESettings.getTimeToReceive()).isEqualTo(Duration.ZERO);
    }

    @Test
    public void whenGettingBatchSize_itReturnsOne() {
        assertThat(oxygenGradeESettings.getBatchSize()).isEqualTo(1);
    }

    @Test
    public void whenGettingCostPerBatch_itReturns5000() {
        assertThat(oxygenGradeESettings.getCostPerBatch()).isEqualTo(new Price(5000));
    }
}
