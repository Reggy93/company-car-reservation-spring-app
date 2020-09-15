package org.reggy93.ccrsa.facade;

import org.reggy93.ccrsa.facade.dto.car.LocalizationDTO;
import org.reggy93.ccrsa.facade.exception.FacadeOperationException;
import org.reggy93.ccrsa.service.exception.ServiceOperationException;

import java.util.Optional;
import java.util.Set;

/**
 * Interface used for serving country display requests.
 *
 * @author Reggy93 <marcin.z.wrobel@gmail.com>
 * created on 15 Sep 2020
 */
public interface LocalizationInfoDisplayFacade {

    /**
     * Gets all {@link LocalizationDTO}.
     *
     * @return {@link Set} of retrieved {@link LocalizationDTO}
     * @throws FacadeOperationException when {@link ServiceOperationException} is thrown in the service layer
     */
    Set<LocalizationDTO> getAllLocalizations() throws FacadeOperationException;

    /**
     * Gets {@link LocalizationDTO} by given {@code id}.
     *
     * @param id id of the {@link LocalizationDTO} to retrieve
     * @return {@link Optional} of {@link LocalizationDTO} if localization with given {@code id} is found or an empty
     * optional
     * @throws FacadeOperationException when {@link ServiceOperationException} is thrown in the service layer
     */
    Optional<LocalizationDTO> getLocalizationById(Long id) throws FacadeOperationException;

    /**
     * Gets {@link LocalizationDTO} by given {@code cityName}
     *
     * @param cityName name of the city assigned to actual localization
     * @return {@link Optional} of {@link LocalizationDTO} with giiven {@code cityName} or an empty optional
     * @throws FacadeOperationException when {@link ServiceOperationException} is thrown in the service layer
     */
    Optional<LocalizationDTO> getLocalizationByCityName(String cityName) throws FacadeOperationException;
}
