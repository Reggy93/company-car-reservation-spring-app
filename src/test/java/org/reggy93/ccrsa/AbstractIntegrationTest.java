package org.reggy93.ccrsa;

import org.junit.jupiter.api.extension.ExtendWith;
import org.reggy93.ccrsa.service.dao.CarModelRepository;
import org.reggy93.ccrsa.service.dao.CountryRepository;
import org.reggy93.ccrsa.service.dao.LocalizationRepository;
import org.reggy93.ccrsa.service.dao.MakeRepository;
import org.reggy93.ccrsa.service.entity.car.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Abstract parent class for all integration tests, containing necessary annotations and helper methods.
 *
 * @author Reggy93 <marcin.z.wrobel@gmail.com>
 * created on 27 Jul 2020
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/applicationContext.xml"})
public abstract class AbstractIntegrationTest {

    protected static final String WARSAW = "Warsaw";
    protected static final String BERLIN = "Berlin";
    protected static final String PARIS = "Paris";

    protected static final String POLAND = "Poland";
    protected static final String GERMANY = "Germany";
    protected static final String FRANCE = "France";

    protected static final String FABIA = "Fabia";
    protected static final String SKODA = "Skoda";

    protected static final String AUDI = "Audi";
    protected static final String A_3 = "A3";
    protected static final String SUPERB = "Superb";

    private static final Map<String, String> countryIsoCodeMap = new HashMap<>();

    @Autowired
    protected CarModelRepository carModelRepository;

    @Autowired
    protected LocalizationRepository localizationRepository;

    @Autowired
    protected CountryRepository countryRepository;

    @Autowired
    protected MakeRepository makeRepository;

    static {
        countryIsoCodeMap.put(POLAND, "PL");
        countryIsoCodeMap.put(GERMANY, "GE");
        countryIsoCodeMap.put(FRANCE, "FR");
    }

    protected Car prepareCar(final String makeName, final String model, final String country, final String city,
                             final float engineCapacity, final FuelType fuelType, final int horsePower,
                             final int numberOfDoors, final String registrationNumber) {

        final Car car = new Car();

        final CarModel carModel = prepareCarModel(model, makeName);
        final Localization localization = prepareLocalization(city, country);

        car.setCarModel(carModel);
        car.setLocalization(localization);
        car.setCurrentlyAvailable(true);

        car.setCarDetails(prepareCarDetails(engineCapacity, fuelType, horsePower, numberOfDoors, registrationNumber));

        return car;
    }

    protected Make prepareMake(final String makeName) {
        return Optional.ofNullable(makeRepository.findByName(makeName)).orElseGet(() -> createAndSaveNewMake(makeName));
    }

    protected CarModel prepareCarModel(final String modelName, final String makeName) {

        return Optional.ofNullable(carModelRepository.findByName(modelName))
                .orElseGet(() -> createAndSaveNewCarModel(modelName, prepareMake(makeName)));
    }

    protected Country prepareCountry(final String countryName) {

        return Optional.ofNullable(countryRepository.findByName(countryName)).orElseGet(() -> createAndSaveNewCountry(countryName));
    }

    protected Localization prepareLocalization(final String cityName, final String countryName) {

        return localizationRepository.findByCity(cityName)
                .orElseGet(() -> createAndSaveNewLocalization(cityName, prepareCountry(countryName)));
    }

    private Make createAndSaveNewMake(final String makeName) {

        Make make = new Make(makeName);
        return makeRepository.save(make);
    }

    private Country createAndSaveNewCountry(final String countryName) {

        Country country = new Country(countryName, Optional.ofNullable(countryIsoCodeMap.get(countryName)).orElse("PL"));
        return countryRepository.save(country);
    }

    private CarModel createAndSaveNewCarModel(final String carModelName, final Make make) {

        CarModel carModel = new CarModel(carModelName, make);
        return carModelRepository.save(carModel);
    }

    private Localization createAndSaveNewLocalization(final String cityName, final Country country) {

        Localization localization = new Localization(cityName, country);
        return localizationRepository.save(localization);
    }

    private CarDetails prepareCarDetails(final float engineCapacity, final FuelType fuelType, final int horsePower,
                                         final int numberOfDoors, final String registrationNumber) {

        CarDetails carDetails = new CarDetails();

        carDetails.setEngineCapacity(engineCapacity);
        carDetails.setFuelType(fuelType);
        carDetails.setHorsePower(horsePower);
        carDetails.setNumberOfDoors(numberOfDoors);
        carDetails.setRegistrationNumber(registrationNumber);

        return carDetails;
    }
}
