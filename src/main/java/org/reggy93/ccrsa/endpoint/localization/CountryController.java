package org.reggy93.ccrsa.endpoint.localization;

import org.reggy93.ccrsa.facade.CountryInfoDisplayFacade;
import org.reggy93.ccrsa.facade.dto.car.CountryDTO;
import org.reggy93.ccrsa.facade.exception.FacadeOperationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
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
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.reggy93.ccrsa.endpoint.EndpointConstants.ControllerPathConstants.Localization.COUNTRIES;

/**
 * Controller for serving country requests.
 *
 * @author Reggy93 <marcin.z.wrobel@gmail.com>
 * created on 13 Aug 2020
 */
@RestController
@RequestMapping(COUNTRIES)
public class CountryController {

    private final CountryInfoDisplayFacade countryInfoDisplayFacade;

    private final RepresentationModelAssembler<CountryDTO, CountryDTO> countryDTORepresentationModelAssembler;

    @Autowired
    public CountryController(CountryInfoDisplayFacade countryInfoDisplayFacade,
                             RepresentationModelAssembler<CountryDTO, CountryDTO> countryDTORepresentationModelAssembler) {
        this.countryInfoDisplayFacade = countryInfoDisplayFacade;
        this.countryDTORepresentationModelAssembler = countryDTORepresentationModelAssembler;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<CountryDTO>> getAllCountries() {

        Set<CountryDTO> countryDTOSet = null;

        try {
            countryDTOSet =
                    countryInfoDisplayFacade.getAllCountries().stream().sorted(Comparator.comparing(CountryDTO::getId))
                            .collect(Collectors.toCollection(LinkedHashSet::new));
        } catch (FacadeOperationException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

        if (CollectionUtils.isEmpty(countryDTOSet)) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(countryDTORepresentationModelAssembler.toCollectionModel(countryDTOSet));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CountryDTO> getCountryById(@PathVariable final Long id) {

        Optional<CountryDTO> countryDTOOptional;
        try {
            countryDTOOptional = countryInfoDisplayFacade.getCountryById(id);
        } catch (FacadeOperationException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

        return countryDTOOptional.map(countryDTO -> ResponseEntity.ok(countryDTORepresentationModelAssembler.toModel(countryDTO)))
                .orElse(ResponseEntity.noContent().build());
    }
}
