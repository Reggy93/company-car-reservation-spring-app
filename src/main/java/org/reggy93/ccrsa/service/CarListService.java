package org.reggy93.ccrsa.service;

import org.reggy93.ccrsa.service.entity.car.Car;
import org.reggy93.ccrsa.service.exception.ServiceOperationException;

import java.util.List;
import java.util.Optional;

/**
 * Service for serving car list requests.
 *
 * @author Reggy93 <marcin.z.wrobel@gmail.com>
 * created on 13 Jul 2020
 */
public interface CarListService {

    /**
     * Retrieves all cars present in the database.
     *
     * @return {@link List} of {@link Car} retrieved from the database
     * @throws ServiceOperationException when an error occurs while retrieving the list of cars (for example database connection error).
     */
    List<Car> retrieveAllCars() throws ServiceOperationException;

    Optional<Car> retrieveCarById(Long id);

    /**
     * Saves given {@link Car} in the database.
     *
     * @param car {@link Car} to save
     * @return Saved {@link Car} entity
     */
    Car saveCar(Car car);

    /**
     * Deletes all {@link Car} entities in the database using batch mode.
     */
    void deleteAllCars();
}
