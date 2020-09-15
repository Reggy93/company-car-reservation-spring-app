package org.reggy93.ccrsa.endpoint.localization.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reggy93.ccrsa.facade.LocalizationInfoDisplayFacade;
import org.reggy93.ccrsa.facade.dto.car.LocalizationDTO;
import org.reggy93.ccrsa.facade.exception.FacadeOperationException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Mockito.*;

/**
 * Unit test for {@link LocalizationController}.
 *
 * @author Reggy93 <marcin.z.wrobel@gmail.com>
 * created on 26 Sep 2020
 */
@ExtendWith(MockitoExtension.class)
class LocalizationControllerUnitTest {

    private static final long LOCALIZATION_ID_1 = 1L;

    private static final String CITY_NAME = "cityName";

    @InjectMocks
    private LocalizationController testedController;

    @Mock
    private LocalizationInfoDisplayFacade localizationInfoDisplayFacade;

    @Mock
    private RepresentationModelAssembler<LocalizationDTO, LocalizationDTO> localizationDTORepresentationModelAssembler;

    @Mock
    private LocalizationDTO localizationDTO1;

    @Mock
    private LocalizationDTO localizationDTO2;

    @Mock
    private CollectionModel<LocalizationDTO> localizationDTOCollectionModel;

    @BeforeEach
    void setUp() {

        testedController = new LocalizationController(localizationInfoDisplayFacade,
                localizationDTORepresentationModelAssembler);
    }

    @Test
    void getAllLocalizationsTest() throws FacadeOperationException {

        final Set<LocalizationDTO> retrievedLocalizationDTOSet = Set.of(localizationDTO1, localizationDTO2);
        when(localizationInfoDisplayFacade.getAllLocalizations()).thenReturn(retrievedLocalizationDTOSet);
        when(localizationDTORepresentationModelAssembler.toCollectionModel(retrievedLocalizationDTOSet))
                .thenReturn(localizationDTOCollectionModel);

        final ResponseEntity<CollectionModel<LocalizationDTO>> result = testedController.getAllLocalizations();

        verify(localizationDTORepresentationModelAssembler, times(1)).toCollectionModel(retrievedLocalizationDTOSet);
        assertThat(result, is(notNullValue()));
        assertThat(result.getBody(), is(notNullValue()));
        assertThat(result.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    void getAllLocalizationsWhenNoLocalizationRetrievedTest() throws FacadeOperationException {

        when(localizationInfoDisplayFacade.getAllLocalizations()).thenReturn(Collections.emptySet());

        final ResponseEntity<CollectionModel<LocalizationDTO>> result = testedController.getAllLocalizations();

        verify(localizationDTORepresentationModelAssembler, never()).toCollectionModel(anySet());
        assertThat(result, is(notNullValue()));
        assertThat(result.getBody(), is(nullValue()));
        assertThat(result.getStatusCode(), is(HttpStatus.NO_CONTENT));
    }

    @Test
    void getAllLocalizationsWhenFacadeExceptionThrownTest() throws FacadeOperationException {

        when(localizationInfoDisplayFacade.getAllLocalizations()).thenThrow(new FacadeOperationException("Test " +
                "exception while retrieving all localizations"));

        final ResponseEntity<CollectionModel<LocalizationDTO>> result = testedController.getAllLocalizations();

        verify(localizationDTORepresentationModelAssembler, never()).toCollectionModel(anySet());
        assertThat(result, is(notNullValue()));
        assertThat(result.getBody(), is(nullValue()));
        assertThat(result.getStatusCode(), is(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @Test
    void getLocalizationByIdTest() throws FacadeOperationException {

        when(localizationInfoDisplayFacade.getLocalizationById(LOCALIZATION_ID_1)).thenReturn(Optional.of(localizationDTO1));
        when(localizationDTORepresentationModelAssembler.toModel(localizationDTO1)).thenReturn(localizationDTO1);

        final ResponseEntity<LocalizationDTO> result = testedController.getLocalizationById(LOCALIZATION_ID_1);

        verify(localizationDTORepresentationModelAssembler, times(1)).toModel(localizationDTO1);
        assertThat(result, is(notNullValue()));
        assertThat(result.getBody(), is(notNullValue()));
        assertThat(result.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    void getLocalizationByIdWhenNoLocalizationRetrievedTest() throws FacadeOperationException {

        when(localizationInfoDisplayFacade.getLocalizationById(LOCALIZATION_ID_1)).thenReturn(Optional.empty());

        final ResponseEntity<LocalizationDTO> result = testedController.getLocalizationById(LOCALIZATION_ID_1);

        verify(localizationDTORepresentationModelAssembler, never()).toModel(any(LocalizationDTO.class));
        assertThat(result, is(notNullValue()));
        assertThat(result.getBody(), is(nullValue()));
        assertThat(result.getStatusCode(), is(HttpStatus.NO_CONTENT));
    }

    @Test
    void getLocalizationByIdWhenFacadeExceptionThrownTest() throws FacadeOperationException {

        when(localizationInfoDisplayFacade.getLocalizationById(LOCALIZATION_ID_1)).thenThrow(
                new FacadeOperationException("Test exception while retrieving localization by id"));

        final ResponseEntity<LocalizationDTO> result = testedController.getLocalizationById(LOCALIZATION_ID_1);

        verify(localizationDTORepresentationModelAssembler, never()).toModel(any(LocalizationDTO.class));
        assertThat(result, is(notNullValue()));
        assertThat(result.getBody(), is(nullValue()));
        assertThat(result.getStatusCode(), is(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @Test
    void getLocalizationByCityNameTest() throws FacadeOperationException {

        when(localizationInfoDisplayFacade.getLocalizationByCityName(CITY_NAME)).thenReturn(Optional.of(localizationDTO1));
        when(localizationDTORepresentationModelAssembler.toModel(localizationDTO1)).thenReturn(localizationDTO1);

        final ResponseEntity<LocalizationDTO> result = testedController.getLocalizationByCityName(CITY_NAME);

        verify(localizationDTORepresentationModelAssembler, times(1)).toModel(any(LocalizationDTO.class));
        assertThat(result, is(notNullValue()));
        assertThat(result.getBody(), is(notNullValue()));
        assertThat(result.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    void getLocalizationByCityNameWhenNoLocalizationRetrievedTest() throws FacadeOperationException {

        when(localizationInfoDisplayFacade.getLocalizationByCityName(CITY_NAME)).thenReturn(Optional.empty());

        final ResponseEntity<LocalizationDTO> result = testedController.getLocalizationByCityName(CITY_NAME);

        verify(localizationDTORepresentationModelAssembler, never()).toModel(any(LocalizationDTO.class));
        assertThat(result, is(notNullValue()));
        assertThat(result.getBody(), is(nullValue()));
        assertThat(result.getStatusCode(), is(HttpStatus.NO_CONTENT));
    }

    @Test
    void getLocalizationByCityNameWhenFacadeExceptionThrowTest() throws FacadeOperationException {

        when(localizationInfoDisplayFacade.getLocalizationByCityName(CITY_NAME)).
                thenThrow(new FacadeOperationException("Test exception while retrieving localization by city name"));

        final ResponseEntity<LocalizationDTO> result = testedController.getLocalizationByCityName(CITY_NAME);

        verify(localizationDTORepresentationModelAssembler, never()).toModel(any(LocalizationDTO.class));
        assertThat(result, is(notNullValue()));
        assertThat(result.getBody(), is(nullValue()));
        assertThat(result.getStatusCode(), is(HttpStatus.INTERNAL_SERVER_ERROR));
    }
}