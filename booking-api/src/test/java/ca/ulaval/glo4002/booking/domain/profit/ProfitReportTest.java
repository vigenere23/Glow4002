package ca.ulaval.glo4002.booking.domain.profit;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.application.profit.dtos.ProfitReport;
import ca.ulaval.glo4002.booking.domain.Price;

public class ProfitReportTest {

    private static final Price INCOME = new Price(1234.5678);
    private static final Price OUTCOME = new Price(5678.1234);
    private static final Price EXPECTED_PROFIT = new Price(-4443.5556);

    private ProfitReport profitReport;

    @BeforeEach
    public void setup() {
        profitReport = new ProfitReport(INCOME, OUTCOME);
    }

    @Test
    public void whenCreating_itSetsTheIncome() {
        assertThat(profitReport.getIncome()).isEqualTo(INCOME);
    }

    @Test
    public void whenCreating_itSetsTheOutcome() {
        assertThat(profitReport.getOutcome()).isEqualTo(OUTCOME);
    }

    @Test
    public void whenCalculatingProfit_itReturnsTheIncomeMinusTheOutcome() {
        assertThat(profitReport.calculateProfit()).isEqualTo(EXPECTED_PROFIT);
    }
}
