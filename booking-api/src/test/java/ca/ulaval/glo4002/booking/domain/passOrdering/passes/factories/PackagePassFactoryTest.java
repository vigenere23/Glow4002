package ca.ulaval.glo4002.booking.domain.passOrdering.passes.factories;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import org.junit.Test;

import ca.ulaval.glo4002.booking.domain.passOrdering.passes.PassCategory;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.passTypes.NebulaPackagePass;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.passTypes.PackagePass;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.passTypes.SupergiantPackagePass;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.passTypes.SupernovaPackagePass;

public class PackagePassFactoryTest {
    
    OffsetDateTime festivalStart = OffsetDateTime.of(LocalDate.of(2050, 7, 17), LocalTime.MIDNIGHT, ZoneOffset.UTC);
    OffsetDateTime festivalEnd = OffsetDateTime.of(LocalDate.of(2050, 7, 25), LocalTime.MIDNIGHT.minusSeconds(1), ZoneOffset.UTC);
    private PackagePassFactory passFactory = new PackagePassFactory(festivalStart, festivalEnd);

    @Test
    public void shouldCreateNebulaPackagePassInstance() {
        PackagePass pass = passFactory.create(PassCategory.NEBULA);

        assertTrue(pass.getClass().equals(NebulaPackagePass.class));
    }

    @Test
    public void shouldCreateSupergiantPackagePassInstance() {
        PackagePass pass = passFactory.create(PassCategory.SUPERNOVA);

        assertTrue(pass.getClass().equals(SupernovaPackagePass.class));
    }

    @Test
    public void shouldCreateSupernovaPackagePassInstance() {
        PackagePass pass = passFactory.create(PassCategory.SUPERGIANT);

        assertTrue(pass.getClass().equals(SupergiantPackagePass.class));
    }
}