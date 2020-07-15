package org.reggy93.ccrsa.service.dao;

import org.reggy93.ccrsa.service.entity.car.CarModel;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import static org.reggy93.ccrsa.service.ServiceConstants.Car.CAR_MODEL_LIST_MAKE;

/**
 *  Repository for retrieving and saving {@link CarModel} entities.
 *
 * @author Reggy93 <marcin.z.wrobel@gmail.com>
 * created on 28 Jul 2020
 */
@Repository
public interface CarModelRepository extends JpaRepository<CarModel, Long> {

    @Transactional(readOnly = true)
    @EntityGraph(value = CAR_MODEL_LIST_MAKE, type = EntityGraph.EntityGraphType.LOAD)
    CarModel findByName(String name);
}
