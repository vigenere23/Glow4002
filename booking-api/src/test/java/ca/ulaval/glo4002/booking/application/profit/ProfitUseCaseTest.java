package ca.ulaval.glo4002.booking.application.profit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.ulaval.glo4002.booking.application.profit.dtos.ProfitReport;
import ca.ulaval.glo4002.booking.domain.Price;
import ca.ulaval.glo4002.booking.domain.profit.ProfitRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProfitUseCaseTest {
    
    @Mock ProfitRepository profitRepository;
    @Mock Price profit;
    @Mock Price income;
    @Mock Price outcome;
    @InjectMocks ProfitUseCase profitUseCase;

    @BeforeEach
    public void setup() {
        when(profitRepository.findIncome()).thenReturn(income);
        when(profitRepository.findOutcome()).thenReturn(outcome);
    }

    @Test
    public void whenGeneratingProfitReport_thenReturnsProfitReportWithPopulatedFields() {
        ProfitReport profitReport = profitUseCase.generateProfitReport();

        assertThat(profitReport.getIncome()).isEqualTo(income);
        assertThat(profitReport.getOutcome()).isEqualTo(outcome);
    }
}
