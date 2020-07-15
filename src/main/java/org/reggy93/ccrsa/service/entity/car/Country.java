package org.reggy93.ccrsa.service.entity.car;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.SelectBeforeUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

/**
 * Entity class for {@code Country} representation.
 *
 * @author Reggy93 <marcin.z.wrobel@gmail.com>
 * created on 08 Jul 2020
 */
@Entity
@Table(name = "countries")
@SelectBeforeUpdate
public class Country implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "int(11)")
    private Long id;

    @Column(name = "name")
    @NotBlank
    private String name;

    @Column(name = "iso_code")
    @NotBlank
    @Size(min = 2, max = 3)
    private String isoCode;

    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL)
    private Set<Localization> localizationSet;

    public Country() {
    }

    public Country(@NotBlank String name, @NotBlank @Size(min = 2, max = 3) String isoCode) {
        this.name = name;
        this.isoCode = isoCode;
    }

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

    public Set<Localization> getLocalizationSet() {
        return localizationSet;
    }

    public void setLocalizationSet(Set<Localization> localizationSet) {
        this.localizationSet = localizationSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Country)) return false;

        Country country = (Country) o;

        return new EqualsBuilder()
                .append(id, country.id)
                .append(name, country.name)
                .append(isoCode, country.isoCode)
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
