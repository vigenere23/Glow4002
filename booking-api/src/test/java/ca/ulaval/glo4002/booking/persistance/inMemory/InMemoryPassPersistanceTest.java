package ca.ulaval.glo4002.booking.persistance.inMemory;

import java.time.OffsetDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.passOrdering.passes.Pass;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.PassCategory;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.factories.PackagePassFactory;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.factories.SinglePassFactory;
import ca.ulaval.glo4002.booking.domain.persistanceInterface.PassPersistance;
import ca.ulaval.glo4002.booking.domain.persistanceInterface.Repository;
import ca.ulaval.glo4002.booking.persistance.inMemory.exceptions.RecordAlreadyExistsException;
import ca.ulaval.glo4002.booking.persistance.inMemory.exceptions.RecordNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class InMemoryPassPersistanceTest {

    private PassPersistance passPersistance;
    private Pass pass;
    private Pass otherPass;

    @BeforeEach
    public void setUp() {
        Repository repository = new InMemoryRepository();
        this.passPersistance = repository.getPassPersistance();
        this.pass = new SinglePassFactory().create(PassCategory.NEBULA, OffsetDateTime.now());
        this.otherPass = new PackagePassFactory().create(PassCategory.SUPERGIANT);
    }

    @Test
    public void whenGetWithNonExistantId_itThrowsARecordNotFoundException() {
        assertThatExceptionOfType(RecordNotFoundException.class).isThrownBy(() -> {
            this.passPersistance.getById(-1L);
        });
    }

    @Test
    public void givenSavingAPass_whenGetThePassById_itReturnsTheSamePass() throws Exception {
        this.pass.setId(null);
        this.passPersistance.save(this.pass);
        Pass savedPass = this.passPersistance.getById(this.pass.getId());
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

        assertThat(secondPassId - firstPassId).isEqualTo((long) 1);
    }

    @Test
    public void whenSavingAnAlreadyExistingPass_itThrowsRecordAlreadyExistsException() throws Exception {
        this.passPersistance.save(this.pass);
        assertThatExceptionOfType(RecordAlreadyExistsException.class).isThrownBy(() -> {
            this.passPersistance.save(this.pass);
        });
    }

    @Test
    public void givenPassIsSaved_whenRemovingPassById_itDeletedThePass() throws Exception {
        this.passPersistance.save(this.pass);
        this.passPersistance.remove(this.pass.getId());
        assertThatExceptionOfType(RecordNotFoundException.class).isThrownBy(() -> {
            this.passPersistance.getById(this.pass.getId());
        });
    }

    @Test
    public void whenRemovingPassByNotSavedId_itThrowsARecordNotFoundException() throws Exception {
        assertThatExceptionOfType(RecordNotFoundException.class).isThrownBy(() -> {
            this.passPersistance.remove(this.pass.getId());            
        });
    }
}
