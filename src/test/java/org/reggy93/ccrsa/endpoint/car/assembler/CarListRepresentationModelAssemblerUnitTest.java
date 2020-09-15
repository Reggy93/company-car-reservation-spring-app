package org.reggy93.ccrsa.endpoint.car.assembler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reggy93.ccrsa.facade.dto.api.car.CarListDisplayDTO;
import org.reggy93.ccrsa.facade.dto.car.CarModelDTO;
import org.reggy93.ccrsa.facade.dto.car.LocalizationDTO;
import org.reggy93.ccrsa.facade.dto.car.MakeDTO;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.reggy93.ccrsa.endpoint.EndpointConstants.ControllerPathConstants.Car.CARS;
import static org.reggy93.ccrsa.endpoint.EndpointConstants.ControllerPathConstants.Car.CARS_MODELS;
import static org.reggy93.ccrsa.endpoint.EndpointConstants.ControllerPathConstants.Car.MAKES;
import static org.reggy93.ccrsa.endpoint.EndpointConstants.LinksConstants.*;

/**
 * Unit test for {@link CarListRepresentationModelAssembler}.
 *
 * @author Reggy93 <marcin.z.wrobel@gmail.com>
 * created on 13 Aug 2020
 */
@ExtendWith(MockitoExtension.class)
class CarListRepresentationModelAssemblerUnitTest {

    private static final String NO_LINK = "no_link";

    @InjectMocks
    private CarListRepresentationModelAssembler testedAssembler;

    @Mock
    private RepresentationModelAssembler<LocalizationDTO, LocalizationDTO> localizationDTORepresentationModelAssembler;

    @Mock
    private CarListDisplayDTO carListDisplayDTO1;

    @Mock
    private CarListDisplayDTO carListDisplayDTO2;

    @Spy
    private CarModelDTO carModelDTO1;

    @Spy
    private CarModelDTO carModelDTO2;

    @Spy
    private MakeDTO makeDTO1;

    @Spy
    private MakeDTO makeDTO2;

    @BeforeEach
    void setUp() {
        testedAssembler = new CarListRepresentationModelAssembler(localizationDTORepresentationModelAssembler);

        when(carListDisplayDTO1.getId()).thenReturn(1L);
        when(carListDisplayDTO1.getCarModel()).thenReturn(carModelDTO1);

        when(carModelDTO1.getId()).thenReturn(1L);
        when(carModelDTO1.getMake()).thenReturn(makeDTO1);

        when(makeDTO1.getId()).thenReturn(1L);

        when(carListDisplayDTO2.getId()).thenReturn(2L);
        when(carListDisplayDTO2.getCarModel()).thenReturn(carModelDTO2);

        when(carModelDTO2.getId()).thenReturn(2L);
        when(carModelDTO2.getMake()).thenReturn(makeDTO2);

        when(makeDTO2.getId()).thenReturn(2L);
    }

    @Test
    void toModelTest() {

        EntityModel<CarListDisplayDTO> resultEntityModel = testedAssembler.toModel(carListDisplayDTO1);

        entityModelAssertions(resultEntityModel, carListDisplayDTO1, CARS + "/1");

        CarModelDTO carModelDTO = Objects.requireNonNull(resultEntityModel.getContent()).getCarModel();
        carModelAssertions(carModelDTO, carModelDTO1, CARS_MODELS +"/1");

        MakeDTO makeDTO = carModelDTO.getMake();
        makeAssertions(makeDTO, makeDTO1, MAKES + "/1");

        resultEntityModel = testedAssembler.toModel(carListDisplayDTO2);

        entityModelAssertions(resultEntityModel, carListDisplayDTO2, CARS + "/2");

        carModelDTO = Objects.requireNonNull(resultEntityModel.getContent()).getCarModel();
        carModelAssertions(carModelDTO, carModelDTO2, CARS_MODELS + "/2");

        makeDTO = carModelDTO2.getMake();
        makeAssertions(makeDTO, makeDTO2, MAKES + "/2");

    }

    @Test
    void toCollectionModelTest() {

        final CollectionModel<EntityModel<CarListDisplayDTO>> resultCollectionModel =
                testedAssembler.toCollectionModel(Set.of(carListDisplayDTO1, carListDisplayDTO2));

        assertThat(resultCollectionModel, is(notNullValue()));
        assertThat(resultCollectionModel.getLink(SELF_RELATION).orElse(Link.of(NO_LINK)).getHref(), is(endsWith(CARS)));

        final Collection<EntityModel<CarListDisplayDTO>> resultCollection = resultCollectionModel.getContent();
        assertThat(resultCollection, is(not(empty())));

        EntityModel<CarListDisplayDTO> entityModel = resultCollection.stream()
                .filter(carListDisplayDTOEntityModel -> Objects.equals(carListDisplayDTOEntityModel.getContent(), carListDisplayDTO1))
                .findAny().orElse(null);

        assertThat(entityModel, is(notNullValue()));
        assertThat(entityModel.getLink(SELF_RELATION).orElse(Link.of(NO_LINK)).getHref(), is(endsWith("1")));
        assertThat(entityModel.getLink(ALL_CARS_RELATION).orElse(Link.of(NO_LINK)).getHref(), is(endsWith(CARS)));

        entityModel = resultCollection.stream()
                .filter(carListDisplayDTOEntityModel -> Objects.equals(carListDisplayDTOEntityModel.getContent(), carListDisplayDTO2))
                .findAny().orElse(null);

        assertThat(entityModel, is(notNullValue()));
        assertThat(entityModel.getLink(SELF_RELATION).orElse(Link.of(NO_LINK)).getHref(), is(endsWith("2")));
        assertThat(entityModel.getLink(ALL_CARS_RELATION).orElse(Link.of(NO_LINK)).getHref(), is(endsWith(CARS)));

    }

    private void carModelAssertions(final CarModelDTO carModelDTO, final CarModelDTO compareCarModelDTO,
                                    final String idSuffix) {

        assertThat(carModelDTO, is(compareCarModelDTO));
        assertThat(carModelDTO.getLink(SELF_RELATION).orElse(Link.of(NO_LINK)).getHref(),
                is(endsWith(idSuffix)));
        assertThat(carModelDTO.getLink(ALL_CARS_MODELS_RELATION).orElse(Link.of(NO_LINK)).getHref(), is(endsWith(CARS_MODELS)));
    }

    private void entityModelAssertions(final EntityModel<CarListDisplayDTO> resultEntityModel,
                                       final CarListDisplayDTO carListDisplayDTO, final String idSuffix) {

        assertThat(resultEntityModel, is(notNullValue()));
        assertThat(resultEntityModel.getContent(), is(carListDisplayDTO));
        assertThat(resultEntityModel.getLink(SELF_RELATION).orElse(Link.of(NO_LINK)).getHref(), is(endsWith(idSuffix)));
        assertThat(resultEntityModel.getLink(ALL_CARS_RELATION).orElse(Link.of(NO_LINK)).getHref(), is(endsWith(CARS)));
    }

    private void makeAssertions(final MakeDTO makeDTO, final MakeDTO compareMakeDTO, final String idSuffix) {

        assertThat(makeDTO, is(compareMakeDTO));
        assertThat(makeDTO.getLink(SELF_RELATION).orElse(Link.of(NO_LINK)).getHref(), is(endsWith(idSuffix)));
        assertThat(makeDTO.getLink(ALL_MAKES_RELATION).orElse(Link.of(NO_LINK)).getHref(), is(endsWith(MAKES)));
    }

}