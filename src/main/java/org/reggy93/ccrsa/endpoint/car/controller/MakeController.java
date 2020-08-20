package org.reggy93.ccrsa.endpoint.car.controller;

import org.reggy93.ccrsa.facade.dto.car.MakeDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

import static org.reggy93.ccrsa.endpoint.EndpointConstants.ControllerPathConstants.Car.MAKES;

/**
 * Controller for serving makes requests.
 *
 * @author Reggy93 <marcin.z.wrobel@gmail.com>
 * created on 13 Aug 2020
 */
@RestController
@RequestMapping(MAKES)
public class MakeController {


    @GetMapping
    public ResponseEntity<Set<MakeDTO>> getAllMakes() {
//        TODO: to implement!
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MakeDTO> getMakeById(@PathVariable final Long id) {
//        TODO: to implement!
        return ResponseEntity.badRequest().build();
    }
}
