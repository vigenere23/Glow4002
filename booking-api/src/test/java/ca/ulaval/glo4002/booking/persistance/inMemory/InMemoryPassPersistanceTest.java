package ca.ulaval.glo4002.booking.persistance.inMemory;

import java.time.OffsetDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.passOrdering.passes.Pass;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.PassCategory;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.factories.SinglePassFactory;
import ca.ulaval.glo4002.booking.domain.persistanceInterface.PassPersistance;
import ca.ulaval.glo4002.booking.domain.persistanceInterface.Repository;

import static org.assertj.core.api.Assertions.assertThat;

public class InMemoryPassPersistanceTest {

    private PassPersistance passPersistance;
    private Pass pass;

    @BeforeEach
    public void setUp() {
        Repository repository = new InMemoryRepository();
        this.passPersistance = repository.getPassPersistance();
        this.pass = new SinglePassFactory().create(PassCategory.NEBULA, OffsetDateTime.now());
    }

    @Test
    public void whenGetWithNonExistantId_itReturnsNull() {
        assertThat(this.passPersistance.getById((long) -1)).isNull();
    }

    @Test
    public void whenSavingPassWithIdNull_itSetsAnId() {
        this.pass.setId(null);
        this.passPersistance.save(this.pass);
        assertThat(this.pass.getId()).isNotNull();
    }

    @Test
    public void whenSavingPassWithNotNullId_itSetsANewId() {
        Long passId = (long) 1;
        this.pass.setId((long) passId);
        this.passPersistance.save(this.pass);
        assertThat(this.pass.getId()).isNotEqualTo(passId);
    }

    @Test
    public void givenSavingAPass_whenGetThePassById_itReturnsTheSamePass() {
        this.pass.setId(null);
        this.passPersistance.save(this.pass);
        Pass savedPass = this.passPersistance.getById(this.pass.getId());
        assertThat(savedPass).isEqualTo(this.pass);
    }

    @Test
    public void whenSavingTwoPasses_itIncrementsTheIdBy1() {
        this.passPersistance.save(this.pass);
        Long firstPassId = this.pass.getId();

        this.passPersistance.save(this.pass);
        Long secondPassId = this.pass.getId();

        assertThat(secondPassId - firstPassId).isEqualTo((long) 1);
    }
}
