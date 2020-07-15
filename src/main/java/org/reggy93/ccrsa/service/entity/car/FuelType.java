package org.reggy93.ccrsa.service.entity.car;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Enum class to hold fuel types.
 *
 * @author Reggy93 <marcin.z.wrobel@gmail.com>
 * created on 08 Jul 2020
 */
public enum FuelType {
    PB(FuelGroup.GENERAL), ON(FuelGroup.GENERAL), ELECTRIC(FuelGroup.GENERAL),
    LPG(FuelGroup.GAS), CNG(FuelGroup.GAS), LNG(FuelGroup.GAS);

    private final FuelGroup fuelGroup;

    FuelType(FuelGroup fuelGroup) {
        this.fuelGroup = fuelGroup;
    }

    public boolean isInFuelGroup(final FuelGroup fuelGroup) {
        return fuelGroup == this.fuelGroup;
    }

    public enum FuelGroup {
        GENERAL, GAS
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("fuelGroup", fuelGroup)
                .toString();
    }
}
