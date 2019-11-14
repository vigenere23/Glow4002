package ca.ulaval.glo4002.booking.api.dtoMappers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.ulaval.glo4002.booking.api.resources.passOrder.dto.PassDto;
import ca.ulaval.glo4002.booking.api.resources.passOrder.dto.PassMapper;
import ca.ulaval.glo4002.booking.api.resources.passOrder.dto.SinglePassDto;
import ca.ulaval.glo4002.booking.domain.passes.Pass;
import ca.ulaval.glo4002.booking.domain.passes.PassCategory;
import ca.ulaval.glo4002.booking.domain.passes.PassNumber;
import ca.ulaval.glo4002.booking.domain.passes.PassOption;

public class PassMapperTest {

    private final static PassCategory SOME_PASS_CATEGORY = PassCategory.NEBULA;
    private final static PassOption SOME_PASS_OPTION = PassOption.PACKAGE;
    private final static PassOption OTHER_PASS_OPTION = PassOption.SINGLE_PASS;
    private final static PassNumber SOME_PASS_NUMBER = new PassNumber();
    private final static PassNumber OTHER_PASS_NUMBER = new PassNumber();
    
    private PassMapper passMapper;
    private Pass packagePass;
    private Pass singlePass;

    @BeforeEach
    public void setUpMapper() {
        packagePass = mock(Pass.class);
        singlePass = mock(Pass.class);

        when(packagePass.getPassCategory()).thenReturn(SOME_PASS_CATEGORY);
        when(singlePass.getPassCategory()).thenReturn(SOME_PASS_CATEGORY);
        when(packagePass.getPassOption()).thenReturn(SOME_PASS_OPTION);
        when(singlePass.getPassOption()).thenReturn(OTHER_PASS_OPTION);
        when(packagePass.getPassNumber()).thenReturn(SOME_PASS_NUMBER);
        when(singlePass.getPassNumber()).thenReturn(OTHER_PASS_NUMBER);

        passMapper = new PassMapper();
    }

    @Test
    public void givenSinglePass_whenMappingToDTO_thenReturnsDTOWithCorrectAttributes() {
        PassDto passDto = passMapper.toDto(singlePass);

        assertEquals(passDto.passCategory, SOME_PASS_CATEGORY.toString());
        assertEquals(passDto.passOption, OTHER_PASS_OPTION.toString());
        assertEquals(passDto.passNumber, OTHER_PASS_NUMBER.getValue());
        assertEquals(passDto.getClass(), SinglePassDto.class);
    }

    @Test
    public void givenPackagePass_whenMappingToDTO_thenReturnsDTOWithCorrectAttributes() {
        PassDto passDto = passMapper.toDto(packagePass);

        assertEquals(passDto.passCategory, SOME_PASS_CATEGORY.toString());
        assertEquals(passDto.passOption, SOME_PASS_OPTION.toString());
        assertEquals(passDto.passNumber, SOME_PASS_NUMBER.getValue());
        assertEquals(passDto.getClass(), PassDto.class);
    }
}