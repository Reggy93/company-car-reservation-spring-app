package org.reggy93.ccrsa.service.impl;

import org.reggy93.ccrsa.service.CarListService;
import org.reggy93.ccrsa.service.dao.CarRepository;
import org.reggy93.ccrsa.service.entity.car.Car;
import org.reggy93.ccrsa.service.exception.ServiceOperationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Default implementation of {@link CarListService}.
 *
 * @author Reggy93 <marcin.z.wrobel@gmail.com>
 * created on 13 Jul 2020
 */
@Service
public class CarListServiceImpl implements CarListService {

    private final CarRepository carRepository;

    @Autowired
    public CarListServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<Car> retrieveAllCars() throws ServiceOperationException {
        try {
            return carRepository.findAll();
        } catch (RuntimeException e) {
            throw new ServiceOperationException("Unable to retrieve list of all cars because of carRepository error:", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Car> retrieveCarById(final Long id) {
//        TODO: implement the logic
        return carRepository.findById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Car saveCar(final Car car) {
//        TODO: implement the logic
        return carRepository.save(car);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteAllCars() {
//    TODO: implement the logic
        carRepository.deleteAll();
    }
}
