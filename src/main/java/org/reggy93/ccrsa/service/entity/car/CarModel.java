package org.reggy93.ccrsa.service.entity.car;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

import static org.reggy93.ccrsa.service.ServiceConstants.Car.CAR_MODEL_LIST_MAKE;

/**
 * Entity class for {@code CarModel} representation.
 *
 * @author Reggy93 <marcin.z.wrobel@gmail.com>
 * created on 08 Jul 2020
 */
@Entity
@Table(name = "cars_models")
@NamedEntityGraph(name = CAR_MODEL_LIST_MAKE,
        attributeNodes = @NamedAttributeNode(value = "make"))
public class CarModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "int(11)")
    private Long id;

    @NotBlank
    @Size(min = 2)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "make_id")
    @NotEmpty
    private Make make;

    @OneToMany(mappedBy = "carModel", cascade = CascadeType.ALL)
    private Set<Car> carSet;

    public CarModel() {
    }

    public CarModel(@NotBlank @Size(min = 2) String name, @NotEmpty Make make) {
        this.name = name;
        this.make = make;
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

    public Make getMake() {
        return make;
    }

    public void setMake(Make make) {
        this.make = make;
    }

    public Set<Car> getCarSet() {
        return carSet;
    }

    public void setCarSet(Set<Car> carSet) {
        this.carSet = carSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof CarModel)) return false;

        CarModel carModel = (CarModel) o;

        return new EqualsBuilder()
                .append(id, carModel.id)
                .append(name, carModel.name)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(name)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("name", name)
                .toString();
    }
}
