package org.reggy93.ccrsa.facade.dto.car;


/**
 * DTO class for {@code CarDetails}.
 *
 * @author Reggy93 <marcin.z.wrobel@gmail.com>
 * created on 17 Jul 2020
 */
//TODO: End implementation
public class CarDetailsDTO {

    private String registrationNumber;

    private float engineCapacity;

    public String getRegistrationNumber() {
        return registrationNumber;
    }
    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public float getEngineCapacity() {
        return engineCapacity;
    }

    public void setEngineCapacity(float engineCapacity) {
        this.engineCapacity = engineCapacity;
    }
}
