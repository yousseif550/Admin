package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class InformationsTechTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InformationsTech.class);
        InformationsTech informationsTech1 = new InformationsTech();
        informationsTech1.setId("id1");
        InformationsTech informationsTech2 = new InformationsTech();
        informationsTech2.setId(informationsTech1.getId());
        assertThat(informationsTech1).isEqualTo(informationsTech2);
        informationsTech2.setId("id2");
        assertThat(informationsTech1).isNotEqualTo(informationsTech2);
        informationsTech1.setId(null);
        assertThat(informationsTech1).isNotEqualTo(informationsTech2);
    }
}
