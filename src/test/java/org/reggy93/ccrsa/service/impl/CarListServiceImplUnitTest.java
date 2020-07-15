package org.reggy93.ccrsa.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reggy93.ccrsa.service.dao.CarRepository;
import org.reggy93.ccrsa.service.entity.car.Car;
import org.reggy93.ccrsa.service.exception.ServiceOperationException;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

/**
 * Unit test for {@link CarListServiceImpl}.
 *
 * @author Reggy93 <marcin.z.wrobel@gmail.com>
 * created on 20 Jul 2020
 */
@ExtendWith(MockitoExtension.class)
class CarListServiceImplUnitTest {

    @InjectMocks
    private CarListServiceImpl testedService;

    @Mock
    private CarRepository carRepository;

    @Mock
    private Car car1;

    @Mock
    private Car car2;

    @BeforeEach
    void setUp() {
        testedService = new CarListServiceImpl(carRepository);
    }

    @Test
    void retrieveAllCarsTest() throws ServiceOperationException {

        when(carRepository.findAll()).thenReturn(List.of(car1, car2));

        final List<Car> resultList = testedService.retrieveAllCars();
        assertFalse(CollectionUtils.isEmpty(resultList), () -> "Should retrieve 2 cars");
        assertThat(resultList, is(hasSize(2)));
        assertThat(resultList, hasItems(car1, car2));
    }

    @Test
    void retrieveAllCarsWhenNoCarsReturnedTest() throws ServiceOperationException {

        when(carRepository.findAll()).thenReturn(Collections.emptyList());
        assertTrue(testedService.retrieveAllCars().isEmpty());
    }

    @Test
    void retrieveAllCarsWhenRepositoryThrowsARuntimeExceptionTest() {

        when((carRepository.findAll())).thenThrow(new RuntimeException());
        assertThrows(ServiceOperationException.class, () -> testedService.retrieveAllCars(),
                () -> "ServiceOperationException should be thrown as RuntimeException is thrown by carRepository");
    }
}