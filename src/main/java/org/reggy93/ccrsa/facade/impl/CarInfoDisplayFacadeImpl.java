package org.reggy93.ccrsa.facade.impl;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.reggy93.ccrsa.facade.CarInfoDisplayFacade;
import org.reggy93.ccrsa.facade.dto.api.car.CarListDisplayDTO;
import org.reggy93.ccrsa.facade.exception.FacadeOperationException;
import org.reggy93.ccrsa.service.CarListService;
import org.reggy93.ccrsa.service.entity.car.Car;
import org.reggy93.ccrsa.service.exception.ServiceOperationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Default implementation of {@link CarInfoDisplayFacade}.
 *
 * @author Reggy93 <marcin.z.wrobel@gmail.com>
 * created on 16 Jul 2020
 */
@Component
public class CarInfoDisplayFacadeImpl implements CarInfoDisplayFacade {

    private final CarListService carListService;

    private final ModelMapper modelMapper;

    @Autowired
    public CarInfoDisplayFacadeImpl(CarListService carListService, ModelMapper modelMapper) {
        this.carListService = carListService;
        this.modelMapper = modelMapper;
    }

    @Override
    public Set<CarListDisplayDTO> getAllCars() throws FacadeOperationException {

        final List<Car> carList;
        try {
            carList = carListService.retrieveAllCars();
        } catch (ServiceOperationException e) {
            throw new FacadeOperationException(
                    "Couldn't retrieve the list of all cars because of service layer exception:", e);
        }

        if (CollectionUtils.isEmpty(carList)) {
            return Collections.emptySet();
        }

        return modelMapper.map(carList, new TypeToken<Set<CarListDisplayDTO>>() {
        }.getType());
    }

    @Override
    public CarListDisplayDTO getCarById(final Long id) {

        final Optional<Car> retrievedCar = carListService.retrieveCarById(id);

        return (CarListDisplayDTO) retrievedCar.map(car -> modelMapper.map(car, new TypeToken<CarListDisplayDTO>() {
        }.getType())).orElse(null);
    }
}
