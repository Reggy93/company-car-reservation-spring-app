package org.reggy93.ccrsa.endpoint.car;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reggy93.ccrsa.endpoint.car.controller.CarListController;
import org.reggy93.ccrsa.facade.CarInfoDisplayFacade;
import org.reggy93.ccrsa.facade.dto.api.car.CarListDisplayDTO;
import org.reggy93.ccrsa.facade.exception.FacadeOperationException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.Mockito.*;


/**
 * Unit test for {@link CarListController}.
 *
 * @author Reggy93 <marcin.z.wrobel@gmail.com>
 * created on 23 Jul 2020
 */
@ExtendWith(MockitoExtension.class)
class CarListControllerUnitTest {

    @InjectMocks
    private CarListController testedController;

    @Mock
    private CarInfoDisplayFacade carInfoDisplayFacade;

    @Mock
    private RepresentationModelAssembler<CarListDisplayDTO, EntityModel<CarListDisplayDTO>> carListRepresentationModelAssembler;

    @BeforeEach
    void setUp() {
        testedController = new CarListController(carInfoDisplayFacade, carListRepresentationModelAssembler);
    }

    @Mock
    private CarListDisplayDTO carListDisplayDTO1;

    @Mock
    private CarListDisplayDTO carListDisplayDTO2;

    @Mock
    private CollectionModel<EntityModel<CarListDisplayDTO>> entityModelCollectionModel;


    @Test
    void getAllCarsTest() throws FacadeOperationException {

        when(carInfoDisplayFacade.getAllCars()).thenReturn(Set.of(carListDisplayDTO1, carListDisplayDTO2));
        when(carListRepresentationModelAssembler.toCollectionModel(Set.of(carListDisplayDTO1, carListDisplayDTO2)))
                .thenReturn(entityModelCollectionModel);

        final ResponseEntity<CollectionModel<EntityModel<CarListDisplayDTO>>> result = testedController.getAllCars();

        verify(carListRepresentationModelAssembler, times(1)).toCollectionModel(anySet());
        assertThat(result.getBody(), is(notNullValue()));
        assertThat(result.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    void getAllCarsWhenNoCarsRetrievedTest() throws FacadeOperationException {

        when(carInfoDisplayFacade.getAllCars()).thenReturn(Collections.emptySet());

        final ResponseEntity<CollectionModel<EntityModel<CarListDisplayDTO>>> result = testedController.getAllCars();

        assertThat(result.getStatusCode(), is(HttpStatus.NO_CONTENT));
        assertThat(result.getBody(), is(nullValue()));
        verify(carListRepresentationModelAssembler, never()).toCollectionModel(anySet());
    }

    @Test
    void getAllCarsWhenExceptionWasThrownInFacadeTest() throws FacadeOperationException {

        when(carInfoDisplayFacade.getAllCars()).thenThrow(new FacadeOperationException("Test facade operation exception"));

        final ResponseEntity<CollectionModel<EntityModel<CarListDisplayDTO>>> result = testedController.getAllCars();

        assertThat(result.getStatusCode(), is(HttpStatus.INTERNAL_SERVER_ERROR));
        assertThat(result.getBody(), is(nullValue()));

    }
}