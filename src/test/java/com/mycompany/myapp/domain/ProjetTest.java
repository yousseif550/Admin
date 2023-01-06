package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProjetTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Projet.class);
        Projet projet1 = new Projet();
        projet1.setId("id1");
        Projet projet2 = new Projet();
        projet2.setId(projet1.getId());
        assertThat(projet1).isEqualTo(projet2);
        projet2.setId("id2");
        assertThat(projet1).isNotEqualTo(projet2);
        projet1.setId(null);
        assertThat(projet1).isNotEqualTo(projet2);
    }
}
