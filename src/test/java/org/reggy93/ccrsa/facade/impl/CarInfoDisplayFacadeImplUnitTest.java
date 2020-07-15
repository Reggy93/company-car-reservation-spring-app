package org.reggy93.ccrsa.facade.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.reggy93.ccrsa.facade.dto.api.car.CarListDisplayDTO;
import org.reggy93.ccrsa.facade.exception.FacadeOperationException;
import org.reggy93.ccrsa.service.CarListService;
import org.reggy93.ccrsa.service.entity.car.Car;
import org.reggy93.ccrsa.service.exception.ServiceOperationException;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * Unit test for {@link CarInfoDisplayFacadeImpl}.
 *
 * @author Reggy93 <marcin.z.wrobel@gmail.com>
 * created on 22 Jul 2020
 */
@ExtendWith(MockitoExtension.class)
class CarInfoDisplayFacadeImplUnitTest {

    @InjectMocks
    private CarInfoDisplayFacadeImpl testedFacade;

    @Mock
    CarListService carListService;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private Car car1;

    @Mock
    private Car car2;

    @Mock
    private CarListDisplayDTO carListDisplayDTO1;

    @Mock
    private CarListDisplayDTO carListDisplayDTO2;

    @BeforeEach
    void setUp() {
        testedFacade = new CarInfoDisplayFacadeImpl(carListService, modelMapper);
    }

    @Test
    void getAllCarsTest() throws ServiceOperationException, FacadeOperationException {

        final List<Car> retrievedCarList = List.of(car1, car2);

        when(carListService.retrieveAllCars()).thenReturn(retrievedCarList);
        when(modelMapper.map(eq(retrievedCarList), any(new TypeToken<Set<CarListDisplayDTO>>() {
        }.getType().getClass()))).thenReturn(Set.of(carListDisplayDTO1, carListDisplayDTO2));

        final Set<CarListDisplayDTO> resultSet = testedFacade.getAllCars();

        assertFalse(CollectionUtils.isEmpty(resultSet));
        assertThat(resultSet, is(hasSize(2)));
        assertThat(resultSet, hasItems(carListDisplayDTO1, carListDisplayDTO2));
        verify(modelMapper, times(1)).map(anyList(), any(new TypeToken<Set<CarListDisplayDTO>>() {
        }.getType().getClass()));
    }

    @Test
    void getAllCarsWhenNoCarsRetrievedFromServiceTest() throws ServiceOperationException, FacadeOperationException {

        when(carListService.retrieveAllCars()).thenReturn(Collections.emptyList());

        final Set<CarListDisplayDTO> resultSet = testedFacade.getAllCars();

        assertTrue(CollectionUtils.isEmpty(resultSet));
        verify(modelMapper, never()).map(anyList(), any());
    }

    @Test
    void getAllCarsWhenServiceThrowsAnExceptionTest() throws ServiceOperationException {

        when(carListService.retrieveAllCars()).thenThrow(new ServiceOperationException("Service layer exception"));

        assertThrows(FacadeOperationException.class, () -> testedFacade.getAllCars(),
                () -> "FacadeOperationException should be thrown as wrapper for ServiceOperationException");
    }
}