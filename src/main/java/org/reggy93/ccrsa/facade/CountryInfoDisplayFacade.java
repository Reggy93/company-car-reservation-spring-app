package org.reggy93.ccrsa.facade;

import org.reggy93.ccrsa.facade.dto.car.CountryDTO;
import org.reggy93.ccrsa.facade.exception.FacadeOperationException;
import org.springframework.lang.NonNull;

import java.util.Optional;
import java.util.Set;

/**
 * Interface used for serving country display requests.
 *
 * @author Reggy93 <marcin.z.wrobel@gmail.com>
 * created on 02 Sep 2020
 */
public interface CountryInfoDisplayFacade {

    Set<CountryDTO> getAllCountries() throws FacadeOperationException;

    Optional<CountryDTO> getCountryById(@NonNull Long id) throws FacadeOperationException;
}
