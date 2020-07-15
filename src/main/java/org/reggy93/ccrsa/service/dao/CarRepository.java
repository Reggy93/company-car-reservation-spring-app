package org.reggy93.ccrsa.service.dao;

import org.reggy93.ccrsa.service.entity.car.Car;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static org.reggy93.ccrsa.service.ServiceConstants.Car.CAR_LIST_ENTITY_GRAPH;

/**
 * Repository for retrieving and saving {@link Car} entities.
 *
 * @author Reggy93 <marcin.z.wrobel@gmail.com>
 * created on 13 Jul 2020
 */
@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    @Override
    @EntityGraph(value = CAR_LIST_ENTITY_GRAPH, type = EntityGraph.EntityGraphType.LOAD)
    List<Car> findAll();

    @Override
    @EntityGraph(value = CAR_LIST_ENTITY_GRAPH, type = EntityGraph.EntityGraphType.LOAD)
    Optional<Car> findById(Long aLong);
}
