package org.reggy93.ccrsa.service;

import org.reggy93.ccrsa.service.entity.car.Country;
import org.reggy93.ccrsa.service.exception.ServiceOperationException;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

/**
 * Service for serving Country requests.
 *
 * @author Reggy93 <marcin.z.wrobel@gmail.com>
 * created on 01 Sep 2020
 */
public interface CountryService {

    /**
     * Retrieves all countries present in the database.
     *
     * @return {@link List} of {@link Country} present in the database
     * @throws ServiceOperationException when {@code RuntimeException} arises
     */
    List<Country> retrieveAllCountries() throws ServiceOperationException;

    /**
     * Retrieves country by id.
     *
     * @param id {@code id} of the {@link Country} to retrieve
     * @return {@link Country} with passed {@code id} or {@code null} when not found
     */
    Optional<Country> retrieveCountryById(@NonNull Long id) throws ServiceOperationException;
}
