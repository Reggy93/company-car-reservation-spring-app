package org.reggy93.ccrsa.entity.car;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.reggy93.ccrsa.entity.Comment;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;
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

    @Column(name = "horse_power")
    private int horsePower;

    private int milometer;

    @Column(name = "number_of_doors")
    private int numberOfDoors;

    private String description;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "comment_id")
    private Set<Comment> commentSet;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CarDetails)) return false;
        CarDetails that = (CarDetails) o;
        return Float.compare(that.engineCapacity, engineCapacity) == 0 &&
                horsePower == that.horsePower &&
                milometer == that.milometer &&
                numberOfDoors == that.numberOfDoors &&
                registrationNumber.equals(that.registrationNumber) &&
                fuelType == that.fuelType &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(registrationNumber, fuelType, engineCapacity, horsePower, milometer, numberOfDoors, description);
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
