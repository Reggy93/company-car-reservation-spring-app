package org.reggy93.ccrsa.service.dao;

import org.reggy93.ccrsa.service.entity.car.Make;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for retrieving and saving {@link Make} entities.
 *
 * @author Reggy93 <marcin.z.wrobel@gmail.com>
 * created on 28 Jul 2020
 */
@Repository
public interface MakeRepository extends JpaRepository<Make, Long> {

    Make findByName(String name);
}
