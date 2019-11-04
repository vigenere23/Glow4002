package ca.ulaval.glo4002.booking.domain.passes.factories;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.domain.festivals.Glow4002Dates;
import ca.ulaval.glo4002.booking.domain.passes.PassCategory;
import ca.ulaval.glo4002.booking.domain.passes.passTypes.NebulaPackagePass;
import ca.ulaval.glo4002.booking.domain.passes.passTypes.PackagePass;
import ca.ulaval.glo4002.booking.domain.passes.passTypes.SupergiantPackagePass;
import ca.ulaval.glo4002.booking.domain.passes.passTypes.SupernovaPackagePass;

public class PackagePassFactoryTest {
    
    private PackagePassFactory passFactory;

    @BeforeEach
    public void setUp() {
        Glow4002Dates festival = mock(Glow4002Dates.class);
        passFactory = new PackagePassFactory(festival);
    }

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