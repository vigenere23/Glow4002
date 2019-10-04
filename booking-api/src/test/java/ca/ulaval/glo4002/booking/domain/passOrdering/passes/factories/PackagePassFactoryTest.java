package ca.ulaval.glo4002.booking.domain.passOrdering.passes.factories;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.festivals.Glow4002;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.PassCategory;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.passTypes.NebulaPackagePass;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.passTypes.PackagePass;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.passTypes.SupergiantPackagePass;
import ca.ulaval.glo4002.booking.domain.passOrdering.passes.passTypes.SupernovaPackagePass;

public class PackagePassFactoryTest {
    
    Glow4002 festival = new Glow4002();
    private PackagePassFactory passFactory = new PackagePassFactory(festival);

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