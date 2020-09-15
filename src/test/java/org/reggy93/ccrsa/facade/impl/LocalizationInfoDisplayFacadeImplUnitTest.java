package org.reggy93.ccrsa.facade.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.reggy93.ccrsa.facade.dto.car.LocalizationDTO;
import org.reggy93.ccrsa.facade.exception.FacadeOperationException;
import org.reggy93.ccrsa.service.LocalizationService;
import org.reggy93.ccrsa.service.entity.car.Localization;
import org.reggy93.ccrsa.service.exception.ServiceOperationException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * Unit test for {@link LocalizationInfoDisplayFacadeImpl}.
 *
 * @author Reggy93 <marcin.z.wrobel@gmail.com>
 * created on 15 Sep 2020
 */
@ExtendWith(MockitoExtension.class)
class LocalizationInfoDisplayFacadeImplUnitTest {

    private static final String EXCEPTION_ASSERTION_MSG =
            "FacadeOperationException should be thrown as wrapper exception for ServiceOperationException";

    private static final String CITY_NAME = "cityName";

    @InjectMocks
    private LocalizationInfoDisplayFacadeImpl testedFacade;

    @Mock
    private LocalizationService localizationService;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private Localization localization1;

    @Mock
    private Localization localization2;

    @Mock
    private LocalizationDTO localizationDTO1;

    @Mock
    private LocalizationDTO localizationDTO2;

    @BeforeEach
    void setUp() {
        testedFacade = new LocalizationInfoDisplayFacadeImpl(localizationService, modelMapper);
    }

    @Test
    void getAllLocalizationsTest() throws ServiceOperationException, FacadeOperationException {

        final List<Localization> localizationList = List.of(localization1, localization2);
        when(localizationService.retrieveAllLocalizations()).thenReturn(localizationList);
        when(modelMapper.map(eq(localizationList), any(new TypeToken<Set<LocalizationDTO>>() {
        }.getType().getClass()))).thenReturn(Set.of(localizationDTO1, localizationDTO2));

        final Set<LocalizationDTO> localizationDTOResultSet = testedFacade.getAllLocalizations();

        assertThat(localizationDTOResultSet, is(not(empty())));
        assertThat(localizationDTOResultSet, hasSize(2));
        assertThat(localizationDTOResultSet, hasItems(localizationDTO1, localizationDTO2));
        verify(modelMapper, times(1)).map(anyList(), any(new TypeToken<Set<LocalizationDTO>>() {
        }.getType().getClass()));
    }

    @Test
    void getAllLocalizationsWhenNoLocalizationRetrievedTest() throws ServiceOperationException, FacadeOperationException {

        when(localizationService.retrieveAllLocalizations()).thenReturn(Collections.emptyList());
        assertThat(testedFacade.getAllLocalizations(), is(empty()));
        verify(modelMapper, never()).map(anyList(), any(new TypeToken<Set<LocalizationDTO>>() {
        }.getType().getClass()));
    }

    @Test
    void getAllLocalizationsWhenServiceOperationExceptionThrownTest() throws ServiceOperationException {

        when(localizationService.retrieveAllLocalizations()).thenThrow(new ServiceOperationException("Get all " +
                "localizations test exception"));
        assertThrows(FacadeOperationException.class, () -> testedFacade.getAllLocalizations(), () ->
                EXCEPTION_ASSERTION_MSG);
        verify(modelMapper, never()).map(any(),
                any(new TypeToken<Set<LocalizationDTO>>() {
                }.getType().getClass()));
    }

    @Test
    void getLocalizationByIdTest() throws ServiceOperationException, FacadeOperationException {

        when(localizationService.retrieveLocalizationById(anyLong())).thenReturn(Optional.of(localization1));
        when(modelMapper.map(eq(localization1), any(new TypeToken<LocalizationDTO>() {
        }.getType().getClass()))).thenReturn(localizationDTO1);

        final Optional<LocalizationDTO> localizationDTOResultOptional = testedFacade.getLocalizationById(1L);

        assertThat(localizationDTOResultOptional, is(notNullValue()));
        assertThat(localizationDTOResultOptional.isPresent(), is(true));
        assertThat(localizationDTOResultOptional.get(), is(localizationDTO1));
        verify(modelMapper, times(1)).map(eq(localization1), any(new TypeToken<LocalizationDTO>() {
        }.getType().getClass()));
    }

    @Test
    void getLocalizationByIdWhenNoLocalizationRetrievedTest() throws ServiceOperationException, FacadeOperationException {

        when(localizationService.retrieveLocalizationById(anyLong())).thenReturn(Optional.empty());

        final Optional<LocalizationDTO> localizationDTOResultOptional = testedFacade.getLocalizationById(1L);
        assertThat(localizationDTOResultOptional, is(notNullValue()));
        assertThat(localizationDTOResultOptional.isEmpty(), is(true));
        verify(modelMapper, never()).map(any(Localization.class), any(new TypeToken<LocalizationDTO>() {
        }.getType().getClass()));
    }

    @Test
    void getLocalizationByIdWhenServiceOperationExceptionThrownTest() throws ServiceOperationException {

        when(localizationService.retrieveLocalizationById(anyLong())).thenThrow(new ServiceOperationException("Get " +
                "localization by id test exception"));

        assertThrows(FacadeOperationException.class, () -> testedFacade.getLocalizationById(1L),
                () -> EXCEPTION_ASSERTION_MSG);
        verify(modelMapper, never()).map(any(Localization.class), any(new TypeToken<LocalizationDTO>() {
        }.getType().getClass()));
    }

    @Test
    void getLocalizationByCityNameTest() throws ServiceOperationException, FacadeOperationException {

        when(localizationService.retrieveLocalizationByCityName(anyString())).thenReturn(Optional.of(localization1));
        when(modelMapper.map(eq(localization1), any(new TypeToken<LocalizationDTO>() {
        }.getType().getClass()))).thenReturn(localizationDTO1);

        final Optional<LocalizationDTO> localizationDTOResultOptional = testedFacade.getLocalizationByCityName(
                CITY_NAME);

        assertThat(localizationDTOResultOptional, is(notNullValue()));
        assertThat(localizationDTOResultOptional.isPresent(), is(true));
        assertThat(localizationDTOResultOptional.get(), is(localizationDTO1));
        verify(modelMapper, times(1)).map(eq(localization1), any(new TypeToken<LocalizationDTO>() {
        }.getType().getClass()));
    }

    @Test
    void getLocalizationByCityNameWhenNoLocalizationRetrievedTest() throws ServiceOperationException, FacadeOperationException {

        when(localizationService.retrieveLocalizationByCityName(anyString())).thenReturn(Optional.empty());

        final Optional<LocalizationDTO> localizationDTOResultOptional = testedFacade.getLocalizationByCityName(
                CITY_NAME);

        assertThat(localizationDTOResultOptional, is(notNullValue()));
        assertThat(localizationDTOResultOptional.isEmpty(), is(true));
        verify(modelMapper, never()).map(any(Localization.class), any(new TypeToken<LocalizationDTO>() {
        }.getType().getClass()));
    }

    @Test
    void getLocalizationByCityNameWhenServiceOperationExceptionThrownTest() throws ServiceOperationException {

        when(localizationService.retrieveLocalizationByCityName(anyString())).thenThrow(
                new ServiceOperationException("Get localization by city name test exception"));

        assertThrows(FacadeOperationException.class, () -> testedFacade.getLocalizationByCityName(CITY_NAME),
                () -> EXCEPTION_ASSERTION_MSG);
    }
}