package org.reggy93.ccrsa.endpoint.localization;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reggy93.ccrsa.endpoint.localization.controller.CountryController;
import org.reggy93.ccrsa.facade.CountryInfoDisplayFacade;
import org.reggy93.ccrsa.facade.dto.car.CountryDTO;
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
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.Mockito.*;

/**
 * Unit test for {@link CountryController}.
 *
 * @author Reggy93 <marcin.z.wrobel@gmail.com>
 * created on 14 Sep 2020
 */
@ExtendWith(MockitoExtension.class)
class CountryControllerUnitTest {

    private static final long COUNTRY_ID = 1L;

    @InjectMocks
    private CountryController testedController;

    @Mock
    private CountryInfoDisplayFacade countryInfoDisplayFacade;

    @Mock
    private RepresentationModelAssembler<CountryDTO, CountryDTO> countryDTORepresentationModelAssembler;

    @Mock
    private CountryDTO countryDTO1;

    @Mock
    private CountryDTO countryDTO2;

    @Mock
    private CollectionModel<CountryDTO> countryDTOCollectionModel;

    @BeforeEach
    void setUp() {
        testedController = new CountryController(countryInfoDisplayFacade, countryDTORepresentationModelAssembler);
    }

    @Test
    void getAllCountriesTest() throws FacadeOperationException {

        final Set<CountryDTO> retrievedCountrySet = Set.of(countryDTO1, countryDTO2);
        when(countryInfoDisplayFacade.getAllCountries()).thenReturn(retrievedCountrySet);
        when(countryDTORepresentationModelAssembler.toCollectionModel(retrievedCountrySet)).thenReturn(countryDTOCollectionModel);

        final ResponseEntity<CollectionModel<CountryDTO>> result = testedController.getAllCountries();

        verify(countryDTORepresentationModelAssembler, times(1)).toCollectionModel(retrievedCountrySet);
        assertThat(result, is(notNullValue()));
        assertThat(result.getBody(), is(notNullValue()));
        assertThat(result.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    void getAllCountriesWhenNoCountriesRetrievedTest() throws FacadeOperationException {

        when(countryInfoDisplayFacade.getAllCountries()).thenReturn(Collections.emptySet());

        final ResponseEntity<CollectionModel<CountryDTO>> result = testedController.getAllCountries();

        assertThat(result.getStatusCode(), is(HttpStatus.NO_CONTENT));
        assertThat(result.getBody(), is(nullValue()));
        verify(countryDTORepresentationModelAssembler, never()).toCollectionModel(anySet());

    }

    @Test
    void getAllCountriesWhenFacadeExceptionThrownTest() throws FacadeOperationException {

        when(countryInfoDisplayFacade.getAllCountries()).thenThrow(new FacadeOperationException("Test exception while" +
                " retrieving all country list"));

        final ResponseEntity<CollectionModel<CountryDTO>> result = testedController.getAllCountries();

        assertThat(result.getStatusCode(), is(HttpStatus.INTERNAL_SERVER_ERROR));
        assertThat(result.getBody(), is(nullValue()));
        verify(countryDTORepresentationModelAssembler, never()).toCollectionModel(anySet());
    }

    @Test
    void getCountryByIdTest() throws FacadeOperationException {

        when(countryInfoDisplayFacade.getCountryById(COUNTRY_ID)).thenReturn(Optional.of(countryDTO1));
        when(countryDTORepresentationModelAssembler.toModel(countryDTO1)).thenReturn(countryDTO1);

        final ResponseEntity<CountryDTO> result = testedController.getCountryById(COUNTRY_ID);

        assertThat(result, is(notNullValue()));
        assertThat(result.getBody(), is(notNullValue()));
        assertThat(result.getStatusCode(), is(HttpStatus.OK));
        verify(countryDTORepresentationModelAssembler, times(1)).toModel(countryDTO1);
    }

    @Test
    void getCountryByIdWhenNoCountryRetrievedTest() throws FacadeOperationException {

        when(countryInfoDisplayFacade.getCountryById(anyLong())).thenReturn(Optional.empty());

        final ResponseEntity<CountryDTO> result = testedController.getCountryById(COUNTRY_ID);

        assertThat(result, is(notNullValue()));
        assertThat(result.getBody(), is(nullValue()));
        assertThat(result.getStatusCode(), is(HttpStatus.NO_CONTENT));
        verify(countryDTORepresentationModelAssembler, never()).toModel(any(CountryDTO.class));
    }

    @Test
    void getCountryByIdWhenFacadeOperationExceptionThrownTest() throws FacadeOperationException {

        when(countryInfoDisplayFacade.getCountryById(anyLong())).thenThrow(new FacadeOperationException("Test " +
                "exception while retrieving single country"));

        final ResponseEntity<CountryDTO> result = testedController.getCountryById(COUNTRY_ID);

        assertThat(result, is(notNullValue()));
        assertThat(result.getBody(), is(nullValue()));
        assertThat(result.getStatusCode(), is(HttpStatus.INTERNAL_SERVER_ERROR));
        verify(countryDTORepresentationModelAssembler, never()).toModel(any(CountryDTO.class));
    }
}