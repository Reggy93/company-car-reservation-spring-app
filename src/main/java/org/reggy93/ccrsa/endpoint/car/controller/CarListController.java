package org.reggy93.ccrsa.endpoint.car.controller;

import org.reggy93.ccrsa.facade.CarInfoDisplayFacade;
import org.reggy93.ccrsa.facade.dto.api.car.CarListDisplayDTO;
import org.reggy93.ccrsa.facade.exception.FacadeOperationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static org.reggy93.ccrsa.endpoint.EndpointConstants.ControllerPathConstants.Car.CARS;


/**
 * Controller for serving car list requests.
 *
 * @author Reggy93 <marcin.z.wrobel@gmail.com>
 * created on 13 Jul 2020
 */
@RestController
@RequestMapping(CARS)
public class CarListController {

    private final CarInfoDisplayFacade carInfoDisplayFacade;

    private final RepresentationModelAssembler<CarListDisplayDTO, EntityModel<CarListDisplayDTO>> carListRepresentationModelAssembler;

    @Autowired
    public CarListController(CarInfoDisplayFacade carInfoDisplayFacade, RepresentationModelAssembler<CarListDisplayDTO,
            EntityModel<CarListDisplayDTO>> carListRepresentationModelAssembler) {
        this.carInfoDisplayFacade = carInfoDisplayFacade;
        this.carListRepresentationModelAssembler = carListRepresentationModelAssembler;
    }

    @GetMapping()
    public ResponseEntity<CollectionModel<EntityModel<CarListDisplayDTO>>> getAllCars() {

        final Set<CarListDisplayDTO> carListDisplayDTOSet;

        try {
            carListDisplayDTOSet =
                    carInfoDisplayFacade.getAllCars().stream().sorted(Comparator.comparing(CarListDisplayDTO::getId))
                            .collect(Collectors.toCollection(LinkedHashSet::new));
        } catch (FacadeOperationException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

        if (CollectionUtils.isEmpty(carListDisplayDTOSet)) {
//            TODO: Log this
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(carListRepresentationModelAssembler.toCollectionModel(carListDisplayDTOSet));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<CarListDisplayDTO>> getCarById(@PathVariable final Long id) {

        return ResponseEntity.ok(carListRepresentationModelAssembler.toModel(carInfoDisplayFacade.getCarById(id)));
    }
}
