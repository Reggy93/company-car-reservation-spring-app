package org.reggy93.ccrsa.endpoint.car.assembler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reggy93.ccrsa.endpoint.localization.assembler.CountryListRepresentationModelAssembler;
import org.reggy93.ccrsa.facade.dto.car.CountryDTO;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import java.util.Collection;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.reggy93.ccrsa.endpoint.EndpointConstants.ControllerPathConstants.Localization.COUNTRIES;
import static org.reggy93.ccrsa.endpoint.EndpointConstants.LinksConstants.ALL_COUNTRIES_RELATION;
import static org.reggy93.ccrsa.endpoint.EndpointConstants.LinksConstants.SELF_RELATION;

/**
 * Unit test for {@link CountryListRepresentationModelAssembler}.
 *
 * @author Reggy93 <marcin.z.wrobel@gmail.com>
 * created on 04 Sep 2020
 */
@ExtendWith(MockitoExtension.class)
class CountryListRepresentationModelAssemblerUnitTest {

    private static final String NO_LINK = "no_link";

    @InjectMocks
    private final RepresentationModelAssembler<CountryDTO, CountryDTO> testedAssembler =
            new CountryListRepresentationModelAssembler();

    @Spy
    private CountryDTO countryDTO1;

    @Spy
    private CountryDTO countryDTO2;

    @BeforeEach
    void setUp() {
        when(countryDTO1.getId()).thenReturn(1L);

    }

    @Test
    void toModelTest() {

        final CountryDTO result = testedAssembler.toModel(countryDTO1);

        assertThat(result, is(countryDTO1));
        assertThat(result.getLink(SELF_RELATION).orElse(Link.of(NO_LINK)).getHref(), is(endsWith(COUNTRIES + "/1")));
        assertThat(result.getLink(ALL_COUNTRIES_RELATION).orElse(Link.of(NO_LINK)).getHref(),
                is(endsWith(COUNTRIES)));
    }

    @Test
    void toCollectionModelTest() {

        when(countryDTO2.getId()).thenReturn(2L);

        final CollectionModel<CountryDTO> resultCountryDTOCollectionModel =
                testedAssembler.toCollectionModel(Set.of(countryDTO1, countryDTO2));

        assertThat(resultCountryDTOCollectionModel, is(notNullValue()));

        final Collection<CountryDTO> resultCollection = resultCountryDTOCollectionModel.getContent();

        assertThat(resultCollection, is(not(empty())));
        assertThat(resultCollection, is(hasSize(2)));
        assertThat(resultCollection, is(hasItems(countryDTO1, countryDTO2)));

        singleCountryAssertions(resultCollection, countryDTO1.getId());
        singleCountryAssertions(resultCollection, countryDTO2.getId());

        assertThat(resultCountryDTOCollectionModel.getLink(SELF_RELATION)
                .orElse(Link.of(NO_LINK)).getHref(), is(endsWith(COUNTRIES)));

    }

    private void singleCountryAssertions(final Collection<CountryDTO> resultCollection, final long id) {

        CountryDTO resultCountryDTO =
                resultCollection.stream().filter(countryDTO -> countryDTO.getId() == id).findFirst().orElse(null);
        assertThat(resultCountryDTO, is(notNullValue()));

        assertThat(resultCountryDTO.getLink(SELF_RELATION).orElse(Link.of(NO_LINK)).getHref(),
                is(endsWith(String.valueOf(id))));
        assertThat(resultCountryDTO.getLink(ALL_COUNTRIES_RELATION).orElse(Link.of(NO_LINK)).getHref(), is(endsWith(COUNTRIES)));

    }
}