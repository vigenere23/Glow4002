package ca.ulaval.glo4002.booking.persistance.heap;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.orders.ID;
import ca.ulaval.glo4002.booking.domain.passes.Pass;
import ca.ulaval.glo4002.booking.domain.passes.PassRepository;
import ca.ulaval.glo4002.booking.domain.passes.passTypes.NebulaSinglePass;
import ca.ulaval.glo4002.booking.domain.passes.passTypes.SupergiantPackagePass;
import ca.ulaval.glo4002.booking.domain.persistanceInterface.Repository;

import static org.assertj.core.api.Assertions.assertThat;

public class HeapPassRepositoryTest {

    private static final ID INVALID_ID = new ID(-1L);

    private PassRepository passRepository;
    private Pass pass;
    private Pass otherPass;

    @BeforeEach
    public void setUp() {
        Repository repository = new HeapRepository();

        passRepository = repository.getPassRepository();
        pass = new NebulaSinglePass(LocalDate.now());
        otherPass = new SupergiantPackagePass(LocalDate.now(), LocalDate.now());
    }

    @Test
    public void whenGetWithNonExistantId_itReturnsAnEmptyOptional() {
        assertThat(passRepository.getById(INVALID_ID)).isNotPresent();
    }

    @Test
    public void givenSavingAPass_whenGetThePassById_itReturnsTheSamePass() throws Exception {
        pass.setPassNumber(null);
        passRepository.save(pass);
        Pass savedPass = passRepository.getById(pass.getPassNumber()).get();
        assertThat(savedPass).isEqualTo(pass);
    }

    @Test
    public void whenSavingPassWithIdNull_itSetsAnId() throws Exception {
        pass.setPassNumber(null);
        passRepository.save(pass);
        assertThat(pass.getPassNumber()).isNotNull();
    }

    @Test
    public void whenSavingTwoPasses_itIncrementsTheIdBy1() throws Exception {
        passRepository.save(pass);
        Long firstPassId = pass.getPassNumber().getId();

        passRepository.save(otherPass);
        Long secondPassId = otherPass.getPassNumber().getId();

        assertThat(secondPassId - firstPassId).isEqualTo(1L);
    }
}
