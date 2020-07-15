package org.reggy93.ccrsa.endpoint.car.assembler;

import org.reggy93.ccrsa.endpoint.car.controller.CarListController;
import org.reggy93.ccrsa.endpoint.car.controller.CarModelController;
import org.reggy93.ccrsa.endpoint.car.controller.MakeController;
import org.reggy93.ccrsa.endpoint.localization.CountryController;
import org.reggy93.ccrsa.endpoint.localization.LocalizationController;
import org.reggy93.ccrsa.facade.dto.api.car.CarListDisplayDTO;
import org.reggy93.ccrsa.facade.dto.car.CarModelDTO;
import org.reggy93.ccrsa.facade.dto.car.CountryDTO;
import org.reggy93.ccrsa.facade.dto.car.LocalizationDTO;
import org.reggy93.ccrsa.facade.dto.car.MakeDTO;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.reggy93.ccrsa.endpoint.EndpointConstants.LinksConstants.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * @author Reggy93 <marcin.z.wrobel@gmail.com>
 * created on 12 Aug 2020
 */
@Component
public class CarListRepresentationModelAssembler implements RepresentationModelAssembler<CarListDisplayDTO, EntityModel<CarListDisplayDTO>> {

    @Override
    public EntityModel<CarListDisplayDTO> toModel(CarListDisplayDTO entity) {

        final CarModelDTO carModelDTO = entity.getCarModel();
        carModelDTO.add(linkTo(methodOn(CarModelController.class).getCarModelById(carModelDTO.getId())).withSelfRel());
        carModelDTO.add(linkTo(methodOn(CarModelController.class).getAllCarsModels()).withRel(ALL_CARS_MODELS_RELATION));

        final MakeDTO makeDTO = carModelDTO.getMake();
        makeDTO.add(linkTo(methodOn(MakeController.class).getMakeById(makeDTO.getId())).withSelfRel());
        makeDTO.add(linkTo(methodOn(MakeController.class).getAllMakes()).withRel(ALL_MAKES_RELATION));

        final LocalizationDTO localizationDTO = entity.getLocalization();
        localizationDTO.add(linkTo(methodOn(LocalizationController.class).getLocalizationById(localizationDTO.getId())).withSelfRel());
        localizationDTO.add(linkTo(methodOn(LocalizationController.class).getAllLocalizations()).withRel(ALL_LOCALIZATIONS_RELATION));

        final CountryDTO countryDTO = localizationDTO.getCountry();
        countryDTO.add(linkTo(methodOn(CountryController.class).getCountryById(countryDTO.getId())).withSelfRel());
        countryDTO.add(linkTo(methodOn(CountryController.class).getAllCountries()).withRel(ALL_COUNTRIES_RELATION));

        return EntityModel.of(entity,
                linkTo(methodOn(CarListController.class).getCarById(entity.getId())).withSelfRel(),
                linkTo(methodOn(CarListController.class).getAllCars()).withRel(ALL_CARS_RELATION));
    }

    @Override
    public CollectionModel<EntityModel<CarListDisplayDTO>> toCollectionModel(Iterable<? extends CarListDisplayDTO> entities) {

        final List<EntityModel<CarListDisplayDTO>> carEntityModelList = new ArrayList<>();
        entities.forEach(carListDisplayDTO -> carEntityModelList.add(toModel(carListDisplayDTO)));
        return CollectionModel.of(carEntityModelList, linkTo(methodOn(CarListController.class).getAllCars()).withSelfRel());
    }
}
