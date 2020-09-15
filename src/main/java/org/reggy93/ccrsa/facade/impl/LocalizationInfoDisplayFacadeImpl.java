package org.reggy93.ccrsa.facade.impl;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.reggy93.ccrsa.facade.LocalizationInfoDisplayFacade;
import org.reggy93.ccrsa.facade.dto.car.LocalizationDTO;
import org.reggy93.ccrsa.facade.exception.FacadeOperationException;
import org.reggy93.ccrsa.service.LocalizationService;
import org.reggy93.ccrsa.service.entity.car.Localization;
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
 * Default implementation of {@link LocalizationInfoDisplayFacade}.
 *
 * @author Reggy93 <marcin.z.wrobel@gmail.com>
 * created on 15 Sep 2020
 */
@Component
public class LocalizationInfoDisplayFacadeImpl implements LocalizationInfoDisplayFacade {

    @Autowired
    private final LocalizationService localizationService;

    @Autowired
    private final ModelMapper modelMapper;

    public LocalizationInfoDisplayFacadeImpl(LocalizationService localizationService, ModelMapper modelMapper) {
        this.localizationService = localizationService;
        this.modelMapper = modelMapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<LocalizationDTO> getAllLocalizations() throws FacadeOperationException {

        final List<Localization> localizationList;

        try {
            localizationList = localizationService.retrieveAllLocalizations();
        } catch (ServiceOperationException e) {
            throw new FacadeOperationException("Couldn't retrieve the list of all localizations because of service " +
                    "layer exception:", e);
        }

        if (CollectionUtils.isEmpty(localizationList)) {
            return Collections.emptySet();
        }

        return modelMapper.map(localizationList, new TypeToken<Set<LocalizationDTO>>() {
        }.getType());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<LocalizationDTO> getLocalizationById(@NonNull final Long id) throws FacadeOperationException {

        Optional<Localization> localizationOptional;

        try {
            localizationOptional = localizationService.retrieveLocalizationById(id);
        } catch (ServiceOperationException e) {
            throw new FacadeOperationException("Couldn't retrieve localization with id: [" + id + "] because of " +
                    "service layer exception:", e);
        }

        return localizationOptional.map(localization -> modelMapper.map(localization, new TypeToken<LocalizationDTO>() {
        }.getType()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<LocalizationDTO> getLocalizationByCityName(@NonNull final String cityName) throws FacadeOperationException {

        Optional<Localization> localizationOptional;

        try {
            localizationOptional = localizationService.retrieveLocalizationByCityName(cityName);
        } catch (ServiceOperationException e) {
            throw new FacadeOperationException("Couldn't retrieve localization with city name: [" + cityName + "] " +
                    "because of service layer exception:", e);
        }

        return localizationOptional.map(localization -> modelMapper.map(localization, new TypeToken<LocalizationDTO>() {
        }.getType()));
    }
}
