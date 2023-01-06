package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NumeroInventaireTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NumeroInventaire.class);
        NumeroInventaire numeroInventaire1 = new NumeroInventaire();
        numeroInventaire1.setId("id1");
        NumeroInventaire numeroInventaire2 = new NumeroInventaire();
        numeroInventaire2.setId(numeroInventaire1.getId());
        assertThat(numeroInventaire1).isEqualTo(numeroInventaire2);
        numeroInventaire2.setId("id2");
        assertThat(numeroInventaire1).isNotEqualTo(numeroInventaire2);
        numeroInventaire1.setId(null);
        assertThat(numeroInventaire1).isNotEqualTo(numeroInventaire2);
    }
}
