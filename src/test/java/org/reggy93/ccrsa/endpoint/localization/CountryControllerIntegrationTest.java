package org.reggy93.ccrsa.endpoint.localization;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.reggy93.ccrsa.AbstractIntegrationTest;
import org.reggy93.ccrsa.service.dao.CountryRepository;
import org.reggy93.ccrsa.service.entity.car.Country;
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
import static org.reggy93.ccrsa.endpoint.EndpointConstants.ControllerPathConstants.Localization.COUNTRIES;
import static org.reggy93.ccrsa.endpoint.EndpointConstants.LinksConstants.ALL_COUNTRIES_RELATION;
import static org.reggy93.ccrsa.endpoint.EndpointConstants.LinksConstants.SELF_RELATION;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration test for {@link CountryController}.
 *
 * @author Reggy93 <marcin.z.wrobel@gmail.com>
 * created on 14 Sep 2020
 */
@WebAppConfiguration
class CountryControllerIntegrationTest extends AbstractIntegrationTest {

    private static final String COUNTRY_1_URL = COUNTRIES + "/1";

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private CountryRepository countryRepository;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @AfterEach
    void tearDown() {
        countryRepository.deleteAll();
    }

    @Test
    void getAllCountriesTest() throws Exception {

        final Country country1 = super.prepareCountry(POLAND);
        final Country country2 = super.prepareCountry(GERMANY);
        final Country country3 = super.prepareCountry(FRANCE);

        countryRepository.saveAll(List.of(country1, country2, country3));

        final MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get(COUNTRIES))
                .andDo(print())
                .andExpect(status().isOk())
//                check links for country list
                .andExpect(jsonPath("$.links.[0].rel").value(is(SELF_RELATION)))
                .andExpect(jsonPath("$.links.[0].href").value(endsWith(COUNTRIES)))
//                check countries names
                .andExpect(jsonPath("$.content.[0].name").value(is(POLAND)))
                .andExpect(jsonPath("$.content.[1].name").value(is(GERMANY)))
                .andExpect(jsonPath("$.content.[2].name").value(is(FRANCE)))
//                check links for country1
                .andExpect(jsonPath("$.content.[0].links.[0].rel").value(is(SELF_RELATION)))
                .andExpect(jsonPath("$.content.[0].links.[0].href").value(endsWith(country1.getId().toString())))
                .andExpect(jsonPath("$.content.[0].links.[1].rel").value(is(ALL_COUNTRIES_RELATION)))
                .andExpect(jsonPath("$.content.[0].links.[1].href").value(endsWith(COUNTRIES)))
//                check links for country2
                .andExpect(jsonPath("$.content.[1].links.[0].rel").value(is(SELF_RELATION)))
                .andExpect(jsonPath("$.content.[1].links.[0].href").value(endsWith(country2.getId().toString())))
                .andExpect(jsonPath("$.content.[1].links.[1].rel").value(is(ALL_COUNTRIES_RELATION)))
                .andExpect(jsonPath("$.content.[1].links.[1].href").value(endsWith(COUNTRIES)))
//                check links for country3
                .andExpect(jsonPath("$.content.[2].links.[0].rel").value(is(SELF_RELATION)))
                .andExpect(jsonPath("$.content.[2].links.[0].href").value(endsWith(country3.getId().toString())))
                .andExpect(jsonPath("$.content.[2].links.[1].rel").value(is(ALL_COUNTRIES_RELATION)))
                .andExpect(jsonPath("$.content.[2].links.[1].href").value(endsWith(COUNTRIES)))
                .andReturn();

        assertThat(mvcResult.getResponse().getContentType(), is("application/json"));
    }

    @Test
    void getAllCountriesWhenNoCountryReturnedTest() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get(COUNTRIES))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andReturn();
    }

    @Test
    void getCountryByIdTest() throws Exception {

        final Country country1 = super.prepareCountry(POLAND);
        countryRepository.save(country1);

        final String countryURL = COUNTRIES + "/" + country1.getId().toString();

        final MvcResult mvcResult =
                this.mockMvc.perform(MockMvcRequestBuilders.get(countryURL))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.links.[0].rel").value(is(SELF_RELATION)))
                        .andExpect(jsonPath("$.links.[0].href").value(endsWith(countryURL)))
                        .andExpect(jsonPath("$.name").value(is(POLAND)))
                        .andReturn();

        assertThat(mvcResult.getResponse().getContentType(), is("application/json"));

    }

    @Test
    void getCountryByIdWhenNoCountryReturnedTest() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get(COUNTRY_1_URL))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andReturn();
    }


}