package org.reggy93.ccrsa.service.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.reggy93.ccrsa.AbstractIntegrationTest;
import org.reggy93.ccrsa.service.dao.CountryRepository;
import org.reggy93.ccrsa.service.entity.car.Country;
import org.reggy93.ccrsa.service.exception.ServiceOperationException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Integration test for {@link CountryServiceImpl}.
 *
 * @author Reggy93 <marcin.z.wrobel@gmail.com>
 * created on 01 Sep 2020
 */
class CountryServiceImplIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private CountryRepository countryRepository;

    private CountryServiceImpl testedService;

    @BeforeEach
    void setUp() {
        testedService = new CountryServiceImpl(countryRepository);
    }

    @AfterEach
    void tearDown() {
        countryRepository.deleteAll();
    }

    @Test
    void retrieveAllCountriesTest() throws ServiceOperationException {

        final Country country1 = super.prepareCountry(POLAND);
        final Country country2 = super.prepareCountry(GERMANY);

        countryRepository.saveAll(List.of(country1, country2));

        final List<Country> countryList = testedService.retrieveAllCountries();

        assertThat(countryList, is(not(empty())));
        assertThat(countryList, hasItems(country1, country2));
    }

    @Test
    void retrieveAllCountriesWhenNoCountriesSavedInDatabaseTest() throws ServiceOperationException {

        assertThat(testedService.retrieveAllCountries(), is(empty()));
    }

    @Test
    void retrieveCountryByIdTest() throws ServiceOperationException {

        final Country country1 = super.prepareCountry(POLAND);

        countryRepository.save(country1);

        final Long firstRetrievedCountryId =
                countryRepository.findAll().stream().findFirst().map(Country::getId).orElse(0L);

        final Optional<Country> result =
                testedService.retrieveCountryById(firstRetrievedCountryId);

        assertTrue(result.isPresent());
        assertThat(result.get().getName(), is(POLAND));
    }

    @Test
    void retrieveCountryByIdWhenNoCountryReturnedTest() throws ServiceOperationException {

        assertTrue(testedService.retrieveCountryById(0L).isEmpty());
    }
}