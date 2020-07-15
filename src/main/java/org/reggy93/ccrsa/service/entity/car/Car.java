package org.reggy93.ccrsa.service.entity.car;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.io.Serializable;

import static org.reggy93.ccrsa.service.ServiceConstants.Car.CAR_LIST_ENTITY_GRAPH;

/**
 * Entity class for {@code Car} representation.
 *
 * @author Reggy93 <marcin.z.wrobel@gmail.com>
 * created on 08 Jul 2020
 */
@Entity
@Table(name = "cars")
@NamedEntityGraph(name = CAR_LIST_ENTITY_GRAPH,
        attributeNodes = {
                @NamedAttributeNode(value = "carModel", subgraph = "CarList.Car.CarModel.make"),
                @NamedAttributeNode(value = "localization" , subgraph = "CarList.Car.Localization.country")
        },
        subgraphs = {
                @NamedSubgraph(name = "CarList.Car.CarModel.make",
                        attributeNodes = {@NamedAttributeNode("make")}),
                @NamedSubgraph(name = "CarList.Car.Localization.country",
                        attributeNodes = @NamedAttributeNode("country"))
        })
public class Car implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "int(11)")
    private Long id;

    @Column(name = "image")
    private String pathToTheImage;

    @Column(name = "currently_available")
    private boolean currentlyAvailable;

    @Embedded
    private CarDetails carDetails;

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "car_model_id")
    private CarModel carModel;

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "localization_id")
    private Localization localization;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPathToTheImage() {
        return pathToTheImage;
    }

    public void setPathToTheImage(String pathToTheImage) {
        this.pathToTheImage = pathToTheImage;
    }

    public boolean isCurrentlyAvailable() {
        return currentlyAvailable;
    }

    public void setCurrentlyAvailable(boolean currentlyAvailable) {
        this.currentlyAvailable = currentlyAvailable;
    }

    public CarDetails getCarDetails() {
        return carDetails;
    }

    public void setCarDetails(CarDetails carDetails) {
        this.carDetails = carDetails;
    }

    public CarModel getCarModel() {
        return carModel;
    }

    public void setCarModel(CarModel carModel) {
        this.carModel = carModel;
    }

    public Localization getLocalization() {
        return localization;
    }

    public void setLocalization(Localization localization) {
        this.localization = localization;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Car)) return false;

        Car car = (Car) o;

        return new EqualsBuilder()
                .append(currentlyAvailable, car.currentlyAvailable)
                .append(id, car.id)
                .append(pathToTheImage, car.pathToTheImage)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(pathToTheImage)
                .append(currentlyAvailable)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("pathToTheImage", pathToTheImage)
                .append("currentlyAvailable", currentlyAvailable)
                .toString();
    }
}
