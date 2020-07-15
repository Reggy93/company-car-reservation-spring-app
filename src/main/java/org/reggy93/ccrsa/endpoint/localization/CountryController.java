package org.reggy93.ccrsa.endpoint.localization;

import org.reggy93.ccrsa.facade.dto.car.CountryDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

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

    @GetMapping
    public ResponseEntity<Set<CountryDTO>> getAllCountries() {
//        TODO: to implement!
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CountryDTO> getCountryById(@PathVariable final Long id) {
//        TODO: to implement!
        return ResponseEntity.badRequest().build();
    }
}