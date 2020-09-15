package org.reggy93.ccrsa.service.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.reggy93.ccrsa.AbstractIntegrationTest;
import org.reggy93.ccrsa.service.entity.car.Localization;
import org.reggy93.ccrsa.service.exception.ServiceOperationException;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Integration test for {@link LocalizationServiceImpl}.
 *
 * @author Reggy93 <marcin.z.wrobel@gmail.com>
 * created on 15 Sep 2020
 */
class LocalizationServiceImplIntegrationTest extends AbstractIntegrationTest {

    private LocalizationServiceImpl testedService;

    @BeforeEach
    void setUp() {
        testedService = new LocalizationServiceImpl(localizationRepository);
    }

    @AfterEach
    void tearDown() {
        countryRepository.deleteAll();
    }

    @Test
    void retrieveAllLocalizationsTest() throws ServiceOperationException {

        final Localization localization1 = super.prepareLocalization(WARSAW, POLAND);
        final Localization localization2 = super.prepareLocalization(BERLIN, GERMANY);
        final Localization localization3 = super.prepareLocalization(PARIS, FRANCE);

        localizationRepository.saveAll(List.of(localization1, localization2, localization3));

        final List<Localization> localizationResultList = testedService.retrieveAllLocalizations();

        assertThat(localizationResultList, is(not(empty())));
        assertThat(localizationResultList, hasSize(3));
        assertThat(localizationResultList, hasItems(localization1, localization2, localization3));
    }

    @Test
    void retrieveAllLocalizationsWhenNoLocalizationReturnedTest() throws ServiceOperationException {

        assertThat(testedService.retrieveAllLocalizations(), is(empty()));
    }

    @Test
    void retrieveLocalizationByIdTest() throws ServiceOperationException {

        final Localization localization1 = super.prepareLocalization(WARSAW, POLAND);
        localizationRepository.save(localization1);

        final Long savedLocalizationId =
                localizationRepository.findAll().stream().map(Localization::getId).findFirst().orElse(0L);

        final Optional<Localization> result = testedService.retrieveLocalizationById(savedLocalizationId);

        assertThat(result, is(notNullValue()));
        assertThat(result.isPresent(), is(true));
        assertThat(result.get().getCity(), is(localization1.getCity()));
        assertThat(result.get().getCountry().getName(), is(localization1.getCountry().getName()));
    }

    @Test
    void retrieveLocalizationByIdWhenNoLocalizationReturnedTest() throws ServiceOperationException {

        final Optional<Localization> result = testedService.retrieveLocalizationById(1L);
        assertThat(result, is(notNullValue()));
        assertThat(result.isEmpty(), is(true));
    }

    @Test
    void retrieveLocalizationByCityNameTest() throws ServiceOperationException {

        final Localization localization1 = super.prepareLocalization(WARSAW, POLAND);
        localizationRepository.save(localization1);

        final Optional<Localization> result = testedService.retrieveLocalizationByCityName(localization1.getCity());

        assertThat(result, is(notNullValue()));
        assertThat(result.isPresent(), is(true));
        assertThat(result.get().getCity(), is(localization1.getCity()));
        assertThat(result.get().getCountry().getName(), is(localization1.getCountry().getName()));
    }
}