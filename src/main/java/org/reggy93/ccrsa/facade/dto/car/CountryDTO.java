package org.reggy93.ccrsa.facade.dto.car;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.hateoas.RepresentationModel;

/**
 * DTO class for {@code Country} entity.
 *
 * @author Reggy93 <marcin.z.wrobel@gmail.com>
 * created on 16 Jul 2020
 */
public class CountryDTO extends RepresentationModel<CountryDTO> {

    private Long id;

    private String name;

    private String isoCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsoCode() {
        return isoCode;
    }

    public void setIsoCode(String isoCode) {
        this.isoCode = isoCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof CountryDTO)) return false;

        CountryDTO that = (CountryDTO) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(name, that.name)
                .append(isoCode, that.isoCode)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(name)
                .append(isoCode)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("name", name)
                .append("isoCode", isoCode)
                .toString();
    }
}
