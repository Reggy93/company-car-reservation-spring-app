package org.reggy93.ccrsa.service.entity.car;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.SelectBeforeUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

import static org.reggy93.ccrsa.service.ServiceConstants.Localization.LOCALIZATION_LIST_COUNTRY;

/**
 * Entity class for {@code Localization} representation.
 *
 * @author Reggy93 <marcin.z.wrobel@gmail.com>
 * created on 08 Jul 2020
 */
@Entity
@Table(name = "localizations")
@NamedEntityGraph(name = LOCALIZATION_LIST_COUNTRY,
        attributeNodes = {
                @NamedAttributeNode(value = "country")
        })
@SelectBeforeUpdate
public class Localization implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "int(11)")
    private Long id;

    @Column(name = "city")
    @NotBlank
    @Size(min = 2)
    private String city;

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "country_id")
    @NotEmpty
    private Country country;

    public Localization() {
    }

    public Localization(@NotBlank @Size(min = 2) String city, @NotEmpty Country country) {
        this.city = city;
        this.country = country;
    }

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

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Localization)) return false;

        Localization that = (Localization) o;

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
