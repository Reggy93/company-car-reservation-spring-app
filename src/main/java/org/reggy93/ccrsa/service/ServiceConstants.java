package org.reggy93.ccrsa.service;

/**
 * Static class to hold {@code Service} layer constants.
 *
 * @author Reggy93 <marcin.z.wrobel@gmail.com>
 * created on 17 Jul 2020
 */
public final class ServiceConstants {

    private ServiceConstants() {
    }

    public static final class Car {

        private Car() {
        }

        public static final String CAR_LIST_ENTITY_GRAPH = "CarList.CarModel.Localization.details";

        public static final String CAR_MODEL_LIST_MAKE = "CarModelList.make";

    }

    public static final class Localization {

        private Localization() {
        }

        public static final String LOCALIZATION_LIST_COUNTRY = "LocalizationList.country";
    }

}
