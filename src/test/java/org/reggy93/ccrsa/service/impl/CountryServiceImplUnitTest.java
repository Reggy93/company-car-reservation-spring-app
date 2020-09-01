package org.reggy93.ccrsa.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reggy93.ccrsa.service.dao.CountryRepository;
import org.reggy93.ccrsa.service.entity.car.Country;
import org.reggy93.ccrsa.service.exception.ServiceOperationException;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

/**
 * Unit test for {@link CountryServiceImpl}.
 *
 * @author Reggy93 <marcin.z.wrobel@gmail.com>
 * created on 01 Sep 2020
 */
@ExtendWith(MockitoExtension.class)
class CountryServiceImplUnitTest {

    private static final Long COUNTRY_POLAND_ID = 1L;

    private static final String SERVICE_OPERATION_EXCEPTION_SHOULD_BE_THROWN = "ServiceOperationException should be " +
            "thrown as RuntimeException is thrown by countryRepository";

    @InjectMocks
    private CountryServiceImpl testedService;

    @Mock
    private CountryRepository countryRepository;

    @Mock
    private Country country1;

    @Mock
    private Country country2;

    @BeforeEach
    void setUp() {
        testedService = new CountryServiceImpl(countryRepository);
    }

    @Test
    void retrieveAllCountriesTest() throws ServiceOperationException {

        when(countryRepository.findAll()).thenReturn(List.of(country1, country2));

        final List<Country> resultList = testedService.retrieveAllCountries();

        assertFalse(CollectionUtils.isEmpty(resultList), () -> "Should retrieve 2 countries");
        assertThat(resultList, hasSize(2));
        assertThat(resultList, hasItems(country1, country2));
    }

    @Test
    void retrieveAllCountriesWhenNoCountryReturnedTest() throws ServiceOperationException {

        assertTrue(CollectionUtils.isEmpty(testedService.retrieveAllCountries()));
    }

    @Test
    void retrieveAllCountriesWhenRuntimeExceptionIsThrownTest() {

        when(countryRepository.findAll()).thenThrow(new RuntimeException());

        assertThrows(ServiceOperationException.class, () -> testedService.retrieveAllCountries(), () ->
                SERVICE_OPERATION_EXCEPTION_SHOULD_BE_THROWN);
    }

    @Test
    void retrieveCountryByIdTest() throws ServiceOperationException {

        when(countryRepository.findById(COUNTRY_POLAND_ID)).thenReturn(Optional.of(country1));

        final Optional<Country> result = testedService.retrieveCountryById(COUNTRY_POLAND_ID);

        assertTrue(result.isPresent());
        assertThat(result.get(), is(country1));
    }

    @Test
    void retrieveCountryByIdWhenNoCountryReturnedTest() throws ServiceOperationException {

        assertTrue(testedService.retrieveCountryById(COUNTRY_POLAND_ID).isEmpty());
    }

    @Test
    void retrieveCountryByIdWhenRuntimeExceptionIsThrownTest() {

        when(countryRepository.findById(COUNTRY_POLAND_ID)).thenThrow(new RuntimeException());

        assertThrows(ServiceOperationException.class, () -> testedService.retrieveCountryById(COUNTRY_POLAND_ID), () ->
                SERVICE_OPERATION_EXCEPTION_SHOULD_BE_THROWN);
    }
}