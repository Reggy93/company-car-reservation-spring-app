package org.reggy93.ccrsa.entity.car;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.reggy93.ccrsa.entity.reservation.Reservation;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Entity class for {@code Car} representation.
 *
 * @author Reggy93 <marcin.z.wrobel@gmail.com>
 * created on 08 Jul 2020
 */
@Entity
@Table(name = "car")
public class Car implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private String id;

    @Column(name = "image")
    private String pathToTheImage;

    @Column(name = "currently_available")
    private boolean currentlyAvailable;

    @Embedded
    private CarDetails carDetails;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL)
    private Set<Reservation> reservationSet;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public Set<Reservation> getReservationSet() {
        return reservationSet;
    }

    public void setReservationSet(Set<Reservation> reservationSet) {
        this.reservationSet = reservationSet;
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
                .append(carDetails, car.carDetails)
                .append(reservationSet, car.reservationSet)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(pathToTheImage)
                .append(currentlyAvailable)
                .append(carDetails)
                .append(reservationSet)
                .toHashCode();
    }
}
