package org.reggy93.ccrsa.endpoint.car.assembler;

import org.reggy93.ccrsa.endpoint.localization.CountryController;
import org.reggy93.ccrsa.facade.dto.car.CountryDTO;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.Set;

import static org.reggy93.ccrsa.endpoint.EndpointConstants.LinksConstants.ALL_COUNTRIES_RELATION;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Assembles {@link CountryDTO} with appropriate links relations.
 *
 * @author Reggy93 <marcin.z.wrobel@gmail.com>
 * created on 04 Sep 2020
 */
@Component
public class CountryListRepresentationModelAssembler implements RepresentationModelAssembler<CountryDTO, CountryDTO> {

    @Override
    public CountryDTO toModel(CountryDTO entity) {

        entity.add(linkTo(methodOn(CountryController.class).getCountryById(entity.getId())).withSelfRel());
        entity.add(linkTo(methodOn(CountryController.class).getAllCountries()).withRel(ALL_COUNTRIES_RELATION));
        return entity;
    }

    @Override
    public CollectionModel<CountryDTO> toCollectionModel(Iterable<? extends CountryDTO> entities) {

        final Set<CountryDTO> countryDTOSet = new LinkedHashSet<>();
        entities.forEach(countryDTO -> countryDTOSet.add(toModel(countryDTO)));
        return CollectionModel.of(countryDTOSet, linkTo(methodOn(CountryController.class).getAllCountries()).withSelfRel());
    }
}
