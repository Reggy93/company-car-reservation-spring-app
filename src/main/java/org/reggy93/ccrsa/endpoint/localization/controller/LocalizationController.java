package org.reggy93.ccrsa.endpoint.localization.controller;

import org.reggy93.ccrsa.facade.dto.car.LocalizationDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

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

    @GetMapping
    public ResponseEntity<Set<LocalizationDTO>> getAllLocalizations() {
//        TODO: to implement!
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocalizationDTO> getLocalizationById(@PathVariable final Long id) {

//        TODO: to implement!
        return ResponseEntity.badRequest().build();
    }
}
