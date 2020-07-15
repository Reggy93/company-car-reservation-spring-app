package org.reggy93.ccrsa.service.dao;

import org.reggy93.ccrsa.service.entity.car.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Repository for retrieving and saving {@link Country} entities.
 *
 * @author Reggy93 <marcin.z.wrobel@gmail.com>
 * created on 28 Jul 2020
 */
@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {

    @Transactional(readOnly = true)
    Country findByName(String name);
}
