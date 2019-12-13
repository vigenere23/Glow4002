package ca.ulaval.glo4002.booking.domain.oxygen.settings;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.oxygen.OxygenGrade;

public class OxygenRequestSettingsFactoryTest {

    private OxygenRequestSettingsFactory oxygenRequestSettingsFactory;

    @BeforeEach
    public void setup() {
        oxygenRequestSettingsFactory = new OxygenRequestSettingsFactory();
    }

    @Test
    public void givenGradeAOxygen_whenCreating_itReturnsGradeASettings() {
        OxygenGrade oxygenGrade = OxygenGrade.A;
        OxygenRequestSettings oxygenRequestSettings = oxygenRequestSettingsFactory.create(oxygenGrade);
        assertThat(oxygenRequestSettings).isInstanceOf(OxygenGradeASettings.class);
    }

    @Test
    public void givenGradeBOxygen_whenCreating_itReturnsGradeBSettings() {
        OxygenGrade oxygenGrade = OxygenGrade.B;
        OxygenRequestSettings oxygenRequestSettings = oxygenRequestSettingsFactory.create(oxygenGrade);
        assertThat(oxygenRequestSettings).isInstanceOf(OxygenGradeBSettings.class);
    }

    @Test
    public void givenGradeEOxygen_whenCreating_itReturnsGradeESettings() {
        OxygenGrade oxygenGrade = OxygenGrade.E;
        OxygenRequestSettings oxygenRequestSettings = oxygenRequestSettingsFactory.create(oxygenGrade);
        assertThat(oxygenRequestSettings).isInstanceOf(OxygenGradeESettings.class);
    }
}
