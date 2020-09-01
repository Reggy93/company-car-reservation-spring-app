package org.reggy93.ccrsa.endpoint.car;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.reggy93.ccrsa.AbstractIntegrationTest;
import org.reggy93.ccrsa.endpoint.car.controller.CarListController;
import org.reggy93.ccrsa.service.dao.CarRepository;
import org.reggy93.ccrsa.service.entity.car.Car;
import org.reggy93.ccrsa.service.entity.car.FuelType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.is;
import static org.reggy93.ccrsa.endpoint.EndpointConstants.ControllerPathConstants.Car.CARS;
import static org.reggy93.ccrsa.endpoint.EndpointConstants.ControllerPathConstants.Car.CARS_MODELS;
import static org.reggy93.ccrsa.endpoint.EndpointConstants.ControllerPathConstants.Car.MAKES;
import static org.reggy93.ccrsa.endpoint.EndpointConstants.ControllerPathConstants.Localization.COUNTRIES;
import static org.reggy93.ccrsa.endpoint.EndpointConstants.ControllerPathConstants.Localization.LOCALIZATIONS;
import static org.reggy93.ccrsa.endpoint.EndpointConstants.LinksConstants.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration test for {@link CarListController}.
 *
 * @author Reggy93 <marcin.z.wrobel@gmail.com>
 * created on 28 Jul 2020
 */
@WebAppConfiguration
class CarListControllerIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @AfterEach
    void tearDown() {
        makeRepository.deleteAll();
        countryRepository.deleteAll();
    }

    @Test
    void getAllCarsTest() throws Exception {

        final Car car1 = super.prepareCar(SKODA, FABIA, POLAND, WARSAW, 1.2f,
                FuelType.LPG, 100, 3, "WE123W");

        final Car car2 = super.prepareCar(AUDI, A_3, GERMANY, BERLIN, 1.4f,
                FuelType.PB, 140, 5, "DE234GE");

        final Car car3 = super.prepareCar(SKODA, SUPERB, POLAND, WARSAW, 2.0f,
                FuelType.ON, 170, 5, "WE123Q");

        carRepository.saveAll(List.of(car1, car2, car3));

        final MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get(CARS))
                .andDo(print())
                .andExpect(status().isOk())
//                check links for car list
                .andExpect(jsonPath("$.links.[0].rel").value(is(SELF_RELATION)))
                .andExpect(jsonPath("$.links.[0].href").value(endsWith(CARS)))
//                check car models
                .andExpect(jsonPath("$.content.[0].carModel.name").value(is(FABIA)))
                .andExpect(jsonPath("$.content.[1].carModel.name").value(is(A_3)))
                .andExpect(jsonPath("$.content.[2].carModel.name").value(is(SUPERB)))
//                check car makes
                .andExpect(jsonPath("$.content.[0].carModel.make.name").value(is(SKODA)))
                .andExpect(jsonPath("$.content.[1].carModel.make.name").value(is(AUDI)))
                .andExpect(jsonPath("$.content.[2].carModel.make.name").value(is(SKODA)))
//                check car cities
                .andExpect(jsonPath("$.content.[0].localization.city").value(is(WARSAW)))
                .andExpect(jsonPath("$.content.[1].localization.city").value(is(BERLIN)))
                .andExpect(jsonPath("$.content.[2].localization.city").value(is(WARSAW)))
//                check car countries
                .andExpect(jsonPath("$.content.[0].localization.country.name").value(is(POLAND)))
                .andExpect(jsonPath("$.content.[1].localization.country.name").value(is(GERMANY)))
                .andExpect(jsonPath("$.content.[2].localization.country.name").value(is(POLAND)))
//                check links for car1
                .andExpect(jsonPath("$.content.[0].links.[0].rel").value(is(SELF_RELATION)))
                .andExpect(jsonPath("$.content.[0].links.[0].href").value(endsWith(car1.getId().toString())))
                .andExpect(jsonPath("$.content.[0].links.[1].rel").value(is(ALL_CARS_RELATION)))
                .andExpect(jsonPath("$.content.[0].links.[1].href").value(endsWith(CARS)))
