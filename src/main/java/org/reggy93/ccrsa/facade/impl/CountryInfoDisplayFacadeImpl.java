package org.reggy93.ccrsa.facade.impl;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.reggy93.ccrsa.facade.CountryInfoDisplayFacade;
import org.reggy93.ccrsa.facade.dto.car.CountryDTO;
import org.reggy93.ccrsa.facade.exception.FacadeOperationException;
import org.reggy93.ccrsa.service.CountryService;
import org.reggy93.ccrsa.service.entity.car.Country;
import org.reggy93.ccrsa.service.exception.ServiceOperationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Default implementation of {@link CountryInfoDisplayFacade}.
 *
 * @author Reggy93 <marcin.z.wrobel@gmail.com>
 * created on 02 Sep 2020
 */
@Component
public class CountryInfoDisplayFacadeImpl implements CountryInfoDisplayFacade {

    private final CountryService countryService;

    private final ModelMapper modelMapper;

    @Autowired
    public CountryInfoDisplayFacadeImpl(CountryService countryService, ModelMapper modelMapper) {
        this.countryService = countryService;
        this.modelMapper = modelMapper;
    }

    @Override
    public Set<CountryDTO> getAllCountries() throws FacadeOperationException {

        final List<Country> countryList;
        try {
            countryList = countryService.retrieveAllCountries();
        } catch (ServiceOperationException e) {
            throw new FacadeOperationException("Couldn't retrieve the list of all countries because of service layer " +
                    "exception:", e);
        }

        if (CollectionUtils.isEmpty(countryList)) {
            return Collections.emptySet();
        }

        return modelMapper.map(countryList, new TypeToken<Set<CountryDTO>>() {
        }.getType());
    }

    @Override
    public Optional<CountryDTO> getCountryById(@NonNull final Long id) throws FacadeOperationException {

        final Optional<Country> retrievedCountryOptional;

        try {
            retrievedCountryOptional = countryService.retrieveCountryById(id);
        } catch (ServiceOperationException e) {
            throw new FacadeOperationException("Couldn't retrieve country with id [" + id + "] because of service " +
                    "layer exception:", e);
        }

        return retrievedCountryOptional.map(country -> modelMapper.map(country,
                new TypeToken<CountryDTO>() {}.getType()));
    }
}
