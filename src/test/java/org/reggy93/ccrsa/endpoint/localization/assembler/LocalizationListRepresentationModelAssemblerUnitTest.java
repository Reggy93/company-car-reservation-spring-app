package org.reggy93.ccrsa.endpoint.localization.assembler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reggy93.ccrsa.facade.dto.car.CountryDTO;
import org.reggy93.ccrsa.facade.dto.car.LocalizationDTO;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import java.util.Collection;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.reggy93.ccrsa.endpoint.EndpointConstants.ControllerPathConstants.Localization.LOCALIZATIONS;
import static org.reggy93.ccrsa.endpoint.EndpointConstants.LinksConstants.ALL_LOCALIZATIONS_RELATION;
import static org.reggy93.ccrsa.endpoint.EndpointConstants.LinksConstants.SELF_RELATION;

/**
 * Unit test for {@link LocalizationListRepresentationModelAssembler}.
 *
 * @author Reggy93 <marcin.z.wrobel@gmail.com>
 * created on 17 Sep 2020
 */
@ExtendWith(MockitoExtension.class)
class LocalizationListRepresentationModelAssemblerUnitTest {

    private static final String NO_LINK = "no_link";

    @InjectMocks
    private LocalizationListRepresentationModelAssembler testedAssembler;

    @Mock
    private RepresentationModelAssembler<CountryDTO, CountryDTO> countryDTORepresentationModelAssembler;

    @Spy
    private LocalizationDTO localizationDTO1;

    @Spy
    private LocalizationDTO localizationDTO2;


    @BeforeEach
    void setUp() {
        testedAssembler = new LocalizationListRepresentationModelAssembler(countryDTORepresentationModelAssembler);
        when(localizationDTO1.getId()).thenReturn(1L);
    }

    @Test
    void toModelTest() {

        final LocalizationDTO result = testedAssembler.toModel(localizationDTO1);

        assertThat(result, is(localizationDTO1));
        assertThat(result.getLink(SELF_RELATION).orElse(Link.of(NO_LINK)).getHref(), is(endsWith(LOCALIZATIONS + "/1")));
        assertThat(result.getLink(ALL_LOCALIZATIONS_RELATION).orElse(Link.of(NO_LINK)).getHref(),
                is(endsWith(LOCALIZATIONS)));
    }

    @Test
    void toCollectionModelTest() {

        when(localizationDTO2.getId()).thenReturn(2L);

        final CollectionModel<LocalizationDTO> resultCollectionModel =
                testedAssembler.toCollectionModel(Set.of(localizationDTO1, localizationDTO2));

        assertThat(resultCollectionModel, is(notNullValue()));

        final Collection<LocalizationDTO> resultCollection = resultCollectionModel.getContent();

        assertThat(resultCollection, is(not(empty())));
        assertThat(resultCollection, is(hasSize(2)));
        assertThat(resultCollection, hasItems(localizationDTO1, localizationDTO2));

        singleLocalizationAssertions(resultCollection, localizationDTO1.getId());
        singleLocalizationAssertions(resultCollection, localizationDTO2.getId());

        assertThat(resultCollectionModel.getLink(SELF_RELATION).orElse(Link.of(NO_LINK)).getHref(), is(endsWith(LOCALIZATIONS)));

    }

    private void singleLocalizationAssertions(final Collection<LocalizationDTO> resultCollection, final long id) {

        final LocalizationDTO resultLocalizationDTO =
                resultCollection.stream().filter(localizationDTO -> localizationDTO.getId() == id).findFirst().orElse(null);

        assertThat(resultLocalizationDTO, is(notNullValue()));
        assertThat(resultLocalizationDTO.getLink(SELF_RELATION).orElse(Link.of(NO_LINK)).getHref(),
                is(endsWith(String.valueOf(id))));
        assertThat(resultLocalizationDTO.getLink(ALL_LOCALIZATIONS_RELATION).orElse(Link.of(NO_LINK)).getHref(),
                is(endsWith(LOCALIZATIONS)));
    }
}