//                check links for car2
                .andExpect(jsonPath("$.content.[1].links.[0].rel").value(is(SELF_RELATION)))
                .andExpect(jsonPath("$.content.[1].links.[0].href").value(endsWith(car2.getId().toString())))
                .andExpect(jsonPath("$.content.[1].links.[1].rel").value(is(ALL_CARS_RELATION)))
                .andExpect(jsonPath("$.content.[1].links.[1].href").value(endsWith(CARS)))
//                check links for car3
                .andExpect(jsonPath("$.content.[2].links.[0].rel").value(is(SELF_RELATION)))
                .andExpect(jsonPath("$.content.[2].links.[0].href")
                        .value(endsWith(car3.getId().toString())))
                .andExpect(jsonPath("$.content.[2].links.[1].rel").value(is(ALL_CARS_RELATION)))
                .andExpect(jsonPath("$.content.[2].links.[1].href").value(endsWith(CARS)))
//                check links for carModel of car1
                .andExpect(jsonPath("$.content.[0].carModel.links.[0].rel").value(is(SELF_RELATION)))
                .andExpect(jsonPath("$.content.[0].carModel.links.[0].href")
                        .value(endsWith(car1.getCarModel().getId().toString())))
                .andExpect(jsonPath("$.content.[0].carModel.links.[1].rel").value(is(ALL_CARS_MODELS_RELATION)))
                .andExpect(jsonPath("$.content.[0].carModel.links.[1].href").value(endsWith(CARS_MODELS)))
//                check links for carModel of car2
                .andExpect(jsonPath("$.content.[1].carModel.links.[0].rel").value(is(SELF_RELATION)))
                .andExpect(jsonPath("$.content.[1].carModel.links.[0].href")
                        .value(endsWith(car2.getCarModel().getId().toString())))
                .andExpect(jsonPath("$.content.[1].carModel.links.[1].rel").value(is(ALL_CARS_MODELS_RELATION)))
                .andExpect(jsonPath("$.content.[1].carModel.links.[1].href").value(endsWith(CARS_MODELS)))
//                check links for carModel of car3
                .andExpect(jsonPath("$.content.[2].carModel.links.[0].rel").value(is(SELF_RELATION)))
                .andExpect(jsonPath("$.content.[2].carModel.links.[0].href")
                        .value(endsWith(car3.getCarModel().getId().toString())))
                .andExpect(jsonPath("$.content.[2].carModel.links.[1].rel").value(is(ALL_CARS_MODELS_RELATION)))
                .andExpect(jsonPath("$.content.[2].carModel.links.[1].href").value(endsWith(CARS_MODELS)))
//                check links for make of car1
                .andExpect(jsonPath("$.content.[0].carModel.make.links.[0].rel").value(is(SELF_RELATION)))
                .andExpect(jsonPath("$.content.[0].carModel.make.links.[0].href")
                        .value(endsWith(car1.getCarModel().getMake().getId().toString())))
                .andExpect(jsonPath("$.content.[0].carModel.make.links.[1].rel").value(is(ALL_MAKES_RELATION)))
                .andExpect(jsonPath("$.content.[0].carModel.make.links.[1].href").value(endsWith(MAKES)))
//                check links for make of car2
                .andExpect(jsonPath("$.content.[1].carModel.make.links.[0].rel").value(is(SELF_RELATION)))
                .andExpect(jsonPath("$.content.[1].carModel.make.links.[0].href")
                        .value(endsWith(car2.getCarModel().getMake().getId().toString())))
                .andExpect(jsonPath("$.content.[1].carModel.make.links.[1].rel").value(is(ALL_MAKES_RELATION)))
                .andExpect(jsonPath("$.content.[1].carModel.make.links.[1].href").value(endsWith(MAKES)))
