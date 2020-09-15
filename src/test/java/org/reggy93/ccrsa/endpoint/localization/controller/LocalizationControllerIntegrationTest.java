package org.reggy93.ccrsa.endpoint.localization.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.reggy93.ccrsa.AbstractIntegrationTest;
import org.reggy93.ccrsa.service.dao.CountryRepository;
import org.reggy93.ccrsa.service.dao.LocalizationRepository;
import org.reggy93.ccrsa.service.entity.car.Localization;
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
import static org.reggy93.ccrsa.endpoint.EndpointConstants.ControllerPathConstants.Localization.LOCALIZATIONS;
import static org.reggy93.ccrsa.endpoint.EndpointConstants.LinksConstants.ALL_COUNTRIES_RELATION;
import static org.reggy93.ccrsa.endpoint.EndpointConstants.LinksConstants.ALL_LOCALIZATIONS_RELATION;
import static org.reggy93.ccrsa.endpoint.EndpointConstants.LinksConstants.SELF_RELATION;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration test for {@link LocalizationController}.
 *
 * @author Reggy93 <marcin.z.wrobel@gmail.com>
 * created on 26 Sep 2020
 */
@WebAppConfiguration
class LocalizationControllerIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private LocalizationRepository localizationRepository;

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
    void getAllLocalizationsTest() throws Exception {

        final Localization localization1 = super.prepareLocalization(WARSAW, POLAND);
        final Localization localization2 = super.prepareLocalization(BERLIN, GERMANY);
        final Localization localization3 = super.prepareLocalization(PARIS, FRANCE);

        localizationRepository.saveAll(List.of(localization1, localization2, localization3));

        final MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get(LOCALIZATIONS))
                .andDo(print())
                .andExpect(status().isOk())
//                check links for localization list
                .andExpect(jsonPath("$.links.[0].rel").value(is(SELF_RELATION)))
                .andExpect(jsonPath("$.links.[0].href").value(endsWith(LOCALIZATIONS)))
//                check localizations city names
                .andExpect(jsonPath("$.content.[0].city").value(is(WARSAW)))
                .andExpect(jsonPath("$.content.[1].city").value(is(BERLIN)))
                .andExpect(jsonPath("$.content.[2].city").value(is(PARIS)))
//                check localizations country names
                .andExpect(jsonPath("$.content.[0].country.name").value(is(POLAND)))
                .andExpect(jsonPath("$.content.[1].country.name").value(is(GERMANY)))
                .andExpect(jsonPath("$.content.[2].country.name").value(is(FRANCE)))
//                check links for localization1
                .andExpect(jsonPath("$.content.[0].links.[0].rel").value(is(SELF_RELATION)))
                .andExpect(jsonPath("$.content.[0].links.[0].href").value(endsWith(localization1.getId().toString())))
                .andExpect(jsonPath("$.content.[0].links.[1].rel").value(is(ALL_LOCALIZATIONS_RELATION)))
                .andExpect(jsonPath("$.content.[0].links.[1].href").value(endsWith(LOCALIZATIONS)))
//                check links for localization2
                .andExpect(jsonPath("$.content.[1].links.[0].rel").value(is(SELF_RELATION)))
                .andExpect(jsonPath("$.content.[1].links.[0].href").value(endsWith(localization2.getId().toString())))
                .andExpect(jsonPath("$.content.[1].links.[1].rel").value(is(ALL_LOCALIZATIONS_RELATION)))
                .andExpect(jsonPath("$.content.[1].links.[1].href").value(endsWith(LOCALIZATIONS)))
//                check links for localization2
                .andExpect(jsonPath("$.content.[2].links.[0].rel").value(is(SELF_RELATION)))
                .andExpect(jsonPath("$.content.[2].links.[0].href").value(endsWith(localization3.getId().toString())))
                .andExpect(jsonPath("$.content.[2].links.[1].rel").value(is(ALL_LOCALIZATIONS_RELATION)))
                .andExpect(jsonPath("$.content.[2].links.[1].href").value(endsWith(LOCALIZATIONS)))
//                check links for localization1 country links
                .andExpect(jsonPath("$.content.[0].country.links.[0].rel").value(is(SELF_RELATION)))
                .andExpect(jsonPath("$.content.[0].country.links.[0].href").
                        value(endsWith(localization1.getCountry().getId().toString())))
                .andExpect(jsonPath("$.content.[0].country.links.[1].rel").value(is(ALL_COUNTRIES_RELATION)))
                .andExpect(jsonPath("$.content.[0].country.links.[1].href").value(endsWith(COUNTRIES)))
//                check links for localization2 country links
                .andExpect(jsonPath("$.content.[1].country.links.[0].rel").value(is(SELF_RELATION)))
                .andExpect(jsonPath("$.content.[1].country.links.[0].href").
                        value(endsWith(localization2.getCountry().getId().toString())))
                .andExpect(jsonPath("$.content.[1].country.links.[1].rel").value(is(ALL_COUNTRIES_RELATION)))
                .andExpect(jsonPath("$.content.[1].country.links.[1].href").value(endsWith(COUNTRIES)))
//                check links for localization3 country links
                .andExpect(jsonPath("$.content.[2].country.links.[0].rel").value(is(SELF_RELATION)))
                .andExpect(jsonPath("$.content.[2].country.links.[0].href").
                        value(endsWith(localization3.getCountry().getId().toString())))
                .andExpect(jsonPath("$.content.[2].country.links.[1].rel").value(is(ALL_COUNTRIES_RELATION)))
                .andExpect(jsonPath("$.content.[2].country.links.[1].href").value(endsWith(COUNTRIES)))
                .andReturn();

        assertThat(mvcResult.getResponse().getContentType(), is("application/json"));
    }

    @Test
    void getAllLocalizationsWhenNoLocalizationRetrievedTest() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get(LOCALIZATIONS))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andReturn();
    }

    @Test
    void getLocalizationByIdTest() throws Exception {

        final Localization localization1 = super.prepareLocalization(WARSAW, POLAND);
        localizationRepository.save(localization1);

        final String localizationURL = LOCALIZATIONS + "/" + localization1.getId().toString();

        verifyResult(localizationURL, localization1);
    }

    @Test
    void getLocalizationByCityNameTest() throws Exception {

        final Localization localization1 = super.prepareLocalization(WARSAW, POLAND);
        localizationRepository.save(localization1);

        final String localizationURL = LOCALIZATIONS + "/filter?name=" + localization1.getCity();

        verifyResult(localizationURL, localization1);
    }

    private void verifyResult(final String url, final Localization localization) throws Exception {

        final MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get(url))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.links.[0].rel").value(is(SELF_RELATION)))
                .andExpect(jsonPath("$.links.[0].href").value(endsWith(localization.getId().toString())))
                .andExpect(jsonPath("$.city").value(is(WARSAW)))
                .andExpect(jsonPath("$.country.name").value(is(POLAND)))
                .andExpect(jsonPath("$.country.links.[0].rel").value(is(SELF_RELATION)))
                .andExpect(jsonPath("$.country.links.[0].href").value(endsWith(localization.getCountry().getId().toString())))
                .andExpect(jsonPath("$.country.links.[1].rel").value(is(ALL_COUNTRIES_RELATION)))
                .andExpect(jsonPath("$.country.links.[1].href").value(endsWith(COUNTRIES)))
                .andReturn();

        assertThat(mvcResult.getResponse().getContentType(), is("application/json"));

    }
}