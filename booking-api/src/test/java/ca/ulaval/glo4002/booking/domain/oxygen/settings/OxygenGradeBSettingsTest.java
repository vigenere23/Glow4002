package ca.ulaval.glo4002.booking.domain.oxygen.settings;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.Price;
import ca.ulaval.glo4002.booking.domain.oxygen.OxygenGrade;

public class OxygenGradeBSettingsTest {

    private OxygenGradeBSettings oxygenGradeBSettings;

    @BeforeEach
    public void setup() {
        oxygenGradeBSettings = new OxygenGradeBSettings();
    }

    @Test
    public void whenGettingOxygenGrade_itReturnsGradeB() {
        assertThat(oxygenGradeBSettings.getGrade()).isEqualTo(OxygenGrade.B);
    }

    @Test
    public void whenGettingTimeToReceive_itReturns10Days() {
        assertThat(oxygenGradeBSettings.getTimeToReceive()).isEqualTo(Duration.ofDays(10));
    }

    @Test
    public void whenGettingBatchSize_itReturnsThree() {
        assertThat(oxygenGradeBSettings.getBatchSize()).isEqualTo(3);
    }

    @Test
    public void whenGettingCostPerBatch_itReturns4800() {
        assertThat(oxygenGradeBSettings.getCostPerBatch()).isEqualTo(new Price(4800));
    }
}
