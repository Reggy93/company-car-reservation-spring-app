package org.reggy93.ccrsa.facade.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.reggy93.ccrsa.facade.dto.car.CountryDTO;
import org.reggy93.ccrsa.facade.exception.FacadeOperationException;
import org.reggy93.ccrsa.service.CountryService;
import org.reggy93.ccrsa.service.entity.car.Country;
import org.reggy93.ccrsa.service.exception.ServiceOperationException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * Unit test for {@link CountryInfoDisplayFacadeImpl}.
 *
 * @author Reggy93 <marcin.z.wrobel@gmail.com>
 * created on 02 Sep 2020
 */
@ExtendWith(MockitoExtension.class)
class CountryInfoDisplayFacadeImplUnitTest {

    private static final String SERVICE_LAYER_EXCEPTION = "Service layer exception";

    @InjectMocks
    private CountryInfoDisplayFacadeImpl testedFacade;

    @Mock
    private CountryService countryService;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private Country country1;

    @Mock
    private Country country2;

    @Mock
    private CountryDTO countryDTO1;

    @Mock
    private CountryDTO countryDTO2;

    @BeforeEach
    void setUp() {
        testedFacade = new CountryInfoDisplayFacadeImpl(countryService, modelMapper);
    }

    @Test
    void getAllCountriesTest() throws ServiceOperationException, FacadeOperationException {

        final List<Country> retrievedCountryList = List.of(country1, country2);

        when(countryService.retrieveAllCountries()).thenReturn(retrievedCountryList);
        when(modelMapper.map(eq(retrievedCountryList), any(new TypeToken<Set<CountryDTO>>() {
        }.getType().getClass()))).thenReturn(Set.of(countryDTO1, countryDTO2));

        final Set<CountryDTO> resultSet = testedFacade.getAllCountries();

        assertThat(resultSet, is(not(empty())));
        assertThat(resultSet, is(hasSize(2)));
        assertThat(resultSet, hasItems(countryDTO1, countryDTO2));
        verify(modelMapper, times(1)).map(anyList(), any(new TypeToken<Set<CountryDTO>>() {
        }.getType().getClass()));
    }

    @Test
    void getAllCountriesWhenNoCountriesRetrievedFromServiceTest() throws ServiceOperationException, FacadeOperationException {

        when(countryService.retrieveAllCountries()).thenReturn(Collections.emptyList());

        final Set<CountryDTO> resultSet = testedFacade.getAllCountries();

        assertThat(resultSet, is(empty()));
        verify(modelMapper, never()).map(anyList(), any());
    }

    @Test
    void getAllCountriesWhenServiceThrowsAnExceptionTest() throws ServiceOperationException {

        when(countryService.retrieveAllCountries()).thenThrow(new ServiceOperationException(SERVICE_LAYER_EXCEPTION));

        assertThrows(FacadeOperationException.class, () -> testedFacade.getAllCountries(), () ->
                "FacadeOperationException should be thrown as wrapper for ServiceOperationException while getting all" +
                        " countries");
    }

    @Test
    void getCountryByIdTest() throws ServiceOperationException, FacadeOperationException {

        when(countryService.retrieveCountryById(anyLong())).thenReturn(Optional.of(country1));

        when(modelMapper.map(eq(country1), any(new TypeToken<CountryDTO>() {
        }.getType().getClass()))).thenReturn(countryDTO1);

        final Optional<CountryDTO> result = testedFacade.getCountryById(1L);

        assertTrue(result.isPresent());
        assertThat(countryDTO1, is(result.get()));
        verify(modelMapper, times(1)).map(any(), any(new TypeToken<CountryDTO>() {
        }.getType().getClass()));
    }

    @Test
    void getCountryByIdWhenNoCountryRetrievedFromServiceTest() throws FacadeOperationException {

        final Optional<CountryDTO> result = testedFacade.getCountryById(1L);

        assertTrue(result.isEmpty());
        verify(modelMapper, never()).map(any(), any());
    }

    @Test
    void getCountryByIdWhenServiceThrowsAnExceptionTest() throws ServiceOperationException {

        when(countryService.retrieveCountryById(anyLong())).thenThrow(new ServiceOperationException(SERVICE_LAYER_EXCEPTION));

        assertThrows(FacadeOperationException.class, () -> testedFacade.getCountryById(1L), () ->
                "FacadeOperationException should be thrown as wrapper for ServiceOperationException while getting " +
                        "country by id");
    }
}