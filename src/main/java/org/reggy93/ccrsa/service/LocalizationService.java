package org.reggy93.ccrsa.service;

import org.reggy93.ccrsa.service.entity.car.Localization;
import org.reggy93.ccrsa.service.exception.ServiceOperationException;

import java.util.List;
import java.util.Optional;

/**
 * Service for serving Localization requests.
 *
 * @author Reggy93 <marcin.z.wrobel@gmail.com>
 * created on 15 Sep 2020
 */
public interface LocalizationService {

    /**
     * Retrieves list of all {@link Localization} found in the database.
     *
     * @return {@link List} of all {@link Localization} found in the database
     * @throws ServiceOperationException when any {@link RuntimeException} occurs
     */
    List<Localization> retrieveAllLocalizations() throws ServiceOperationException;

    /**
     * Retrieves {@link Localization} by given {@code id}.
     *
     * @param id id of the {@link Localization} to retrieve
     * @return {@link Optional} of {@link Localization} with given {@code id} or an empty optional if localization
     * was not found
     * @throws ServiceOperationException when any {@link RuntimeException} occurs
     */
    Optional<Localization> retrieveLocalizationById(Long id) throws ServiceOperationException;

    /**
     * Retrieves {@link Localization} by given name of the city
     *
     * @param cityName name of the city assigned to actual {@link Localization}
     * @return {@link Optional} of {@link Localization} with given {@code cityName} or an empty optional if localization
     * was not found
     * @throws ServiceOperationException when any {@link RuntimeException} occurs
     */
    Optional<Localization> retrieveLocalizationByCityName(String cityName) throws ServiceOperationException;
}
