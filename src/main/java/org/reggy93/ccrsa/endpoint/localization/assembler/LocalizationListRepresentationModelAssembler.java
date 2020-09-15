package org.reggy93.ccrsa.endpoint.localization.assembler;

import org.reggy93.ccrsa.endpoint.localization.controller.LocalizationController;
import org.reggy93.ccrsa.facade.dto.car.CountryDTO;
import org.reggy93.ccrsa.facade.dto.car.LocalizationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.Set;

import static org.reggy93.ccrsa.endpoint.EndpointConstants.LinksConstants.ALL_LOCALIZATIONS_RELATION;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Assembles {@link LocalizationDTO} with appropriate links relations.
 *
 * @author Reggy93 <marcin.z.wrobel@gmail.com>
 * created on 17 Sep 2020
 */
@Component
public class LocalizationListRepresentationModelAssembler implements RepresentationModelAssembler<LocalizationDTO, LocalizationDTO> {

    private final RepresentationModelAssembler<CountryDTO, CountryDTO> countryRepresentationModelAssembler;

    @Autowired
    public LocalizationListRepresentationModelAssembler(RepresentationModelAssembler<CountryDTO, CountryDTO> countryRepresentationModelAssembler) {
        this.countryRepresentationModelAssembler = countryRepresentationModelAssembler;
    }

    @Override
    public LocalizationDTO toModel(LocalizationDTO entity) {

        entity.add(linkTo(methodOn(LocalizationController.class).getLocalizationById(entity.getId())).withSelfRel());
        entity.add(linkTo(methodOn(LocalizationController.class).getAllLocalizations()).withRel(ALL_LOCALIZATIONS_RELATION));

        countryRepresentationModelAssembler.toModel(entity.getCountry());

        return entity;
    }

    @Override
    public CollectionModel<LocalizationDTO> toCollectionModel(Iterable<? extends LocalizationDTO> entities) {

        final Set<LocalizationDTO> localizationDTOSet = new LinkedHashSet<>();
        entities.forEach(localizationDTO -> localizationDTOSet.add(toModel(localizationDTO)));
        return CollectionModel.of(localizationDTOSet,
                linkTo(methodOn(LocalizationController.class).getAllLocalizations()).withSelfRel());
    }
}
