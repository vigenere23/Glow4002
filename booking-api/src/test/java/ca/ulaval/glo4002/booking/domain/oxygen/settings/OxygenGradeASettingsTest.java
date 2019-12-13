package ca.ulaval.glo4002.booking.domain.oxygen.settings;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.Price;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenGrade;

public class OxygenGradeASettingsTest {

    private OxygenGradeASettings oxygenGradeASettings;

    @BeforeEach
    public void setup() {
        oxygenGradeASettings = new OxygenGradeASettings();
    }

    @Test
    public void whenGettingOxygenGrade_itReturnsGradeA() {
        assertThat(oxygenGradeASettings.getGrade()).isEqualTo(OxygenGrade.A);
    }

    @Test
    public void whenGettingTimeToReceive_itReturns20Days() {
        assertThat(oxygenGradeASettings.getTimeToReceive()).isEqualTo(Duration.ofDays(20));
    }

    @Test
    public void whenGettingBatchSize_itReturnsFive() {
        assertThat(oxygenGradeASettings.getBatchSize()).isEqualTo(5);
    }

    @Test
    public void whenGettingCostPerBatch_itReturns9750() {
        assertThat(oxygenGradeASettings.getCostPerBatch()).isEqualTo(new Price(9750));
    }
}
