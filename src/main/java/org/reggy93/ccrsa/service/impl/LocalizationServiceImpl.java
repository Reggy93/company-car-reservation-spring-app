package org.reggy93.ccrsa.service.impl;

import org.reggy93.ccrsa.service.LocalizationService;
import org.reggy93.ccrsa.service.dao.LocalizationRepository;
import org.reggy93.ccrsa.service.entity.car.Localization;
import org.reggy93.ccrsa.service.exception.ServiceOperationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Default implementation of {@link LocalizationService}.
 *
 * @author Reggy93 <marcin.z.wrobel@gmail.com>
 * created on 15 Sep 2020
 */
@Service
public class LocalizationServiceImpl implements LocalizationService {

    private static final String LOCALIZATION_REPOSITORY_ERROR = "localizationRepository error:";

    @Autowired
    private final LocalizationRepository localizationRepository;

    public LocalizationServiceImpl(LocalizationRepository localizationRepository) {
        this.localizationRepository = localizationRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<Localization> retrieveAllLocalizations() throws ServiceOperationException {

        List<Localization> localizationList = null;

        try {
            localizationList = localizationRepository.findAll();
        } catch (RuntimeException e) {
            throw new ServiceOperationException("Unable to retrieve list of all localizations because of " +
                    LOCALIZATION_REPOSITORY_ERROR, e);
        }

        return localizationList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Localization> retrieveLocalizationById(@NonNull final Long id) throws ServiceOperationException {

        Optional<Localization> localizationOptional;
        try {
            localizationOptional = localizationRepository.findById(id);
        } catch (RuntimeException e) {
            throw new ServiceOperationException("Unable to retrieve localization with id [" + id + "] because of " +
                    LOCALIZATION_REPOSITORY_ERROR, e);
        }

        return localizationOptional;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Localization> retrieveLocalizationByCityName(@NonNull final String cityName) throws ServiceOperationException {
        Optional<Localization> localizationOptional;
        try {
            localizationOptional = localizationRepository.findByCity(cityName);
        } catch (RuntimeException e) {
            throw new ServiceOperationException("Unable to retrieve localization with id [" + cityName + "] because of " +
                    LOCALIZATION_REPOSITORY_ERROR, e);
        }

        return localizationOptional;
    }
}
