package org.reggy93.ccrsa.service.entity.car;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.reggy93.ccrsa.service.entity.Comment;
import org.reggy93.ccrsa.service.entity.reservation.Reservation;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

/**
 * Embeddable class for {@link Car} to represent {@code CarDetails}.
 *
 * @author Reggy93 <marcin.z.wrobel@gmail.com>
 * created on 08 Jul 2020
 */
@Embeddable
public class CarDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "registration_number", unique = true)
    @NotBlank
    @Size(min = 6, max = 10)
    private String registrationNumber;

    @Column(name = "fuel_type")
    @Enumerated(EnumType.STRING)
    @NotEmpty
    private FuelType fuelType;

    @Column(name = "engine_capacity")
    private float engineCapacity;

    @Column(name = "horse_power", columnDefinition = "smallint")
    @Min(value = 30)
    private int horsePower;

    @Column(name = "milometer")
    @Min(value = 0)
    private int milometer;

    @Column(name = "number_of_doors", columnDefinition = "tinyint")
    @Min(value = 3)
    private int numberOfDoors;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @OneToMany(mappedBy = "car", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Comment> commentSet;

    @OneToMany(mappedBy = "car", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Reservation> reservationSet;

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }

    public float getEngineCapacity() {
        return engineCapacity;
    }

    public void setEngineCapacity(float engineCapacity) {
        this.engineCapacity = engineCapacity;
    }

    public int getHorsePower() {
        return horsePower;
    }

    public void setHorsePower(int horsePower) {
        this.horsePower = horsePower;
    }

    public int getMilometer() {
        return milometer;
    }

    public void setMilometer(int milometer) {
        this.milometer = milometer;
    }

    public int getNumberOfDoors() {
        return numberOfDoors;
    }

    public void setNumberOfDoors(int numberOfDoors) {
        this.numberOfDoors = numberOfDoors;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Comment> getCommentSet() {
        return commentSet;
    }

    public void setCommentSet(Set<Comment> commentSet) {
        this.commentSet = commentSet;
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

        if (!(o instanceof CarDetails)) return false;

        CarDetails that = (CarDetails) o;

        return new EqualsBuilder()
                .append(engineCapacity, that.engineCapacity)
                .append(horsePower, that.horsePower)
                .append(milometer, that.milometer)
                .append(numberOfDoors, that.numberOfDoors)
                .append(registrationNumber, that.registrationNumber)
                .append(fuelType, that.fuelType)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(registrationNumber)
                .append(fuelType)
                .append(engineCapacity)
                .append(horsePower)
                .append(milometer)
                .append(numberOfDoors)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("registrationNumber", registrationNumber)
                .append("fuelType", fuelType)
                .append("engineCapacity", engineCapacity)
                .append("horsePower", horsePower)
                .append("milometer", milometer)
                .append("numberOfDoors", numberOfDoors)
                .toString();
    }
}
