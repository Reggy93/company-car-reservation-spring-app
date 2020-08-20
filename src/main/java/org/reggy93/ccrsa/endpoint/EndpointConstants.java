package org.reggy93.ccrsa.endpoint;

/**
 * Static class to hold {@code Endpoint} layer constants.
 *
 * @author Reggy93 <marcin.z.wrobel@gmail.com>
 * created on 13 Aug 2020
 */
public final class EndpointConstants {

    private EndpointConstants() {
    }

    /**
     * Static class to hold user, rental, car, and localization types controllers request mapping path constants.
     */
    public static final class ControllerPathConstants {

        /**
         * Static class to hold car controllers request mapping path constants.
         */
        public static final class Car {

            public static final String CARS = "/cars";

            public static final String CARS_MODELS = "/carsModels";

            public static final String MAKES = "/makes";

            private Car() {
            }
        }

        /**
         * Static class to hold localization controllers request mapping path constants.
         */
        public static final class Localization {


            public static final String COUNTRIES = "/countries";

            public static final String LOCALIZATIONS = "/localizations";

            private Localization() {
            }
        }

        private ControllerPathConstants() {
        }
    }

    /**
     * Static class to hold links constants.
     */
    public static final class LinksConstants {

        public static final String ALL_MAKES_RELATION = "allMakes";

        public static final String ALL_LOCALIZATIONS_RELATION = "allLocalizations";

        public static final String ALL_COUNTRIES_RELATION = "allCountries";

        public static final String SELF_RELATION = "self";

        public static final String ALL_CARS_RELATION = "allCars";

        public static final String ALL_CARS_MODELS_RELATION = "allCarsModels";

        private LinksConstants() {
        }
    }
}