//               check links form make of car3
                .andExpect(jsonPath("$.content.[2].carModel.make.links.[0].rel").value(is(SELF_RELATION)))
                .andExpect(jsonPath("$.content.[2].carModel.make.links.[0].href")
                        .value(endsWith(car3.getCarModel().getMake().getId().toString())))
                .andExpect(jsonPath("$.content.[2].carModel.make.links.[1].rel").value(is(ALL_MAKES_RELATION)))
                .andExpect(jsonPath("$.content.[2].carModel.make.links.[1].href").value(endsWith(MAKES)))
//               check links for localization of car1
                .andExpect(jsonPath("$.content.[0].localization.links.[0].rel").value(is(SELF_RELATION)))
                .andExpect(jsonPath("$.content.[0].localization.links.[0].href")
                        .value(endsWith(car1.getLocalization().getId().toString())))
                .andExpect(jsonPath("$.content.[0].localization.links.[1].rel").value(is(ALL_LOCALIZATIONS_RELATION)))
                .andExpect(jsonPath("$.content.[0].localization.links.[1].href").value(endsWith(LOCALIZATIONS)))
//                check links for localization of car2
                .andExpect(jsonPath("$.content.[1].localization.links.[0].rel").value(is(SELF_RELATION)))
                .andExpect(jsonPath("$.content.[1].localization.links.[0].href")
                        .value(endsWith(car2.getLocalization().getId().toString())))
                .andExpect(jsonPath("$.content.[1].localization.links.[1].rel").value(is(ALL_LOCALIZATIONS_RELATION)))
                .andExpect(jsonPath("$.content.[1].localization.links.[1].href").value(endsWith(LOCALIZATIONS)))
//                check links for localization of car3
                .andExpect(jsonPath("$.content.[2].localization.links.[0].rel").value(is(SELF_RELATION)))
                .andExpect(jsonPath("$.content.[2].localization.links.[0].href")
                        .value(endsWith(car3.getLocalization().getId().toString())))
                .andExpect(jsonPath("$.content.[2].localization.links.[1].rel").value(is(ALL_LOCALIZATIONS_RELATION)))
                .andExpect(jsonPath("$.content.[2].localization.links.[1].href").value(endsWith(LOCALIZATIONS)))
//                check links for country of car1
                .andExpect(jsonPath("$.content.[0].localization.country.links.[0].rel").value(is(SELF_RELATION)))
                .andExpect(jsonPath("$.content.[0].localization.country.links.[0].href")
                        .value(endsWith(car1.getLocalization().getCountry().getId().toString())))
                .andExpect(jsonPath("$.content.[0].localization.country.links.[1].rel").value(is(ALL_COUNTRIES_RELATION)))
                .andExpect(jsonPath("$.content.[0].localization.country.links.[1].href").value(endsWith(COUNTRIES)))
//                check links for country of car2
                .andExpect(jsonPath("$.content.[1].localization.country.links.[0].rel").value(is(SELF_RELATION)))
                .andExpect(jsonPath("$.content.[1].localization.country.links.[0].href")
                        .value(endsWith(car2.getLocalization().getCountry().getId().toString())))
                .andExpect(jsonPath("$.content.[1].localization.country.links.[1].rel").value(is(ALL_COUNTRIES_RELATION)))
                .andExpect(jsonPath("$.content.[1].localization.country.links.[1].href").value(endsWith(COUNTRIES)))
//                check links for country of car3
                .andExpect(jsonPath("$.content.[2].localization.country.links.[0].rel").value(is(SELF_RELATION)))
                .andExpect(jsonPath("$.content.[2].localization.country.links.[0].href")
                        .value(endsWith(car3.getLocalization().getCountry().getId().toString())))
                .andExpect(jsonPath("$.content.[2].localization.country.links.[1].rel").value(is(ALL_COUNTRIES_RELATION)))
                .andExpect(jsonPath("$.content.[2].localization.country.links.[1].href").value(endsWith(COUNTRIES)))
                .andReturn();

        assertThat(mvcResult.getResponse().getContentType(), is("application/json"));
    }

    @Test
    void getAllCarsWhenNoCarReturnedTest() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get(CARS))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andReturn();
    }
}