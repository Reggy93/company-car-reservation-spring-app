package org.reggy93.ccrsa.service.dao;

import org.reggy93.ccrsa.service.entity.car.Localization;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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
     * Finds all {@link Localization} in the database.
     *
     * @return {@link List} of all {@link Localization} found in the database
     */
    @Override
    @EntityGraph(value = LOCALIZATION_LIST_COUNTRY, type = EntityGraph.EntityGraphType.LOAD)
    List<Localization> findAll();

    /**
     * Finds {@link Localization} by given {@code id}.
     *
     * @param aLong {@code id} of the {@link Localization}
     * @return {@link Optional} of {@link Localization} with found localization or an empty optional
     */
    @Override
    @EntityGraph(value = LOCALIZATION_LIST_COUNTRY, type = EntityGraph.EntityGraphType.LOAD)
    Optional<Localization> findById(Long aLong);

    /**
     * Searches for localization with {@code city} passed as search parameter.
     *
     * @param city name of the city to search by
     * @return {@link Localization} with given {@code city}
     */
    @EntityGraph(value = LOCALIZATION_LIST_COUNTRY, type = EntityGraph.EntityGraphType.LOAD)
    Optional<Localization> findByCity(String city);
}
