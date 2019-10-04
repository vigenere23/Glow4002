package ca.ulaval.glo4002.booking.persistance.heap;

import java.time.OffsetDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.passOrdering.passes.Pass;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.passTypes.NebulaSinglePass;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.passTypes.SupergiantPackagePass;
import ca.ulaval.glo4002.booking.domain.persistanceInterface.PassPersistance;
import ca.ulaval.glo4002.booking.domain.persistanceInterface.Repository;

import static org.assertj.core.api.Assertions.assertThat;

public class HeapPassPersistanceTest {

    private static final long INVALID_ID = -1L;

    private PassPersistance passPersistance;
    private Pass pass;
    private Pass otherPass;

    @BeforeEach
    public void setUp() {
        Repository repository = new HeapRepository();

        this.passPersistance = repository.getPassPersistance();
        this.pass = new NebulaSinglePass(OffsetDateTime.now());
        this.otherPass = new SupergiantPackagePass(OffsetDateTime.now(), OffsetDateTime.now());
    }

    @Test
    public void whenGetWithNonExistantId_itReturnsAnEmptyOptional() {
        assertThat(this.passPersistance.getById(INVALID_ID)).isNotPresent();
    }

    @Test
    public void givenSavingAPass_whenGetThePassById_itReturnsTheSamePass() throws Exception {
        this.pass.setId(null);
        this.passPersistance.save(this.pass);
        Pass savedPass = this.passPersistance.getById(this.pass.getId()).get();
        assertThat(savedPass).isEqualTo(this.pass);
    }

    @Test
    public void whenSavingPassWithIdNull_itSetsAnId() throws Exception {
        this.pass.setId(null);
        this.passPersistance.save(this.pass);
        assertThat(this.pass.getId()).isNotNull();
    }

    @Test
    public void whenSavingTwoPasses_itIncrementsTheIdBy1() throws Exception {
        this.passPersistance.save(this.pass);
        Long firstPassId = this.pass.getId();

        this.passPersistance.save(this.otherPass);
        Long secondPassId = this.otherPass.getId();

        assertThat(secondPassId - firstPassId).isEqualTo(1L);
    }
}
