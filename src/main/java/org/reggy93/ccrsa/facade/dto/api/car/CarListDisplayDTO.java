package org.reggy93.ccrsa.facade.dto.api.car;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.reggy93.ccrsa.facade.dto.car.CarModelDTO;
import org.reggy93.ccrsa.facade.dto.car.LocalizationDTO;

/**
 * @author Reggy93 <marcin.z.wrobel@gmail.com>
 * created on 16 Jul 2020
 */
@JsonRootName(value = "car")
public class CarListDisplayDTO {

    private Long id;

    private boolean currentlyAvailable;

    @JsonProperty(value = "carModel")
    private CarModelDTO carModel;

    @JsonProperty(value = "localization")
    private LocalizationDTO localization;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isCurrentlyAvailable() {
        return currentlyAvailable;
    }

    public void setCurrentlyAvailable(boolean currentlyAvailable) {
        this.currentlyAvailable = currentlyAvailable;
    }

    public CarModelDTO getCarModel() {
        return carModel;
    }

    public void setCarModel(CarModelDTO carModel) {
        this.carModel = carModel;
    }

    public LocalizationDTO getLocalization() {
        return localization;
    }

    public void setLocalization(LocalizationDTO localization) {
        this.localization = localization;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof CarListDisplayDTO)) return false;

        CarListDisplayDTO that = (CarListDisplayDTO) o;

        return new EqualsBuilder()
                .append(currentlyAvailable, that.currentlyAvailable)
                .append(id, that.id)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(currentlyAvailable)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("currentlyAvailable", currentlyAvailable)
                .toString();
    }
}
