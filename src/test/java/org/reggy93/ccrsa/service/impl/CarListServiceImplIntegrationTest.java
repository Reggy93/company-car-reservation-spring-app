package org.reggy93.ccrsa.service.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.reggy93.ccrsa.AbstractIntegrationTest;
import org.reggy93.ccrsa.service.CarListService;
import org.reggy93.ccrsa.service.dao.CarRepository;
import org.reggy93.ccrsa.service.entity.car.Car;
import org.reggy93.ccrsa.service.entity.car.FuelType;
import org.reggy93.ccrsa.service.exception.ServiceOperationException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.Is.is;

/**
 * Integration test for {@link CarListServiceImpl}.
 *
 * @author Reggy93 <marcin.z.wrobel@gmail.com>
 * created on 24 Jul 2020
 */
class CarListServiceImplIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private CarRepository carRepository;

    private CarListService testedService;

    @BeforeEach
    void setUp() {
        testedService = new CarListServiceImpl(carRepository);
    }

    @AfterEach
    void tearDown() {
        makeRepository.deleteAll();
        countryRepository.deleteAll();
        carRepository.deleteAll();
    }

    @Test
    void retrieveAllCarsTest() throws ServiceOperationException {

        final Car car1 = super.prepareCar(SKODA, FABIA, POLAND, WARSAW, 1.2f,
                FuelType.LPG, 100, 5, "EL123W");

        final Car car2 = super.prepareCar(AUDI, A_3, GERMANY, BERLIN, 1.4f,
                FuelType.PB, 140, 3, "DE234GE");

        final Car car3 = super.prepareCar(SKODA, SUPERB, POLAND, WARSAW, 2.0f,
                FuelType.ON, 170, 5, "WE123Q");

        carRepository.saveAll(List.of(car1, car2, car3));

        final List<Car> carList = testedService.retrieveAllCars();

        assertThat(carList, is(not(empty())));
        assertThat(carList, hasItems(car1, car2, car3));

        final Car retrievedCar1 = carList.stream().filter(car -> getRegistrationNumberFromCar(car).equals(
                getRegistrationNumberFromCar(car1))).findAny().orElse(new Car());


        final Car retrievedCar2 = carList.stream().filter(car -> getRegistrationNumberFromCar(car).equals(
                getRegistrationNumberFromCar(car2))).findAny().orElse(new Car());

        final Car retrievedCar3 = carList.stream().filter(car -> getRegistrationNumberFromCar(car).equals(
                getRegistrationNumberFromCar(car3))).findAny().orElse(new Car());

        assertThat(getCarModelNameFromCar(retrievedCar1), is(FABIA));
        assertThat(getCarModelMakeNameFromCar(retrievedCar1), is(SKODA));
        assertThat(getCountryNameFromCar(retrievedCar1), is(POLAND));

        assertThat(getCarModelNameFromCar(retrievedCar2), is(A_3));
        assertThat(getCarModelMakeNameFromCar(retrievedCar2), is(AUDI));
        assertThat(getCountryNameFromCar(retrievedCar2), is(GERMANY));

        assertThat(getCarModelNameFromCar(retrievedCar3), is(SUPERB));
        assertThat(getCarModelMakeNameFromCar(retrievedCar3), is(SKODA));
        assertThat(getCountryNameFromCar(retrievedCar3), is(POLAND));
    }

    @Test
    void retrieveAllCarsWhenNoCarsSavedInDataBaseTest() throws ServiceOperationException {

        assertThat(testedService.retrieveAllCars(), is(empty()));
    }

    private String getCountryNameFromCar(final Car car) {
        return car.getLocalization().getCountry().getName();
    }

    private String getCarModelNameFromCar(final Car car) {
        return car.getCarModel().getName();
    }

    private String getCarModelMakeNameFromCar(final Car car) {
        return car.getCarModel().getMake().getName();
    }

    private String getRegistrationNumberFromCar(final Car car) {
        return car.getCarDetails().getRegistrationNumber();
    }
}