package org.reggy93.ccrsa.facade.dto.car;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.hateoas.RepresentationModel;

/**
 * DTO class for {@code Localization} entity.
 *
 * @author Reggy93 <marcin.z.wrobel@gmail.com>
 * created on 16 Jul 2020
 */
public class LocalizationDTO extends RepresentationModel<LocalizationDTO> {

    private Long id;

    private String city;

    private CountryDTO country;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public CountryDTO getCountry() {
        return country;
    }

    public void setCountry(CountryDTO country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof LocalizationDTO)) return false;

        LocalizationDTO that = (LocalizationDTO) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(city, that.city)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(city)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("city", city)
                .toString();
    }
}
