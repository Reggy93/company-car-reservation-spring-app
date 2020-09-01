package org.reggy93.ccrsa.service.impl;

import org.reggy93.ccrsa.service.CountryService;
import org.reggy93.ccrsa.service.dao.CountryRepository;
import org.reggy93.ccrsa.service.entity.car.Country;
import org.reggy93.ccrsa.service.exception.ServiceOperationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Default implementation of {@link CountryService}.
 *
 * @author Reggy93 <marcin.z.wrobel@gmail.com>
 * created on 01 Sep 2020
 */
@Service
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;

    @Autowired
    public CountryServiceImpl(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<Country> retrieveAllCountries() throws ServiceOperationException {

        try {
            return countryRepository.findAll();
        } catch (RuntimeException e) {
            throw new ServiceOperationException("Unable to retrieve list of all countries because of " +
                    "countryRepository error:", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Country> retrieveCountryById(@NonNull final Long id) throws ServiceOperationException {
        try {
            return countryRepository.findById(id);
        } catch (RuntimeException e) {
            throw new ServiceOperationException("Unable to retrieve country with id: [" + id + "] because of " +
                    "countryRepository error:", e);
        }
    }
}
