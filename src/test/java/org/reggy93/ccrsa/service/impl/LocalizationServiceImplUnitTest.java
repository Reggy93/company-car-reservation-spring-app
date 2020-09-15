package org.reggy93.ccrsa.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reggy93.ccrsa.service.dao.LocalizationRepository;
import org.reggy93.ccrsa.service.entity.car.Localization;
import org.reggy93.ccrsa.service.exception.ServiceOperationException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Unit test for {@link LocalizationServiceImpl}.
 *
 * @author Reggy93 <marcin.z.wrobel@gmail.com>
 * created on 15 Sep 2020
 */
@ExtendWith(MockitoExtension.class)
class LocalizationServiceImplUnitTest {

    private static final String SERVICE_OPERATION_EXCEPTION_ASSERTION_MSG =
            "Service operation exception should be thrown as wrapper for the thrown RuntimeException";

    private static final String CITY_NAME = "cityName";

    @InjectMocks
    private LocalizationServiceImpl testedService;

    @Mock
    private LocalizationRepository localizationRepository;

    @Mock
    private Localization localization1;

    @Mock
    private Localization localization2;

    @BeforeEach
    void setUp() {
        testedService = new LocalizationServiceImpl(localizationRepository);
    }

    @Test
    void retrieveAllLocalizationsTest() throws ServiceOperationException {

        when(localizationRepository.findAll()).thenReturn(List.of(localization1, localization2));

        final List<Localization> resultList = testedService.retrieveAllLocalizations();

        assertThat(resultList, is(not(empty())));
        assertThat(resultList, hasSize(2));
        assertThat(resultList, hasItems(localization1, localization2));
    }

    @Test
    void retrieveAllLocalizationsWhenNoLocalizationReturnedTest() throws ServiceOperationException {

        when(localizationRepository.findAll()).thenReturn(Collections.emptyList());
        final List<Localization> resultList = testedService.retrieveAllLocalizations();
        assertThat(resultList, is(empty()));
    }

    @Test
    void retrieveAllLocalizationsWhenRuntimeExceptionThrownTest() {

        when(localizationRepository.findAll()).thenThrow(new RuntimeException("Retrieve all localizations test " +
                "exception"));

        assertThrows(ServiceOperationException.class, () -> testedService.retrieveAllLocalizations(), () ->
                SERVICE_OPERATION_EXCEPTION_ASSERTION_MSG);
    }

    @Test
    void retrieveLocalizationByIdTest() throws ServiceOperationException {

        when(localizationRepository.findById(anyLong())).thenReturn(Optional.of(localization1));

        final Optional<Localization> result = testedService.retrieveLocalizationById(1L);
        assertThat(result, is(notNullValue()));
        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), is(localization1));
    }

    @Test
    void retrieveLocalizationByIdWhenNoLocalizationReturnedTest() throws ServiceOperationException {

        when(localizationRepository.findById(anyLong())).thenReturn(Optional.empty());

        final Optional<Localization> result = testedService.retrieveLocalizationById(1L);
        assertThat(result, is(notNullValue()));
        assertThat(result.isEmpty(), is(true));
    }

    @Test
    void retrieveLocalizationByIdWhenRuntimeExceptionIsThrownTest() {

        when(localizationRepository.findById(anyLong())).thenThrow(new RuntimeException("Retrieve " +
                "localization by id test exception"));
        assertThrows(ServiceOperationException.class, () -> testedService.retrieveLocalizationById(1L), () ->
                SERVICE_OPERATION_EXCEPTION_ASSERTION_MSG);
    }

    @Test
    void retrieveLocalizationByCityNameTest() throws ServiceOperationException {

        when(localizationRepository.findByCity(anyString())).thenReturn(Optional.of(localization1));

        final Optional<Localization> result = testedService.retrieveLocalizationByCityName(CITY_NAME);
        assertThat(result, is(notNullValue()));
        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), is(localization1));
    }

    @Test
    void retrieveLocalizationByCityNameWhenNoLocalizationReturnedTest() throws ServiceOperationException {

        when(localizationRepository.findByCity(anyString())).thenReturn(Optional.empty());

        final Optional<Localization> result = testedService.retrieveLocalizationByCityName(CITY_NAME);
        assertThat(result, is(notNullValue()));
        assertThat(result.isEmpty(), is(true));
    }

    @Test
    void retrieveLocalizationByCityNameWhenRuntimeExceptionThrownTest() {

        when(localizationRepository.findByCity(anyString())).thenThrow(new RuntimeException("Retrieve localization by" +
                " city name test exception"));
        assertThrows(ServiceOperationException.class, () -> testedService.retrieveLocalizationByCityName(CITY_NAME),
                () -> SERVICE_OPERATION_EXCEPTION_ASSERTION_MSG);
    }
}