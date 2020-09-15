package org.reggy93.ccrsa.endpoint.localization.controller;

import org.reggy93.ccrsa.facade.LocalizationInfoDisplayFacade;
import org.reggy93.ccrsa.facade.dto.car.LocalizationDTO;
import org.reggy93.ccrsa.facade.exception.FacadeOperationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.reggy93.ccrsa.endpoint.EndpointConstants.ControllerPathConstants.Localization.LOCALIZATIONS;

/**
 * Controller for serving localization requests.
 *
 * @author Reggy93 <marcin.z.wrobel@gmail.com>
 * created on 13 Aug 2020
 */
@RestController
@RequestMapping(LOCALIZATIONS)
public class LocalizationController {

    private final LocalizationInfoDisplayFacade localizationInfoDisplayFacade;

    private final RepresentationModelAssembler<LocalizationDTO, LocalizationDTO> localizationDTORepresentationModelAssembler;

    @Autowired
    public LocalizationController(LocalizationInfoDisplayFacade localizationInfoDisplayFacade,
                                  RepresentationModelAssembler<LocalizationDTO, LocalizationDTO> localizationDTORepresentationModelAssembler) {
        this.localizationInfoDisplayFacade = localizationInfoDisplayFacade;
        this.localizationDTORepresentationModelAssembler = localizationDTORepresentationModelAssembler;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<LocalizationDTO>> getAllLocalizations() {

        Set<LocalizationDTO> localizationDTOSet = null;
        try {
            localizationDTOSet =
                    localizationInfoDisplayFacade.getAllLocalizations().stream()
                            .sorted(Comparator.comparing(LocalizationDTO::getId))
                            .collect(Collectors.toCollection(LinkedHashSet::new));
        } catch (FacadeOperationException e) {
//            TODO: log exception
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        if (CollectionUtils.isEmpty(localizationDTOSet)) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(localizationDTORepresentationModelAssembler.toCollectionModel(localizationDTOSet));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocalizationDTO> getLocalizationById(@PathVariable final Long id) {

        Optional<LocalizationDTO> retrievedLocalizationOptional;

        try {
            retrievedLocalizationOptional = localizationInfoDisplayFacade.getLocalizationById(id);
        } catch (FacadeOperationException e) {
//            TODO: log exception
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return retrievedLocalizationOptional.map(localizationDTO ->
                ResponseEntity.ok(localizationDTORepresentationModelAssembler.toModel(localizationDTO)))
                .orElse(ResponseEntity.noContent().build());
    }

    @GetMapping("/filter")
    public ResponseEntity<LocalizationDTO> getLocalizationByCityName(@RequestParam(value = "name") final String cityName) {

        Optional<LocalizationDTO> retrievedLocalizationOptional;

        try {
            retrievedLocalizationOptional = localizationInfoDisplayFacade.getLocalizationByCityName(cityName);
        } catch (FacadeOperationException e) {
//            TODO: log exception
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return retrievedLocalizationOptional.map(localizationDTO ->
                ResponseEntity.ok(localizationDTORepresentationModelAssembler.toModel(localizationDTO)))
                .orElse(ResponseEntity.noContent().build());
    }
}
