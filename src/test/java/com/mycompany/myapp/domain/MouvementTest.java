package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MouvementTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Mouvement.class);
        Mouvement mouvement1 = new Mouvement();
        mouvement1.setId("id1");
        Mouvement mouvement2 = new Mouvement();
        mouvement2.setId(mouvement1.getId());
        assertThat(mouvement1).isEqualTo(mouvement2);
        mouvement2.setId("id2");
        assertThat(mouvement1).isNotEqualTo(mouvement2);
        mouvement1.setId(null);
        assertThat(mouvement1).isNotEqualTo(mouvement2);
    }
}
