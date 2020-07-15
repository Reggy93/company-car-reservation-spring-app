package org.reggy93.ccrsa.facade;

import org.reggy93.ccrsa.facade.dto.api.car.CarListDisplayDTO;
import org.reggy93.ccrsa.facade.exception.FacadeOperationException;

import java.util.Set;

/**
 * Interface used for serving car list and car details requests.
 *
 * @author Reggy93 <marcin.z.wrobel@gmail.com>
 * created on 16 Jul 2020
 */
public interface CarInfoDisplayFacade {

    /**
     * Retrieves all cars
     *
     * @return set of all retrieved cars.
     */
    Set<CarListDisplayDTO> getAllCars() throws FacadeOperationException;

    CarListDisplayDTO getCarById(Long id);
}
