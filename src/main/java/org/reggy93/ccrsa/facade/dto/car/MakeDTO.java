package org.reggy93.ccrsa.facade.dto.car;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.hateoas.RepresentationModel;

/**
 * DTO class for {@code Make} entity.
 *
 * @author Reggy93 <marcin.z.wrobel@gmail.com>
 * created on 16 Jul 2020
 */
public class MakeDTO extends RepresentationModel<MakeDTO> {

    private Long id;

    private String name;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof MakeDTO)) return false;

        MakeDTO makeDTO = (MakeDTO) o;

        return new EqualsBuilder()
                .append(id, makeDTO.id)
                .append(name, makeDTO.name)
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
