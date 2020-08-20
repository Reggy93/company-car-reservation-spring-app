package org.reggy93.ccrsa.service.dao;

import org.reggy93.ccrsa.service.entity.car.Localization;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import static org.reggy93.ccrsa.service.ServiceConstants.Localization.LOCALIZATION_LIST_COUNTRY;

/**
 * Repository for retrieving and saving {@link Localization} entities.
 *
 * @author Reggy93 <marcin.z.wrobel@gmail.com>
 * created on 28 Jul 2020
 */
@Repository
public interface LocalizationRepository extends JpaRepository<Localization, Long> {

    /**
     * Searches for localization with {@code city} passed as search parameter.
     *
     * @param city name of the city to search by
     * @return {@link Localization} with given {@code city}
     */
    @Transactional(readOnly = true)
    @EntityGraph(value = LOCALIZATION_LIST_COUNTRY, type = EntityGraph.EntityGraphType.LOAD)
    Localization findByCity(String city);
}
